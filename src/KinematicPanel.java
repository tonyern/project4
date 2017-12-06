import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 * Panel for drawing a single view of an infant.
 * 
 * @author CS2334, modified by Tony Nguyen and Michael Morgan
 * @version 2017-11-10
 */
public class KinematicPanel extends JPanel
{
    /** Serial ID  */
    private static final long serialVersionUID = 1L;
    /** Drawing flip direction for X: +/-1 because of changed coordinate system */
    private double flipX;
    /** Drawing flip direction for Y: +/-1 because of changed coordinate system */
    private double flipY;
    
    /** Subfield used for X dimension. */
    private String screenXSubfield;
    /** Subfield used for Y dimension. */
    private String screenYSubfield;
    /** Root of the kinematic tree that is the (0, 0, 0) origin of the body.  */
    private KinematicPointAbstract rootPoint;
    /** State to render.  */
    private State state;
    /** Panel title */
    private String title;
    /** Font used for panel title.  */
    private static final Font FONT = new Font("Times New Roman", Font.BOLD, 25);

    /**
     * Constructor for Kinematic Panel 
     * 
     * @param rootPoint Root of the kinematic tree for this panel
     * @param flipX Drawing direction for X dimension: +/- 1
     * @param flipY Drawing direction for Y dimension: +/- 1
     * @param screenXSubfield Subfield name for the X dimension
     * @param screenYSubfield Subfield name for the Y dimension
     * @param title Panel title
     */
    public KinematicPanel(
            KinematicPointAbstract rootPoint, 
            double flipX, double flipY, 
            String screenXSubfield, String screenYSubfield, String title)
    {
        super();
        this.flipX = flipX;
        this.flipY = flipY;
        this.rootPoint = rootPoint;
        this.screenXSubfield = screenXSubfield;         // X values of a field to render.
        this.screenYSubfield = screenYSubfield;         // Y values of a field to render.
        this.setPreferredSize(new Dimension(400, 200)); // Size of the view window.
        this.title = title;                             // Displays top, side, and rear view.
        
        // Set up the border for the panel that surrounds the top, side, and rear view.
        Border border = BorderFactory.createLineBorder(Color.black);
        this.setBorder(border);
    }

    /**
     * Set the state to render
     * 
     * @param state State to be rendered
     */
    public void setState(State state)
    {
        this.state = state;
        this.repaint();
    }

    /**
     * Render the panel
     * 
     * @param g Graphics context
     */
    protected void paintComponent(Graphics g)   
    {
        super.paintComponent(g);
        
        // Draw the title for the view panel like Top View, Rear View, and Side View.
        g.setFont(FONT);
        g.drawString(title, 25, 25);
        
        // Render as long as state is defined
        if (this.state != null)
        {
            Graphics2D g2 = (Graphics2D) g;
            
            // Translate the graphics context origin to the center of the panel
            g2.translate(this.getWidth() / 2, this.getHeight() / 2);
            // Flip the drawing directions
            g2.scale(flipX, flipY);
            
            // Draw the kinematic tree.
            rootPoint.draw(g2, state, screenXSubfield, screenYSubfield);
            
            // These next two lines make the border drawing work properly
            // Flip back
            g2.scale(flipX, flipY);
            // Translate the origin back 
            g2.translate(-this.getWidth() / 2, -this.getHeight() / 2);
        }
    }
}