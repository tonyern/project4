import java.util.Iterator;
import java.util.TreeMap;
/**
 * Representation of the state of a single time step.
 * 
 * @author Tony Nguyen and Dustin Sengkhamvilay
 * @version 2017-10-09
 *
 */
public class State extends SingleItemAbstract implements Iterable<String>
{
    /**
     * Map that takes in string field and describe it to the corresponding PointND.
     */
    private TreeMap<String, PointND> variables;
    
    /**
     * Creation of the trials.
     */
    private Trial trial;
    
    /**
     * Default constructor creates an object with no fields.
     */
    public State()
    {
        variables = new TreeMap<String, PointND>();
    }

    /**
     * Main Constructor of State.
     * @param trial as input to which State it belongs to.
     * @param fieldMapper will tell us how to create fields from the data.
     * @param values describes the associated State.
     */
    public State(Trial trial, FieldMapper fieldMapper, String values)
    {
        // Referring to current State.
        this();
        // Setting the trial.
        this.trial = trial;
        
        // Creating a new list to store fields in with the map size.
        String[] stringValues = values.split(",");
        Iterator<String> loopingFields = fieldMapper.iterator();
        
        while (loopingFields.hasNext())
        {
            String tempValue = loopingFields.next();
            // Gets PointND object that tells us how to create fields from the data.
            variables.put(tempValue, fieldMapper.extractPointND(stringValues, tempValue));
        }
    }

    /**
     * Get the current trial.
     * @return trial
     */
    public Trial getTrial()
    {
        return trial;
    }

    /**
     * This returns the PointND object corresponding to the field name.
     * If field is not a part of the State then it is null.
     * @param fieldName is this like left_wrist.
     * @return variables.get(fieldName) as PointND.
     */
    public PointND getPoint(String fieldName)
    {
        // If it contains this field then returns the value.
        if (variables.containsKey(fieldName))
        {
            return variables.get(fieldName);
        }
        // If it doesn't contain the key then returns empty/null.
        else
        {
            return new PointND();
        }
    }
    
    /**
     * Gets the value corresponding to the fields and subfields name.
     * @param fieldName this is like left_wrist.
     * @param subFieldName this is like x, y, and z of left_wrist.
     * @return GeneralValue object.
     */
    public GeneralValue getValue(String fieldName, String subFieldName)
    {
        // If it contains this field then returns the GeneralValue which corresponds with it.
        if (variables.containsKey(fieldName))
        {  
            return variables.get(fieldName).getValue(subFieldName);
        }
        // If it doesn't contain the key then returns an invalid GeneralValue.
        else
        {
            return new GeneralValue();
        }
    }
    
    /**
     * Get the state with the highest value in each week.
     * @param fieldName this is like left_wrist.
     * @param subFieldName this is like x, y, and z of left_wrist.
     * @return Max of State
     */
    public State getMaxState(String fieldName, String subFieldName)
    {
        return this;
    }

    /**
     * Get the state with the lowest value in each week.
     * @param fieldName this is like left_wrist.
     * @param subFieldName this is like x, y, and z of left_wrist.
     * @return min of State
     */
    public State getMinState(String fieldName, String subFieldName)
    {
        return this;
    }

    /**
     * Get the average value of each week.
     * @param fieldName this is like left_wrist.
     * @param subFieldName this is like x, y, and z of left_wrist.
     * @return average of State
     */
    public GeneralValue getAverageValue(String fieldName, String subFieldName)
    {
        return this.getValue(fieldName, subFieldName);
    }

    /**
     * Iterators through the variables treeMap to get the field names.
     * It overrides the method defined in Iterator interface.
     * @return loopThrough
     */
    @Override
    public Iterator<String> iterator()
    {
        return this.variables.keySet().iterator();
    }

    /**
     * Prints the object at given index - "FIELDNAME(POINTND)\n"
     * @return String formatted output.
     */
    public String toString() 
    {
        String out = "";
        
        for (String fieldName : this)
        {
            out += fieldName + "(" + variables.get(fieldName).toString() + ")" + '\n';
        }
        
        return out;
    }
}