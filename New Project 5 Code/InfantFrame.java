import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Timer;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Graphical user interface for interacting with Infant data
 * 
 * @author Tony Nguyen and Michael Morgan
 * @version 2017-11-1
 */
public class InfantFrame extends JFrame
{
    /** Serial ID */
    private static final long serialVersionUID = 1L;
    /** Panel for selecting the station, statistic, and Year */
    private SelectionPanel selectionPanel;
    /** Panel for displaying statistic */
    private DataPanel dataPanel;
    /** Width of column 1 text fields in the data display that is the numbers */
    private final static int COLUMN_FIELD_WIDTH = 10;
    /** Infant that is currently loaded.  */
    private Infant infant;
    /** Trial that is currently loaded.  */
    private Trial trial;
    /** Font of the labels and any text in the program.  */
    private final static Font FONT = new Font("Times New Roman", Font.PLAIN, 12);

    ///////////////////////////////////////////////////////////////////
    /**
     * 
     * @author CS2334, modified by Tony Nguyen and Michael Morgan
     * @version 2017-11-1
     * 
     * Menu bar that provides file loading and program exit capabilities.
     */
    private class FileMenuBar extends JMenuBar 
    {
        /** Serial ID */
		private static final long serialVersionUID = 1L;
		/** Menu on the menu bar */
        private JMenu menu;
        /** Exit menu option */
        private JMenuItem menuExit;
        /** Open menu option.  */
        private JMenuItem menuOpen;
        /** Reference to a file chooser pop-up */
        private JFileChooser fileChooser;

