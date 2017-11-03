/**
* @author Dustin Sy Sengkhamvilay and Tony Nguyen
* @version 2017-10-23
 * 
 * MultipleItemAbstract Class - The heavy calculating is done within this class for the Max/Min/Avg values.
 * This class extends Single Item and also is able to obtain the size of the array and also obtain certain indexes.
 */
public abstract class MultipleItemAbstract extends SingleItemAbstract
{
    /**
     * @return Returns the size of the array.
     */
    public abstract int getSize()
    ;
    /**
     * @param index The specific location that the user wants to pull from the arrayList.
     * @return Returns the contents found in the parameters value.
     */
    public abstract SingleItemAbstract getItem(int index);
    
    /**
     * This method cycles through all the values of the given field and subfield
     * and then calculates and returns the state that contains the maximum value.
     * 
     * @param fieldName The field we are looking at (ie. right_wrist).
     * @param subFieldName The subfield of the field (ie. X/Y/Z).
     * @return Returns the State in which the max resides for the field and subfield.
     */
    public State getMaxState(String fieldName, String subFieldName)
    {
        //Sets the maximum value to negative infinity so that any value great can take its place.
        State finalMax = new State();
        finalMax.getPoint(fieldName).add(subFieldName, new GeneralValue(Double.NEGATIVE_INFINITY));
        //Holds the current value which is being tested.
        State holder = new State();
        //Holds the new GeneralValue being compared.
        GeneralValue holdNewGV = new GeneralValue();
        //Holds the current max GeneralValue being compared.
        GeneralValue holdMaxGV = new GeneralValue();
        holdMaxGV = finalMax.getValue(fieldName, subFieldName);
        //Loops for as long as the size of the array.
        for (int x = 0; x < getSize(); x++)
        {
            //Grabs the next candidate to compare towards the current max.
            holder = getItem(x).getMaxState(fieldName, subFieldName);
            holdNewGV = holder.getValue(fieldName, subFieldName);
            //Checks if it is greater than the previous value.
            if (holdNewGV.isGreaterThan(holdMaxGV))
            {
                //Sets the max to the new found max value as a GeneralValue Object.
                finalMax = holder;
                holdMaxGV = finalMax.getValue(fieldName, subFieldName);
            }
        }
        return finalMax;
    }
    

    /**
     * This method cycles through all the values of the given field and subfield
     * and then calculates and returns the state that contains the minimum value.
     * 
     * @param fieldName The field we are looking at (ie. right_wrist).
     * @param subFieldName The subfield of the field (ie. X/Y/Z).
     * @return Returns the State in which the min resides for the field and subfield.
     */
    public State getMinState(String fieldName, String subFieldName)
    {
        //Sets the minimum value to positive infinity so that any value less can take its place.
        State finalMin = new State();
        finalMin.getPoint(fieldName).add(subFieldName, new GeneralValue(Double.POSITIVE_INFINITY));
        //Holds the current value which is being tested.
        State holder = new State();
        //Holds the new GeneralValue being compared.
        GeneralValue holdNewGV = new GeneralValue();
        //Holds the current minimum GeneralValue to be compared.
        GeneralValue holdMinGV = new GeneralValue();
        holdMinGV = finalMin.getValue(fieldName, subFieldName);
        //Loops for as long as the size of the array.
        for (int x = 0; x < getSize(); x++)
        {
            //Grabs the next candidate to be compared to the minimum value.
            holder = getItem(x).getMinState(fieldName, subFieldName);
            holdNewGV = holder.getValue(fieldName, subFieldName);
            //Checks if it is lesser than the previous value.
            if (holdNewGV.isLessThan(holdMinGV))
            {
                //Sets the minimum to the new found minimum value as a GeneralValue object.
                finalMin = holder;
                holdMinGV = finalMin.getValue(fieldName, subFieldName);
            }
        }
        return finalMin;
    }
    
    /**
     * Obtains the average value for the field and subfield the user
     * is looking to obtain by adding all the values together and then
     * dividing it to obtain the average value.
     * 
     * @param fieldName The field we are looking at (ie. right_wrist).
     * @param subFieldName The subfield of the field (ie. X/Y/Z).
     * @return Returns the GeneralValue for the average value for the field and subfield.
     */
    public GeneralValue getAverageValue(String fieldName, String subFieldName)
    {
        //Holds the combined numbers.
        double avgLeft = 0.0;
        //Holds the completed average.
        double holdTotal = 0.0;
        //Holds the number of numbers combined.
        double countTotal = 0.0;
        //Holds the new GeneralValue to be added. 
        GeneralValue holdingGV = new GeneralValue();
        //Loops for as long as the size of the array.
        for (int x = 0; x < getSize(); x++)
        {
            //Grabs the next candidate to be added.
            holdingGV = getItem(x).getAverageValue(fieldName, subFieldName);
            //Checks if the number is valid.
            if (holdingGV.isValid())
            {
                //Adds the numbers together.
                avgLeft += (holdingGV.getDoubleValue());
                countTotal++;
            }
        }
        //Takes the average by dividing the total by the number of numbers.
        holdTotal = avgLeft / countTotal;
        return new GeneralValue(holdTotal);
    }
}