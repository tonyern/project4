import org.junit.Assert;
import org.junit.Test;
/**
 * Test case for field class.
 * 
 * @author Tony Nguyen
 * @version 2017-10-17
 */
public class FieldTest 
{
    /**
     * Testing default Field constructor if it returns an empty field.
     */
    @Test
    public void testFieldConstructor()
    {
        Field field = new Field();
        Assert.assertEquals("", field.toString());
    }

    /**
     * Testing the add method if it puts items into the map.
     */
    @Test
    public void testAdd()
    {
        Field field = new Field();
        field.addSubField("# of Women", 69);
        // Testing to see if when we add a key "# of Women" with value 69 that it returns 69.
        Assert.assertEquals(69, field.getIndex("# of Women"), 0.00001);
    }

    /**
     * Testing get index if it returns the right item in index we expect.
     */
    @Test
    public void testGetIndex()
    {
        Field field = new Field();
        // Add stuff.
        field.addSubField("One", 1);
        field.addSubField("Two", 2);
        field.addSubField("Three", 3);
        // We add stuff to the map and now we see if it returns the correct value.
        Assert.assertEquals(2, field.getIndex("Two"), 0.00001);
        // Expect null if asked for subField that doesn't exist.
        Assert.assertEquals(null, field.getIndex("Four"));
    }

    /**
     * Testing get size.
     */
    @Test
    public void testSize()
    {
        // We create Field object and populate with three items.
        Field field = new Field();
        field.addSubField("One", 1);
        field.addSubField("Two", 2);
        field.addSubField("Three", 3);
        // We expect a size of 3.
        Assert.assertEquals(3, field.size());
    }

    /**
     * Testing toString if it returns if this format "SUBFIELD(INDEX) ".
     */
    @Test
    public void testToString()
    {
        // We create Field object and populate with three items.
        Field field = new Field();
        field.addSubField("One", 1);
        field.addSubField("Two", 2);
        field.addSubField("Three", 3);
        // We expect "One(1); Three(3); Two(2); ".
        Assert.assertEquals("One(1); Three(3); Two(2); ", field.toString());
    }
}