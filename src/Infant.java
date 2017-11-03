import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
/**
 * Representation of a multiple trials.
 *
 * @author Tony Nguyen and Dustin Sengkhamvilay
 * @version 2017-10-09
 *
 */
public class Infant extends MultipleItemAbstract implements Iterable<Trial>
{
    /** List of trials data. */
    private ArrayList<Trial> trialList = new ArrayList<Trial>();
    
    /** Infant ID. */
    private String infantID;
    
    /** Max trial week. */
    private static final int MAX_WEEK = 16;
    
    /**
     * Default constructor takes in directory and infantID to create multiple trials to
     * compute and display statistics.
     * @param directory where the data came from.
     * @param infantID so we know which infant is which.
     * @throws IOException xx
     */
    public Infant(String directory, String infantID) throws IOException 
    {
        this.infantID = infantID;
        // Iterate through weeks.
        for (int i = 0; i < MAX_WEEK; i++) 
        {
            try 
            { 
                // Construct the trials.
                trialList.add(new Trial(this, directory, infantID, i));
            }
            catch (FileNotFoundException e) 
            {
                // Print this if we can't find a file.
                System.out.println("Week " + i + " for infant " + infantID + " not found.");
            }
        }
    }
    
    /**
     * Overloaded constructor. This constructor takes in an existing infant and an array of indices.
     * The new infant will have the same infant ID but will have the subset of the original infant's trials.
     * The subset is defined by the indices. Any illegal indices are ignored. 
     * @param infant is the existing infant.
     * @param indices is an array of values.
     */
    public Infant(Infant infant, int[] indices)
    {
        // No idea how to use this
    }
    
    /**
     * Gets the item in a specific trial index.
     * @param index index of item
     * @return Trial at index
     */
    public Trial getItem(int index) 
    {
        return trialList.get(index);
    }
    
    /**
     * Gets the size of the trial.
     * @return size of trial
     */
    public int getSize() 
    {
        return trialList.size();
    }
    
    /**
     * Gets the ID of the infant.
     * @return string infantID
     */
    public String getInfantID() 
    {
        return infantID;
    }
    
    /**
     * Iterate over the trials.
     */
    public Iterator<Trial> iterator()
    {
        return this.trialList.iterator();
    }
}