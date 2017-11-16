import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 * An abstract class that maps the infant's movements.
 * 
 * @author Tony Nguyen and Michael Morgan
 * @version 11/15/2017
 */
public abstract class KinematicPointAbstract 
{
    /** The child kinematic point  */
    private ArrayList<KinematicPointAbstract> children; 
    /** Color  */
    private Color color;
    /** The thing that draws the line from one kinematic point to another  */
    private BasicStroke stroke;
    /** Defines the relationship between meters and pixels  */
    private static double scale;
    
    /**
     * Default constructor for KinematicPointAbstract.
     * @param color Color of the line.
     * @param width Width of the line.
     */
    public KinematicPointAbstract(Color color, float width) 
    {
        this.color = color;
    }
    
    /**
     * Adds new KinematicPointAbstract object as a child object.
     * @param child Is the object we add to the parent class.
     */
    public void addChild(KinematicPointAbstract child)
    {
        
    }
    
    /**
     * Takes inputs of graphics, state, and state's x and y dimensions to render on a 2D screen.
     * @param g The Graphics.
     * @param state Current state to render.
     * @param screenXSubfield Mapping the x dimensions from the current State.
     * @param screenYSubfield Mapping the y dimensions from the current State.
     */
    public void draw(Graphics2D g, State state, String screenXSubfield, String screenYSubfield)
    {
        
    }
    
    /**
     * Set the current scale to compare meters to pixels.
     * The measurements of the infants are in meters and this will allow us to convert to pixels to map the infant
     * movements.
     * @param scale The relationship between meters and pixels.
     */
    public static void setScale(double scale)
    {
        this.scale = scale;
    }
    
    /**
     * Gets a GeneralValue corresponding to the subfield name mapped on the screen.
     * @param state Current State.
     * @param screenSubfield Subfield we are looking for on screen.
     * @return 
     */
    public GeneralValue getScreenCoordinate(State state, String screenSubfield)
    {
        
    }
}
