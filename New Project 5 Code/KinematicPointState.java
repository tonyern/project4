import java.awt.Color;

/**
 * Represents a point whose location is defined by a given field.
 * 
 * @author Tony Nguyen and Michael Morgan
 * @version 11/15/2017
 */
public class KinematicPointState extends KinematicPointAbstract
{
    /** Field name  */
    private String fieldName;
    
    /**
     * Default constructor for the KinematicPointState.
     * @param color Color of the line. 
     * @param width Width of the line.
     * @param fieldName Line corresponding to the fieldName given.
     */
    public KinematicPointState(Color color, float width, String fieldName) 
    {
        super(color, width);
    }
    
    /**
     * Gets the GeneralValue coordinates of the corresponding subfield in the current state.
     * @param state Current state.
     * @param screenSubfield Subfield we are looking for.
     * @return
     */
    public GeneralValue getScreenCoordinate(State state, String screenSubfield)
    {
        
    }
}
