/**
 * This class is a simple extension of the RuntimeException.
 * 
 * @author Tony Nguyen and Dustin Sengkhamvilay
 * @version 2017-10-09
 *
 */
@SuppressWarnings("serial")
public class InvalidValueException extends RuntimeException 
{
    /**
     * Simple extension of the RunTimeException.
     * @param message which is the error.
     */
    public InvalidValueException(String message) 
    {
    	System.out.println("Error, invalid value: " + message);
    }
}