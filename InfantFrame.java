import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

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
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Graphical user interface for interacting with Infant data
 * 
 * @author CS2334, modified by Tony Nguyen and Michael Morgan
 * @version 11-19-2017
 *
 */
public class InfantFrame extends JFrame
{
    /** Serializable support.  */
    private static final long serialVersionUID = 1L;

    /** Panel for selecting the station, statistic, and Year */
    private SelectionPanel selectionPanel;

    /** Panel for displaying statistic */
    private DataPanel dataPanel;

    /** Infant that is currently loaded.  */
    private Infant infant;

    /** Currently selected trial.  */
    private Trial trial;

    /** Font used for labels and JLists.  */
    private static final Font FONT = new Font(Font.SANS_SERIF, Font.BOLD, 18);

    ///////////////////////////////////////////////////////////////////
    /**
     * 
     * @author CS2334, modified by Tony Nguyen and Michael Morgan
     * @version 11/19/2017
     * 
     * Menu bar that provides file loading and program exit capabilities.
     *
     */
    private class FileMenuBar extends JMenuBar 
    {
        /** Serializable support.  */
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
        public FileMenuBar() {
            // Create the menu and add it to the menu bar
            menu = new JMenu("File");
            add(menu);

            // Create the menu items and add them to the menu
            menuOpen = new JMenuItem("Open Configuration File");
            menuOpen.setName("MenuOpen");
            menuExit = new JMenuItem("Exit");
            menu.add(menuOpen);
            menu.add(menuExit);

            // Action listener for exit
            menuExit.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Exit the program
                    System.exit(0);
                }
            });

            // Filter for the file chooser: we only want files ending in '.dat'
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Configuration Files", "dat");

            // Create the file chooser: the default is to use the data directory
            fileChooser = new JFileChooser(new File("./data"));
            fileChooser.setFileFilter(filter);

            // Action listener for file open
            menuOpen.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Ask for a file
                    int returnVal = fileChooser.showOpenDialog(menuOpen);
                    // Was a file specified?
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        // Yes
                        // Extract the file that was selected
                        File file = fileChooser.getSelectedFile();
                        try {
                            // Set to a "busy" cursor
                            InfantFrame.this.setCursor(
                                    Cursor.getPredefinedCursor(
                                            Cursor.WAIT_CURSOR));

                            // Save the components of the selected file
                            String directory = file.getParent(); 
                            String fname = file.getName();

                            // Do the loading work: the first two characters are the Infant ID
                            InfantFrame.this.loadData(directory, fname.substring(0, 2));

                            // Return the cursor to standard form
                            InfantFrame.this.setCursor(null);

                            // Did we load any data?
                            if (InfantFrame.this.infant.getSize() == 0)
                            {
                                // No data loaded
                                JOptionPane.showMessageDialog(fileChooser, "No data found");
                            }

                            // Update the display
                            InfantFrame.this.update();

                        }
                        catch (IOException e2)
                        {
                            // Catch IO errors
                            JOptionPane.showMessageDialog(fileChooser, 
                                    "File load error");
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
     * @version 11/19/2017
     * 
     * Selection panel: contains JLists for the list of trials
     *
     */
    private class SelectionPanel extends JPanel
    {
        /** Serializable support.  */
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
         * Creates a 1x2 grid of components with labels down the left column and Jlist down the 2nd column
         */
        private SelectionPanel()
        {
            ////////////////////////////
            // Create the JList for trial selection
            // List model contains the data to be displayed.  Uses Trial.toString() to obtain strings
            trialListModel = new DefaultListModel<String>();
            // JList for trials
            trialList = new JList<String>(trialListModel);
            // Multiple items can be selected at once
            trialList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            // Vertically organized list with an arbitrary number of rows
            trialList.setVisibleRowCount(-1);
            trialList.setLayoutOrientation(JList.VERTICAL);
            // Scroll pane goes around the JList
            trialScroller = new JScrollPane(trialList);
            trialScroller.setPreferredSize(new Dimension(300, 100));
            trialList.setFont(FONT);

            ////////////////
            // Selection Listeners

            // Trial list
            trialList.addListSelectionListener(new ListSelectionListener()
            {
                public void valueChanged(ListSelectionEvent e) {
                    InfantFrame.this.update();
                }
            });


            // Labels
            trialLabel = new JLabel("Trials");
            trialLabel.setFont(FONT);

            ///////////////
            // Layout
            this.setLayout(new GridBagLayout());
            GridBagConstraints layoutConst = new GridBagConstraints();

            // Trial list
            layoutConst.gridx = 0;
            layoutConst.gridy = 0;
            layoutConst.insets = new Insets(10, 10, 10, 10);
            this.add(trialLabel, layoutConst);


            layoutConst.gridx = 1;
            layoutConst.gridy = 0;
            this.add(trialScroller, layoutConst);

            // Background color of the panel
            this.setBackground(new Color(200, 220, 200));

            // ///////////////////////
            // Set the names of the key objects: don't change these
            this.trialList.setName("TrialList");
            // ///////////////////////
        }

        /**
         * Update the entire selection panel based on the currently-loaded infant.
         * 
         * This method is called when a new infant is loaded
         * 
         */
        private void updateSelections()
        {
            ////////////////
            // Trial list

            // Clear all elements
            this.trialListModel.clear();

            if (infant != null)
            {
                // Loop through every trial & add it to the list model
                for (Trial t: infant)
                {
                    this.trialListModel.addElement(t.toString());
                }
            }
        }
    }

    ///////////////////////////////////////////////////////////////////
    /**
     * DataPanel: display selection information and statistics
     * 
     * @author CS2334, modified by Tony Nguyen and Michael Morgan
     * @version 11/19/2017
     */

    public class DataPanel extends JPanel
    {
        /** Serializable support.  */
        private static final long serialVersionUID = 1L;
        
        /** Root point for the kinematic tree */
        private KinematicPointAbstract rootPoint;
        
        /** Panel for all kinematic views.  */
        private JPanel viewPanel;
        /** Panels for top kinematic view.  */
        private KinematicPanel topViewPanel;
        /** Panels for side kinematic view.  */
        private KinematicPanel sideViewPanel;
        /** Panels for rear kinematic view.  */
        private KinematicPanel rearViewPanel;
        /** Panel for textual data.  */
        private JPanel textPanel;
        /** Infant ID display */
        private JTextField infantTextField;
        /** Current time index display */
        private JTextField timeTextField;


        /** Slider for showing/setting current time index */
        private JSlider timeSlider;
        /** Current time index.  */
        private int currentTime = 0;
        /** Button for start/stop of animation. */
        private JButton runButton;
        /** Panel for containing button and slider.  */
        private JPanel timePanel;
        /** Animation timer.  */
        private Timer timer;

        /** Width of text fields.  */
        private static final int FIELD_WIDTH = 30;
        
        /** Width of lines in kinematic models */
        public static final int LINE_WIDTH = 3;


        /**
         * Constructor
         * 
         * Create and lay out the data display panel
         */
        private DataPanel()
        {
            // Background color of the panel
            this.setBackground(new Color(200, 200, 230));
            //this.rootPoint = createKinematicModel();
            KinematicPointAbstract.setScale(300.0);

            ////////////////////////////////////
            // Time slider
            this.timeSlider = new JSlider(JSlider.HORIZONTAL, 0, 15000, 0);
            this.timeSlider.setMajorTickSpacing(1000);
            this.timeSlider.setMinorTickSpacing(100);
            this.timeSlider.setPaintTicks(true);
            this.timeSlider.setPaintLabels(true);
            this.timeSlider.setPreferredSize(new Dimension(800, 100));

            // Start/stop button
            this.runButton = new JButton("Start");
            // Time panel
            this.timePanel = new JPanel();

            /////////////////////////////////////////
            // Text panel will contain the infant ID and the current time step
            this.textPanel = new JPanel();
            this.infantTextField = new JTextField(FIELD_WIDTH);
            this.infantTextField.setEditable(false);
            this.infantTextField.setFont(FONT);
            this.timeTextField = new JTextField(FIELD_WIDTH);
            this.timeTextField.setEditable(false);
            this.timeTextField.setFont(FONT);
            this.textPanel.setLayout(new GridLayout(0, 1));
            this.textPanel.add(infantTextField);
            this.textPanel.add(timeTextField);
            this.textPanel.setPreferredSize(new Dimension(300, 70));

            ///////////////////////////////////////
            // Timer 
            this.timer = new Timer(5, new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e)
                {
                    // Set the current time to one higher than its current value
                    currentTime += 1;
                }

            });

            ///////////////////////////////////////
            // Layout for the time panel
            this.timePanel.setLayout(new GridBagLayout());

            GridBagConstraints layoutConst = new GridBagConstraints();
            layoutConst.insets = new Insets(10, 10, 10, 10);

            //////////////
            // Layout: Time panel in the south area
            this.setLayout(new BorderLayout());
            this.add(this.timePanel, BorderLayout.SOUTH);

            layoutConst.gridx = 0;
            layoutConst.gridy = 0;
            this.timePanel.add(this.runButton, layoutConst);

            layoutConst.gridx++;
            this.timePanel.add(this.timeSlider, layoutConst);


            //////////////////////////////////////////
            // Layout: View panel in the center
            this.viewPanel = new JPanel();
            this.viewPanel.setLayout(new GridBagLayout());
            this.add(this.viewPanel, BorderLayout.CENTER);

            // Sub-panels: 3 different views + the text panel
            // STUDENT
            this.topViewPanel = new KinematicPanel(rootPoint, 1.0, -1.0, "x", "y", "Top View");
            this.sideViewPanel = new KinematicPanel(rootPoint, 1.0, -1.0, "x", "z", "Side View");
            this.rearViewPanel = new KinematicPanel(rootPoint, -1.0, -1.0, "y", "z", "Rear View");

            // Top view
            layoutConst.gridx = 1;
            layoutConst.gridy = 0;
            this.viewPanel.add(this.topViewPanel, layoutConst);

            // Side view
            layoutConst.gridx = 1;
            layoutConst.gridy = 1;
            this.viewPanel.add(this.sideViewPanel, layoutConst);

            // Rear view
            layoutConst.gridx = 0;
            layoutConst.gridy = 0;
            this.viewPanel.add(this.rearViewPanel, layoutConst);

            // Text Field
            layoutConst.gridx = 0;
            layoutConst.gridy = 1;
            this.viewPanel.add(this.textPanel, layoutConst);


            //////////////////////////////////////////////////////////////////
            // Slider change listener: translate the time step into the display
            this.timeSlider.addChangeListener(new ChangeListener() {

                @Override
                public void stateChanged(ChangeEvent e)
                {
                    // Set the current time to match the slider time
                    DataPanel.this.setTime(DataPanel.this.timeSlider.getValue());
                }

            });

            ///////////////////////////
            // Button action listener: used to start/stop the animation
            this.runButton.addActionListener(new ActionListener()  {

                @Override
                public void actionPerformed(ActionEvent e)
                {
                    // If the timer is running and the user wants to stop it.
                    if (timer.isRunning()) 
                    {
                        // Change button text to stop and timer stops running.
                        runButton.setText("Stop");
                        timer.stop();
                    }
                    // If the timer is stopped and user wants to run it again.
                    if (!timer.isRunning())
                    {
                        // Change button text to start and timer runs.
                        runButton.setText("Start");
                        timer.start();
                    }
                }

            });

            // ///////////////////////
            // Set the names of the key objects: don't change these
            this.infantTextField.setName("infantIDField");
            this.timeTextField.setName("timeField");
            this.runButton.setName("runButton");
            this.timeSlider.setName("timeSlider");
            // ///////////////////////
        }

        /**
         * Set the current time for the animation.
         * 
         * Notes:
         * - Deal with the case that 'trial' is null
         * - If the new time is within allowable range, then use that 
         *     as the current time, set the timeSlider to this time,
         *     extract the state for the current time and force an update
         *     of the display
         * - If the new time is outside of the allowable range, then ensure 
         *     that the timer is off 
         * 
         * 
         * @param newTime The new time step
         */
        public void setTime(int newTime)
        {
            // Do we have a trial?
            if (InfantFrame.this.trial != null)
            {
                // Is the new time within 0 to 15000 range?
                if (newTime >= 0 && newTime < 15000)
                {
                    // Set the currentTime to the newTime.
                    this.currentTime = newTime;
                    // Set the timeSlider to the new time.
                    this.timeSlider.setValue(currentTime);
                    // TODO Extract state for current time and force an update on display.
                    State state = new State();
                    update(state);
                }
                // Else if new time is not in allowable range then timer is off.
                else
                {
                    timer.stop();
                }
            }
        }


        /**
         * Create the full kinematic model of the infant.
         * 
         * @return The root of the kinematic tree
         */
        public KinematicPointAbstract createKinematicModel()
        {
            // TODO: implement. Root at point (0, 0, 0).
            KinematicPointConstant root = new KinematicPointConstant(new Color(252, 24, 24), 
                    LINE_WIDTH, 0, 0, 0);

            return root;
        }

        /**
         * Update the data display
         * - Each of the kinematic panels
         * - Each of the text fields
         * - Force all components to redraw
         * 
         * @param state The current state
         */
        private void update(State state)
        {
            // TODO: implement
            state = new State();
            state.
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

        // Menu bar
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
        layoutConst.gridx = 0;
        layoutConst.gridy = 0;
        layoutConst.insets = new Insets(10, 10, 10, 10);

        // Selection panel
        this.selectionPanel = new SelectionPanel();
        this.add(this.selectionPanel, layoutConst);

        // Display panel
        this.dataPanel = new DataPanel();
        layoutConst.gridx = 1;
        this.add(this.dataPanel, layoutConst);

        // Make the frame visible
        this.setVisible(true);

        this.getContentPane().setBackground(new Color(200, 220, 200));

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
        // Create the new infant object
        infant = new Infant(directory, infantID);

        // Update the selection panel
        this.selectionPanel.updateSelections();

    }

    /**
     * Translate the selections made in the SelectionPanel into the configuration of the
     * dataPanel
     * 
     * This method is declared as "synchronized" to ensure that only one thread is allowed to call it
     * and other synchronized methods at once.
     * 
     * One of the challenges in implementing this method is that it can be called at any time.  In 
     * particular, the structures that it is referencing may be in the process of being updated.  So,
     * we cannot assume that things like "selected values" are not actually set to something interesting
     * or useful.
     * 
     */
    public synchronized void update()
    {
        // Selected trial
        int trialIndex = selectionPanel.trialList.getSelectedIndex();

        // Must have a valid infant with some trials
        if (infant != null && infant.getSize() > 0)
        {
            // Check to make sure that the selected trial is in the valid range
            if (trialIndex >= 0 && trialIndex < infant.getSize())
            {
                // Extract the trial
                this.trial = infant.getItem(trialIndex);
                // Reset the time index to the beginning (this will cause an update)
                this.dataPanel.setTime(0);

            }
        }
    }

    /**
     * Return a reference to the data panel.
     * 
     * FOR TESTING ONLY
     * 
     * @return the data panel.
     */
    public DataPanel getDataPanel()
    {
        return this.dataPanel;
    }
}
