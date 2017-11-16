import java.awt.Color;

/**
 * Class that represents a location that does not change with time.
 * 
 * @author Tony Nguyen and Michael Morgan
 * @version 11/15/2017
 */
public class KinematicPointConstant extends KinematicPointAbstract
{
    /** PointND value  */
    private PointND point;
    
    /**
     * Default constructor for KinematicPointConstant.
     * @param color Color of the line.
     * @param width Width of the line.
     * @param x X value on the coordinates.
     * @param y Y value on the coordinates.
     * @param z Z value on the coordinates.
     */
    public KinematicPointConstant(Color color, float width, double x, double y, double z)
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