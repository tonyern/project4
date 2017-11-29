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
        // Gets info from the parent class.
        super(color, width);
        // Sets the field name.
        this.fieldName = fieldName;
    }
    
    /**
     * Gets the GeneralValue coordinates of the corresponding subfield in the current state.
     * @param state Current state.
     * @param screenSubfield Subfield we are looking for.
     * @return screenValueCoordinate The GeneralValue of the field and subfield.
     */
    public GeneralValue getScreenCoordinate(State state, String screenSubfield)
    {
        GeneralValue screenValueCoordinate = state.getValue(fieldName, screenSubfield);
        return screenValueCoordinate;
    }
}
