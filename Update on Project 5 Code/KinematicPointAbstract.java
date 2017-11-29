import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Line2D;
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
    private static double scale = 10;
    
    /**
     * Default constructor for KinematicPointAbstract that contains instructions on how to draw a line segment.
     * @param color Color of the line.
     * @param width Width of the line.
     */
    public KinematicPointAbstract(Color color, float width) 
    {
        // Initializes color.
        this.color = color;
        // Initializes children list.
        this.children = new ArrayList<KinematicPointAbstract>();
        // Basic stroke line with the line width.
        stroke = new BasicStroke(width);
    }
    
    /**
     * Adds new KinematicPointAbstract object as a child object.
     * @param child Is the object we add to the children arraylist.
     */
    public void addChild(KinematicPointAbstract child)
    {
        children.add(child);
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
        // Extracting the current dimensions from state's x and y values. Starting point.
        GeneralValue currentPointX = getScreenCoordinate(state, screenXSubfield);
        GeneralValue currentPointY = getScreenCoordinate(state, screenYSubfield);
        
        // For every child point.
        for (KinematicPointAbstract childPoint: children)
        {
            // Extract points that correspond to the x and y screen coordinates. Ending point.
            GeneralValue childPointX = childPoint.getScreenCoordinate(state, screenXSubfield);
            GeneralValue childPointY = childPoint.getScreenCoordinate(state, screenYSubfield);
            
            // Checking if all 4 values are valid.
            if (currentPointX.isValid() && currentPointY.isValid() && childPointX.isValid() && childPointY.isValid())
            {
                // Transform values into pixel coordinates multipling by scale and drawing a line.
                Shape line = new Line2D.Double(currentPointX.getDoubleValue() * scale, currentPointY.getDoubleValue() * scale, 
                        childPointX.getDoubleValue() * scale, childPointY.getDoubleValue() * scale);
                
                // Draw line between the two points. Use basic stroke. line2d
                g.setStroke(stroke);
                g.draw(line);
            }
            
            // Recursively draw the child points.
            childPoint.draw(g, state, screenXSubfield, screenYSubfield);
        }
    }
    
    /**
     * Set the current scale to compare meters to pixels.
     * The measurements of the infants are in meters and this will allow us to convert to pixels
     * to map the infant movements.
     * @param newScale The relationship between meters and pixels.
     */
    public static void setScale(double newScale)
    {
        scale = newScale;
    }
    
    /**
     * Gets a GeneralValue corresponding to the subfield name mapped on the screen.
     * @param state Current State.
     * @param screenSubfield Subfield we are looking for on screen.
     * @return The GeneralValue corresponding to a specified subfield.
     */
    public abstract GeneralValue getScreenCoordinate(State state, String screenSubfield);
}
