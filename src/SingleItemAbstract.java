/**
 * Single Item Abstract class is the parent to all classes that compute statistics. 
 *
 * @author Tony Nguyen and Dustin Sengkhamvilay
 * @version 2017-10-18
 *
 */
public abstract class SingleItemAbstract 
{
    /**
     * Get the max of State.
     * @param fieldName this is like left_wrist.
     * @param subFieldName this is like x, y, and z values of left_wrist.
     * @return max of State.
     */
    public abstract State getMaxState(String fieldName, String subFieldName);

    /**
     * Get the minimum value of State.
     * @param fieldName this is like left_wrist.
     * @param subFieldName this is like x, y, and z values of left_wrist.
     * @return minimum value of State.
     */
    public abstract State getMinState(String fieldName, String subFieldName);

    /**
     * Get the average.
     * @param fieldName this is like left_wrist.
     * @param subFieldName this is like x, y, and z values of left_wrist.
     * @return average value in general value.
     */
    public abstract GeneralValue getAverageValue(String fieldName, String subFieldName);
}