        /**
         * Constructor: fully assemble the menu bar and attach the 
         * necessary action listeners.
         */
        public FileMenuBar()
        {
            // Create the menu and add it to the menu bar.
            menu = new JMenu("File");
            add(menu);
            // The open file sub item under menu.
            menuOpen = new JMenuItem("Open Configuration File");
            menu.add(menuOpen);
            // The exit sub item under menu.
            menuExit = new JMenuItem("Exit");
            menu.add(menuExit);

            // Action listener for exit
            menuExit.addActionListener(new ActionListener() 
            {
                public void actionPerformed(ActionEvent e) 
                {
                    System.exit(0);
                }
            });

            // Filter for the file chooser: we only want files ending in '.dat'
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Configuration Files", "dat");

            // Create the file chooser: the default is to use the data directory
            fileChooser = new JFileChooser(new File("./data"));
            fileChooser.setFileFilter(filter);

            // Set menu bar name for testing.
            menuOpen.setName("MenuOpen");
            // Action listener for file open
            menuOpen.addActionListener(new ActionListener() 
            {
                public void actionPerformed(ActionEvent e) 
                {
                    // Ask for a file
                    int returnVal = fileChooser.showOpenDialog(menuOpen);
                    // Was a file specified?
                    if (returnVal == JFileChooser.APPROVE_OPTION) 
                    {
                        // Yes
                        // Extract the file that was selected
                        File file = fileChooser.getSelectedFile();
                        try 
                        {
                            // Set to a "busy" cursor
                            Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);

                            // Save the components of the selected file
                            String directory = file.getParent();
                            String fname = file.getName();
                            
                            // No data loaded then open a message dialog box to indicate an error
                            if (file.equals(null))
                            {
                                JOptionPane.showMessageDialog(fileChooser, "No Data Found");
                            }
                            
                            // Do the loading work: the first two characters are the Infant ID
                            loadData(directory, fname);
                            
                            // Return the cursor to standard form
                            InfantFrame.this.setCursor(null);

                            // No data loaded then open a message dialog box to indicate an error
                            if (directory.equals(null) && fname.equals(null))
                            {
                                JOptionPane.showMessageDialog(fileChooser, "No Data Found");
                            }
                            
                            // Update the frame
                            InfantFrame.this.update();
                        }
                        catch (IOException e2)
                        {
                            // Catch IO errors
                            JOptionPane.showMessageDialog(fileChooser, "File load error");
                            // Return the cursor to standard form
                            InfantFrame.this.setCursor(null);
                        }
                    }   
                }
            });
        }
    }


    ///////////////////////////////////////////////////////////////////
    /**
     * 
     * @author CS2334, modified by Tony Nguyen and Michael Morgan
     * @version 11/16/2017
     * 
     * Selection panel: contains JLists for the list of trials.
     */
    private class SelectionPanel extends JPanel
    {
        /** Serial ID */
        private static final long serialVersionUID = 1L;
		/** Selection of available trials/weeks.  */
        private JList<String> trialList;
        /** List model for the trial list.  */
        private DefaultListModel<String> trialListModel;
        /** Scroll pane: trial list  */
        private JScrollPane trialScroller;
        /**  Trial selection label */
        private JLabel trialLabel;

        /**
         * Constructor
         * 
         * Creates a trial with label on the left column and scroll pane on the right 
         * that allows the user to choose which trial to render.
         */
        private SelectionPanel()
        {
            ////////////////////////////
            // Create the JList for trial selection
            // List model contains the data to be displayed.
            trialListModel = new DefaultListModel<String>();
            // JList for trials
            trialList = new JList<String>(trialListModel);
            // Multiple items can be selected at once
            trialList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            // Vertically organized list with an arbitrary numer of rows
            trialList.setVisibleRowCount(-1);
            trialList.setLayoutOrientation(JList.VERTICAL);
            // Scroll pane goes around the JList
            trialScroller = new JScrollPane(trialList);
            trialScroller.setPreferredSize(new Dimension(300, 100));

            ////////////////
            // Selection Listeners
            trialList.addListSelectionListener(new ListSelectionListener()
            {
                public void valueChanged(ListSelectionEvent e) 
                {
                    // Update trial fields and frame.
                    updateSelections();
                    InfantFrame.this.update();
                }
            });

            // Labels displayed on the left column
            trialLabel = new JLabel("Trials");

            ///////////////
            // Layout position
            this.setLayout(new GridBagLayout());
            GridBagConstraints layoutConst = new GridBagConstraints();
            layoutConst.insets = new Insets(30, 30, 30, 30);

            /** Left Column  */
            // Adding trial label
            layoutConst.gridx = 0;
            layoutConst.gridy = 0;
            add(trialLabel, layoutConst);
            
            /** Right column  */
            // Adding trial scroller
            layoutConst.gridx = 1;
            layoutConst.gridy = 0;
            add(trialScroller, layoutConst);
            
            // Background color of the panel
            this.setBackground(Color.PINK);

            /////////////////////////
            // Set the names of the key object: don't change these
            this.trialList.setName("TrialList");
            /////////////////////////
        }

        /**
         * Update the entire selection panel based on the currently-loaded infant.
         * 
         * This method is generally called when a new infant is loaded.
         */
        private void updateSelections()
        {
            ////////////////
            // Trial list
            
            // Clear all elements
            this.trialListModel.clear();
            
            // Adding weeks in string
            for (Trial trial : infant)
            {
                this.trialListModel.addElement(trial.toString());
            }
        }
    }

    ///////////////////////////////////////////////////////////////////
    /**
     * DataPanel: display selection information and statistics
     * 
     * @author CS2334, modified by Tony Nguyen and Michael Morgan
     * @version 11-15-2017
     */

    private class DataPanel extends JPanel
    {
        /** Serial ID */
        private static final long serialVersionUID = 1L;
        /** Instance of abstract class that renders infant */
        private KinematicPointAbstract rootPoint;
        /** The display users can see */
        private JPanel viewPanel;
        /** Top View */
        private KinematicPanel topViewPanel;
        /** Side View */
        private KinematicPanel sideViewPanel;
        /** Rear View */
        private KinematicPanel rearViewPanel;
        /** Text Panel */
        private JPanel textPanel;
        /** The Infant ID displayed */
        private JTextField infantTextField;
        /** The current time */
        private JTextField timeTextField;
        
        /** The slider bar to step through time */
        private JSlider timeSlider;
        /** Displays the current time */
        private int currentTime;
        /** The run button */
        private JButton runButton;
        /** The time panel */
        private JPanel timePanel;
        /** The timer object that drives animation of the infant by incrementing currentTime */
        private Timer timer;
        /** Field width of the textfields */
        private static final int FIELD_WIDTH = 10;
        /** Line width of the line segments in the kinematic panels */
        private static final int LINE_WIDTH = 10;
        
        /**
         * Constructor
         * 
         * Create and lay out the data display panel
         */
        private DataPanel()
        {
            
        }
        
        /**
         * 
         * @param newTime
         */
        public void setTime(int newTime)
        {
            this.currentTime = newTime;
        }

        /**
         * 
         * @param state
         */
        public void update(State state)
        {
            
        }
        
        /**
         * 
         * @return
         */
        public KinematicPointAbstract createKinematicModel()
        {
            
        }
    }

    ///////////////////////////////////////////////////////////////////
    // InfantFrame definition

    /**
     * InfantFrame constructor
     * 
     * Two frames are side-by-side: SelectionPanel on the left and DataPanel on the right.
     */
    public InfantFrame()
    {
        super("Infant Explorer");
        
        /** Menu bar */
        FileMenuBar fileMenuBar;
        // Configure the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);

        // Menu bar
        fileMenuBar = new FileMenuBar();
        this.setJMenuBar(fileMenuBar);

        // GridBagLayout for the contents
        this.setLayout(new GridBagLayout());

        GridBagConstraints layoutConst = new GridBagConstraints();
        layoutConst.insets = new Insets(10, 10, 10, 10);
        
        // Display Selection panel
        layoutConst.gridx = 0;
        layoutConst.gridy = 0;
        this.selectionPanel = new SelectionPanel();
        this.add(this.selectionPanel, layoutConst);

        // Display Data panel that is the rendering graphics of the infant
        layoutConst.gridx = 1;
        layoutConst.gridy = 0;
        this.dataPanel = new DataPanel();
        this.add(this.dataPanel, layoutConst);
        
        // Make the frame visible
        this.setVisible(true);
        
        // Compress the size of all objects
        this.pack();
    }

    /**
     * Load a new Infant.
     * 
     * This method is declared as "synchronized" to ensure that only one thread is allowed to call it
     * and other synchronized methods at once.
     * 
     * @param directory Directory in which the infant data files (csv) are located 
     * @param infantID ID of the infant to be loaded
     * @throws IOException Thrown if an error occurs while reading the file
     */
    public synchronized void loadData(String directory, String infantID) throws IOException
    {
        // Creating a new Infant.
        infant = new Infant(directory, infantID);
        selectionPanel.updateSelections();
    }

    /**
     * Translate the selections made in the SelectionPanel into a set of Strings to
     * be displayed in the DataPanel.
     * 
     * This method is declared as "synchronized" to ensure that only one thread is allowed to call it
     * and other synchronized methods at once.
     * 
     * One of the challenges in implementing this method is that it can be called at any time.  In 
     * particular, the structures that it is referencing may be in the process of being updated.  So,
     * we cannot assume that things like "selected values" are not actually set to something interesting
     * or useful.
     */
    public synchronized void update()
    {
        // Which weeks have been selected?
        int[] indices = selectionPanel.trialList.getSelectedIndices();

        // Default values for the displayed Strings
        String infantID = "n/a";
        String maxStateString = "n/a";
        String maxStateWeekString = "n/a";
        String maxStateTimeString = "n/a";

        String minStateString = "n/a";
        String minStateWeekString = "n/a";
        String minStateTimeString = "n/a";

        String averageString = "n/a";

        String fieldName = "n/a";
        String subfieldName = "n/a";

        // Does the infant object exist and does it have at least one week?
        if (infant != null && infant.getSize() > 0)
        {
            // Create an Infant object with only the selected weeks to the new Infant constructor
            Infant subInfant = new Infant(this.infant, indices);
            
            // Extract the infant ID
            infantID = infant.getInfantID();

            // Which field has been selected?
            fieldName = selectionPanel.fieldList.getSelectedValue();

            // Does this field exist and is it not empty
            if (fieldName != null && !fieldName.equals(""))
            {
                // Check subfield not null
                if (subfieldName != null && !subfieldName.equals(""))
                {
                    // Which subfield has been selected?
                    subfieldName = selectionPanel.subfieldList.getSelectedValue();
                    // What is the max state?
                    maxStateString = subInfant.getMaxState(fieldName, subfieldName).toString();
                    // What is the max state week?
                    maxStateWeekString = subInfant.getMaxState(fieldName, subfieldName).getTrial().toString();
                    // What is the max state time?
                    maxStateTimeString = subInfant.getMaxState(fieldName, subfieldName).getValue(
                            "Time", subfieldName).toString();
                    // What is the min state?
                    minStateString = subInfant.getMinState(fieldName, subfieldName).toString();
                    // What is the min state week?
                    minStateWeekString = subInfant.getMaxState(fieldName, subfieldName).getTrial().toString();
                    // What is the min state time?
                    minStateTimeString = subInfant.getMaxState(fieldName, subfieldName).getValue(
                            "Time", subfieldName).toString();
                    // What is the average?
                    averageString = subInfant.getAverageValue(fieldName, subfieldName).toString();
                }
            }
            else
            {
                // Indicate no meaningful fieldName selected
                fieldName = "n/a";
            }
        }

        // Tell the data panel to update
        this.dataPanel.update();
    }
}