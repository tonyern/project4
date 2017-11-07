import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.DefaultListModel;
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
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Graphical user interface for interacting with Infant data
 * 
 * @author Tony Nguyen
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
    /** Width of column 1 text fields in the data display */
    private final static int COLUMN_FIELD_WIDTH = 10;
    /** Infant that is currently loaded.  */
    private Infant infant;


    ///////////////////////////////////////////////////////////////////
    /**
     * 
     * @author CS2334, modified by Tony Nguyen
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
        public FileMenuBar() {
            // Create the menu and add it to the menu bar
            menu = new JMenu("File");
            // The main menu bar
            add(menu);
            // Create and add menu items
            // The exit sub item under menu.
            menu.add(menuExit);
            // The open file item under menu.
            menu.add(menuOpen);

            // Action listener for exit
            menuExit.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
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
                            Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);

                            // Save the components of the selected file
                            String directory = file.getParent();
                            String fname = file.getName();

                            // Do the loading work: the first two characters are the Infant ID
                            loadData(directory, fname);
                            
                            // Return the cursor to standard form
                            InfantFrame.this.setCursor(null);

                            // No data loaded then open a message dialog box to indicate an error
                            if (directory == null && fname == null)
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
     * @author CS2334, modified by Tony Nguyen
     * @version 2017-11-1
     * 
     * Selection panel: contains JLists for the list of trials, the list of fieldNames and the 
     * list of subfieldNames.  Note that the displayed subfieldNames is dependent on which 
     * field has been selected
     *
     */
    private class SelectionPanel extends JPanel
    {
        /** Serial ID */
        private static final long serialVersionUID = 1L;
		/** Selection of available trials/weeks.  */
        private JList<String> trialList;
        /** Selection of the field.  */
        private JList<String> fieldList;
        /** Selection of the subfield.  */
        private JList<String> subfieldList;

        /** List model for the trial list.  */
        private DefaultListModel<String> trialListModel;
        /** List model for the field list.  */
        private DefaultListModel<String> fieldListModel;
        /** List model for the subfield list.  */
        private DefaultListModel<String> subfieldListModel;

        /** Scroll pane: trial list  */
        private JScrollPane trialScroller;
        /** Scroll pane: field list  */
        private JScrollPane fieldScroller;
        /** Scroll pane: subfield list  */
        private JScrollPane subfieldScroller;

        /**  Trial selection label */
        private JLabel trialLabel;
        /** Field selection label */
        private JLabel fieldLabel;
        /** Subfield selection label */
        private JLabel subfieldLabel;

        /** FieldMapper for one of the trials.  */
        private FieldMapper fieldMapper;

        /**
         * Constructor
         * 
         * Creates a 3x2 grid of components with labels down the left column and Jlist down the 2nd column
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

            /////////////////////////////////////
            // JList for field selection
            // Model is of Strings
            fieldListModel = new DefaultListModel<String>();
            // JList for field
            fieldList = new JList<String>(fieldListModel);
            // Muliple items can be selected at once
            fieldList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
            fieldList.setVisibleRowCount(-1);
            fieldList.setLayoutOrientation(JList.VERTICAL);
            // Scroll pane goes around the JList
            fieldScroller = new JScrollPane(fieldList);
            fieldScroller.setPreferredSize(new Dimension(300, 100));
                        
            ////////////////////////////////////////
            // JList for Subfields
            // Model is of Strings
            subfieldListModel = new DefaultListModel<String>();
            // JList for subfield
            subfieldList = new JList<String>(subfieldListModel);
            // Muliple items can be selected at once
            subfieldList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
            subfieldList.setVisibleRowCount(-1);
            subfieldList.setLayoutOrientation(JList.VERTICAL);
            subfieldScroller = new JScrollPane(subfieldList);
            subfieldScroller.setPreferredSize(new Dimension(300, 100));

            ////////////////
            // Selection Listeners
            ListSelectionModel trialTable = trialList.getSelectionModel();
            trialTable.addListSelectionListener(new ListSelectionListener()
            {
                @Override
                public void valueChanged(ListSelectionEvent e) 
                {
                    if (!trialTable.isSelectionEmpty())
                    {
                        int selectedTable = trialTable.getMinSelectionIndex();
                        JOptionPane.showMessageDialog(null, "Selected Table " + selectedTable);
                    }
                }
            });
            
            ListSelectionModel fieldTable = fieldList.getSelectionModel();
            fieldTable.addListSelectionListener(new ListSelectionListener()
            {
                @Override
                public void valueChanged(ListSelectionEvent e) 
                {
                    if (!fieldTable.isSelectionEmpty())
                    {
                        int selectedTable = fieldTable.getMinSelectionIndex();
                        JOptionPane.showMessageDialog(null, "Selected Table " + selectedTable);
                    }
                }
            });
            
            ListSelectionModel subfieldTable = subfieldList.getSelectionModel();
            subfieldTable.addListSelectionListener(new ListSelectionListener()
            {
                @Override
                public void valueChanged(ListSelectionEvent e) 
                {
                    if (!subfieldTable.isSelectionEmpty())
                    {
                        int selectedTable = subfieldTable.getMinSelectionIndex();
                        JOptionPane.showMessageDialog(null, "Selected Table " + selectedTable);
                    } 
                }
            });

            // Labels displayed on the left column
            trialLabel = new JLabel("Trials");
            fieldLabel = new JLabel("Fields");
            subfieldLabel = new JLabel("Subfields");

            ///////////////
            // Layout position
            this.setLayout(new GridBagLayout());
            GridBagConstraints layoutConst = new GridBagConstraints();

            // TODO: Is the layout correct and positions.
            /** Left Column  */
            // Adding trial label
            layoutConst.gridx = 0;
            layoutConst.gridy = 0;
            add(trialLabel, layoutConst);
            // Adding field label
            layoutConst.gridx = 0;
            layoutConst.gridy = 1;
            add(fieldLabel, layoutConst);
            // Adding subfield label
            layoutConst.gridx = 0;
            layoutConst.gridy = 2;
            add(subfieldLabel, layoutConst);
            
            /** Right column  */
            // Adding trial list
            layoutConst.gridx = 1;
            layoutConst.gridy = 0;
            add(trialList, layoutConst);
            // Adding field list
            layoutConst.gridx = 1;
            layoutConst.gridy = 1;
            add(fieldList, layoutConst);
            // Adding subfield list
            layoutConst.gridx = 1;
            layoutConst.gridy = 2;
            add(subfieldList, layoutConst);
            
            // TODO: Background color of the panel
            this.setBackground(Color.GREEN);

            /////////////////////////
            // Set the names of the key objects: don't change these
            this.trialList.setName("TrialList");
            this.fieldList.setName("FieldList");
            this.subfieldList.setName("SubfieldList");
            /////////////////////////
        }

        /**
         * Update the entire selection panel based on the currently-loaded infant.
         * 
         * This method is generally called when a new infant is loaded
         */
        private void updateSelections()
        {
            ////////////////
            // Trial list
            
            // Clear all elements
            this.trialListModel.clear();
            
            // TODO: complete implementation
            
            
            /////////////////////
            // Field list

            // TODO: complete implementation (the fieldMapper will be useful here, if one exists)
            // Clear all elements
            this.fieldListModel.clear();
            
            // Update the subfields
            this.updateSubfieldSelections();
        }

        /**
         * Update subfield selection list based on the subfields available for the currently selected field
         * 
         * This method is called any time there is a change to the selected field.
         */
        private void updateSubfieldSelections()
        {
            // TODO: complete implementation
            // Clear all elements
            this.subfieldListModel.clear();

            // Tell the rest of the frame that it needs to update
            InfantFrame.this.update();
        }

    }

    ///////////////////////////////////////////////////////////////////
    /**
     * DataPanel: display selection information and statistics
     * 
     * @author CS2334, modified by Tony Nguyen
     * @version 2017-11-2
     */

    private class DataPanel extends JPanel
    {
        // Labels
        private JLabel infantIDLabel = new JLabel("Infant ID:");
        private JLabel fieldNameLabel = new JLabel("Field:");
        private JLabel subfieldNameLabel = new JLabel("Subfield:");
        private JLabel maxLabel = new JLabel("Max:");
        private JLabel maxWeekLabel = new JLabel("on");
        private JLabel maxTimeLabel = new JLabel("at");
        private JLabel minLabel = new JLabel("Min");
        private JLabel minWeekLabel = new JLabel("on");
        private JLabel minTimeLabel = new JLabel("at");
        private JLabel averageLabel = new JLabel("Average:");

        // JTextfield
        JTextField infantIDField = new JTextField();
        JTextField fieldNameField = new JTextField();
        JTextField subfieldNameField = new JTextField();
        JTextField maxValueField = new JTextField();
        JTextField maxWeekField = new JTextField();
        JTextField maxTimeField = new JTextField();
        JTextField minValueField = new JTextField();
        JTextField minWeekField = new JTextField();
        JTextField minTimeField = new JTextField();
        JTextField averageValueField = new JTextField();
        
        /**
         * Constructor
         * 
         * Create and lay out the data display panel
         */
        private DataPanel()
        {
            // Background color of the panel
            this.setBackground(new Color(200, 200, 230));

            // Set the text fields to be non-editable
            infantIDField.setEditable(false);
            fieldNameField.setEditable(false);
            subfieldNameField.setEditable(false);
            maxValueField.setEditable(false);
            maxWeekField.setEditable(false);
            maxTimeField.setEditable(false);
            minValueField.setEditable(false);
            minWeekField.setEditable(false);
            minTimeField.setEditable(false);
            averageValueField.setEditable(false);

            //////////////
            // TODO: Layout is it right and setting the positions
            this.setLayout(new GridBagLayout());
            GridBagConstraints layoutConst = new GridBagConstraints();
            
            /** Adding the labels.  */
            // Adding infantID label
            layoutConst.gridx = 0;
            layoutConst.gridy = 0;
            add(infantIDLabel, layoutConst);
            // Adding fieldname label
            layoutConst.gridx = 0;
            layoutConst.gridy = 0;
            add(fieldNameLabel, layoutConst);
            // Adding subfield label
            layoutConst.gridx = 0;
            layoutConst.gridy = 0;
            add(subfieldNameLabel, layoutConst);
            // Adding max label
            layoutConst.gridx = 0;
            layoutConst.gridy = 0;
            add(maxLabel, layoutConst);
            // Adding max week label
            layoutConst.gridx = 0;
            layoutConst.gridy = 0;
            add(maxWeekLabel, layoutConst);
            // Adding max time label
            layoutConst.gridx = 0;
            layoutConst.gridy = 0;
            add(maxTimeLabel, layoutConst);
            // Adding min label
            layoutConst.gridx = 0;
            layoutConst.gridy = 0;
            add(minLabel, layoutConst);
            // Adding min week label
            layoutConst.gridx = 0;
            layoutConst.gridy = 0;
            add(minWeekLabel, layoutConst);
            // Adding min time label
            layoutConst.gridx = 0;
            layoutConst.gridy = 0;
            add(minTimeLabel, layoutConst);
            // Adding average label
            layoutConst.gridx = 0;
            layoutConst.gridy = 0;
            add(averageLabel, layoutConst);
            
            /** Adding the textfield.  */
            // Adding infantID field
            layoutConst.gridx = 0;
            layoutConst.gridy = 0;
            add(infantIDField, layoutConst);
            // Adding field name
            layoutConst.gridx = 0;
            layoutConst.gridy = 0;
            add(fieldNameField, layoutConst);
            // Adding subfield
            layoutConst.gridx = 0;
            layoutConst.gridy = 0;
            add(subfieldNameField, layoutConst);
            // Adding max value
            layoutConst.gridx = 0;
            layoutConst.gridy = 0;
            add(maxValueField, layoutConst);
            // Adding max week
            layoutConst.gridx = 0;
            layoutConst.gridy = 0;
            add(maxWeekField, layoutConst);
            // Adding max time
            layoutConst.gridx = 0;
            layoutConst.gridy = 0;
            add(maxTimeField, layoutConst);
            // Adding min value
            layoutConst.gridx = 0;
            layoutConst.gridy = 0;
            add(minValueField, layoutConst);
            // Adding min week
            layoutConst.gridx = 0;
            layoutConst.gridy = 0;
            add(minWeekField, layoutConst);
            // Adding min time
            layoutConst.gridx = 0;
            layoutConst.gridy = 0;
            add(minTimeField, layoutConst);
            // Adding average value
            layoutConst.gridx = 0;
            layoutConst.gridy = 0;
            add(averageValueField, layoutConst);
            
            /////////////////////////////////////
            // Component names: DO NOT CHANGE THIS CODE
            infantIDField.setName("infantIDField");
            fieldNameField.setName("fieldNameField");
            subfieldNameField.setName("subfieldNameField");
            maxValueField.setName("maxValueField");
            maxWeekField.setName("maxWeekField");
            maxTimeField.setName("maxTimeField");
            minValueField.setName("minValueField");
            minWeekField.setName("minWeekField");
            minTimeField.setName("minTimeField");
            averageValueField.setName("averageValueField");
            /////////////////////////////////////
        }

        /**
         * Update the data display panel with new Strings
         * 
         * @param infantID Infant ID
         * @param fieldName Field name
         * @param subfieldName Subfield name
         * @param maxState Max value String
         * @param maxStateWeek Max value Week (Trial.toString())
         * @param maxStateTime Max value time
         * @param minState Min value String
         * @param minStateWeek Min value Week
         * @param minStateTime Min value time
         * @param average Average value
         */
        private void update(String infantID, String fieldName, String subfieldName,
                String maxState, String maxStateWeek, String maxStateTime,
                String minState, String minStateWeek, String minStateTime,
                String average)
        {
            // Set each of the text fields
            this.infantIDField = new JTextField(infantID);
            this.fieldNameField = new JTextField(fieldName);
            this.subfieldNameField = new JTextField(subfieldName);
            this.maxValueField = new JTextField(maxState);
            this.maxWeekField = new JTextField(maxStateWeek);
            this.maxTimeField = new JTextField(maxStateTime);
            this.minValueField = new JTextField(minState);
            this.minWeekField = new JTextField(minStateWeek);
            this.minTimeField = new JTextField(minStateTime);
            this.averageValueField = new JTextField(average);
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
        layoutConst.gridx = 0;
        layoutConst.gridy = 0;
        layoutConst.insets = new Insets(10, 10, 10, 10);
        
        // Selection panel
        this.selectionPanel = new SelectionPanel();
        this.add(this.selectionPanel, layoutConst);

        // Display Data panel
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
        // TODO: Creating a new Infant.
        Infant loadTrial = new Infant(directory, infantID);
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
            // Create an Infant object with only the selected weeks
            Infant subInfant = new Infant(this.infant, indices);
            
            // Extract the infant ID
            infantID = infant.getInfantID();

            // Which field has been selected?
            fieldName = selectionPanel.fieldList.getSelectedValue();

            // Does this field exist and is it not empty
            if (fieldName != null && !fieldName.equals(""))
            {
                // Which subfield has been selected?
                subfieldName = selectionPanel.subfieldList.getSelectedValue();
                
                // TODO: complete the setting of the defined Strings
                // Which field has been selected?
                fieldName = selectionPanel.fieldList.getSelectedValue();
            }
            else
            {
                // Indicate no meaningful fieldName selected
                fieldName = "n/a";
            }
        }

        // Tell the data panel to update
        this.dataPanel.update(infantID, fieldName, subfieldName,
                maxStateString, maxStateWeekString, maxStateTimeString,
                minStateString, minStateWeekString, minStateTimeString,
                averageString);
    }
}