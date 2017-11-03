/**
 * Captures both a double value and whether or not it is valid
 * 
 * @author Tony Nguyen and Dustin Sengkhamvilay
 * @version 2017-10-09
 *
 */
public class GeneralValue
{
    /** Is the value valid? */
    private boolean valid = true;
    
    /** Double value stored. */
    private double doubleValue;

    /**
     * Default constructor for GeneralValue that sets valid to false.
     */
    public GeneralValue() 
    {
        valid = false; 
    }
    
      /**
     * Constructor with a string parameter that is data taken from a file.
     * @param stringValue value at index i
     */
    public GeneralValue(String stringValue) 
    {
        // If stringValue is NaN then it is not a value.
        if (stringValue.equals("NaN"))  
        {
            valid = false;
        }
        else 
        { 
            doubleValue = Double.parseDouble(stringValue); 
        }
    }
    
    /**
     * Constructor with double value that is data taken from a file.
     * @param doubleValue is the value extracted from the file that is being tested.
     */
    public GeneralValue(Double doubleValue) 
    {
        this.doubleValue = doubleValue;   
        
        if (doubleValue.isNaN()) 
        {
            valid = false;
        }
    }
    
    /**
     * Get true or false if the string is valid.
     * @return valid
     */
    public boolean isValid()
    {
        return valid; 
    }
    
    /**
     * Gets the double value.
     * @return doubleValue
     */
    public double getDoubleValue()
    {
        // Making sure it is valid.
        if (!this.isValid())
        {
            throw new InvalidValueException("The generalValue is not valid.");
        }
        return doubleValue;
    }
    
    /**
     * We are checking if the passing value is less than the current value.
     * @param value is the passing generalValue.
     * @return the value 
     */
    public boolean isLessThan(GeneralValue value) 
    {
        // Try to see if passing value is less than current value.
        try
        {
            // If passing is less than current then return current value. 
            return (this.getDoubleValue() < value.getDoubleValue());
        }
        // This is if we catch a NaN trying to compare with a value.
        catch (InvalidValueException e)
        {
            return this.isValid();
        }
    }
    
    /**
     * We are checking if the passing value is greater than the current value.
     * @param value is the passing GeneralValue.
     * @return value or isValid
     */
    public boolean isGreaterThan(GeneralValue value) 
    {
        // Try to see if passing value is greater than current value.
        try
        {
            // If passing is greater than current then return passing value.
            return (this.getDoubleValue() > value.getDoubleValue());
        }
        // This is if we catch a NaN trying to compare with a value.
        catch (InvalidValueException e)
        {
            return this.isValid();
        }
    }
    
    /**
     * toString for the object that returns value for the user.
     * @return out
     */
    public String toString() 
    {
        // Format the string.
        String out = String.format("%.3f", doubleValue); 
        
        if (!valid) 
        {
            out = "invalid"; 
        }
        return out;
    }
}