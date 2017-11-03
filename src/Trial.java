import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
/**
 * Representation of a single trial
 *
 * @author Tony Nguyen and Dustin Sengkhamvilay
 * @version 2017-10-09
 *
 */
public class Trial extends MultipleItemAbstract
{
    /** Sequence of states. */
    private ArrayList<State> stateList;
    
    /** The infant where the trial belongs to. */
    private Infant infant;
    
    /** Week index. */
    private int week;
    
    /** File name that was loaded. */
    private String fileName;
    
    /**
     * Trial constructor that takes in directory and ID and access and read data from a file.
     * 
     * @param infant to which the trial belongs to.
     * @param directory String representing the directory containing the data files.
     * @param infantID String representing the infant ID.
     * @param week integer week for the data file.
     * 
     * @throws IOException If there is an error finding or loading the data file.
     */
    public Trial(Infant infant, String directory, String infantID, int week) throws IOException 
    {
        // Set week.
        this.week = week;
        // Set and format the filename.
        this.fileName = String.format("%s/subject_%s_w%02d.csv", directory, infantID, week);
        
        // Set the infant where current trial belongs to.
        this.infant = infant;
        
        // New ArrayList.
        this.stateList = new ArrayList<State>();
        
        // Open the file.
        BufferedReader br = new BufferedReader(new FileReader(this.fileName));
        String strg;
        
        // Use the first line to construct field maps.
        strg = br.readLine();
        FieldMapper fieldMapper = new FieldMapper(strg.split(","));
        
        // Now read the first line of data.
        strg = br.readLine();
        
        // While there is a line of data.
        while (strg != null)  
        {
            // Add to the State with Trial object to our list.
            stateList.add(new State(this, fieldMapper, strg)); 
            strg = br.readLine();
        }
        // Close the file when finished reading all data.
        br.close();
    }

    /**
     * GetItem() method.
     * @param index passed
     * @return State at index
     */
    public State getItem(int index) 
    {
        return stateList.get(index);
    }
    
    /**
     * Gets the size of the array.
     * @return size
     */
    public int getSize() 
    {
        return stateList.size(); 
    }   

    /**
     * Get the current infant being tested and analyzed.
     * @return infant
     */
    public Infant getInfant()
    {
        return infant;
    }
    
    /**
     * Gets the week of the assignment.
     * @return week
     */
    public int getWeek()
    {
        return week;
    }
    
    /**
     * Returns the filename.
     * @return fileName
     */
    public String getFileName()
    {
        return fileName;
    }
    
    /**
     * Print out the current week.
     * @return week for example "Week 03" or "Week 10". 
     */
    public String toString()
    {
        int week = getWeek();
        // If week is less than 10 meaning one digit then return with a 0 in front of the week.
        if (week < 10)
        {
            return "Week 0" + week;
        }
        // Else then just return the week.
        else
        {
            return "Week " + week;
        }
    }
}