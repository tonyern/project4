import static org.junit.Assert.assertEquals;
import org.junit.Assert;
import org.junit.Test;
/**
 * Test case for PointND class.
 * We need a separate data file to test.
 * 
 * @author Tony Nguyen and Dustin Sengkhamvilay
 * @version 2017-10-18
 */
public class PointNDTest
{
    /**
     * Test default constructor if it set the collection map to empty.
     */
    @Test
    public void testPointNDConstructor()
    {
    	PointND pointND = new PointND();
        Assert.assertEquals("", pointND.toString());
    }

    /**
     * Testing the getValue method if it returns the correct corresponding value.
     */
    @Test
    public void testGetValue()
    {
        PointND pointND = new PointND();
        // Add stuff.
        pointND.add("One", new GeneralValue("1"));
        pointND.add("Two", new GeneralValue("2"));
        pointND.add("Three", new GeneralValue("3"));
        
        // Testing if it returns null if we ask for a field that doesn't exist.
        Assert.assertEquals(null, pointND.getValue("Four"));
        // Testing if it returns the correct value 2 for Two.
        assertEquals(new GeneralValue("1").getDoubleValue(), pointND.getValue("One").getDoubleValue(), 0.0001);
        assertEquals(new GeneralValue("2").getDoubleValue(), pointND.getValue("Two").getDoubleValue(), 0.0001);
        assertEquals(new GeneralValue("3").getDoubleValue(), pointND.getValue("Three").getDoubleValue(), 0.0001);
    }

    /**
     * Testing if it returns the correct size.
     */
    @Test
    public void testSize()
    {
        // We create a PointND object and add stuff to it.
        PointND pointND = new PointND();
        pointND.add("This", new GeneralValue("1"));
        pointND.add("Is", new GeneralValue("2"));
        pointND.add("A", new GeneralValue("3"));
        pointND.add("Test", new GeneralValue("4"));
        // We want it to return 4 as that is how much we populated it.
        Assert.assertEquals(4, pointND.size());
    }

    /**
     * Testing toString if it returns in this format "SUBFIELDNAME = VALUE; ".
     */
    @Test
    public void testToString()
    {
        // Creating our PointND object populating the collections.
        PointND pointND = new PointND();
        pointND.add("This", new GeneralValue("1"));
        pointND.add("Is", new GeneralValue("2"));
        pointND.add("A", new GeneralValue("3"));
        pointND.add("Test", new GeneralValue("4"));
        System.out.println(pointND.toString());
        // This puts string in alphabetic order stored. "A, Is, Test, This."
        Assert.assertEquals("A = 3.000; Is = 2.000; Test = 4.000; This = 1.000; ", pointND.toString());
    }
}
