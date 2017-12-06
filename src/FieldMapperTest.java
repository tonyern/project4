import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
/**
 * Test case for our FieldMapper class.
 * 
 * @author Tony Nguyen
 * @version 2017-10-18
 */
public class FieldMapperTest 
{
    /**
     * Testing the constructor.
     * @throws IOException xx
     */
    @Test
    public void testConstructor() throws IOException
    {
        String testColumnHeaders = ("time,right_wrist_x,left_shoulder_z");
        FieldMapper testFieldMapper = new FieldMapper(testColumnHeaders.split(","));
        Assert.assertEquals(1, testFieldMapper.getField("right_wrist").getIndex("x").intValue());
        Assert.assertEquals("0", testFieldMapper.getField("time").getIndex("").toString());
    }

    /**
     * Testing getField method.
     */
    @Test
    public void testGetField()
    {
        String testColumnHeaders = ("time,right_wrist_z,left_shoulder_z");
        FieldMapper testFieldMapper = new FieldMapper(testColumnHeaders.split(","));
        Assert.assertEquals(1, testFieldMapper.getField("right_wrist").getIndex("z").intValue());
        Assert.assertEquals(2, testFieldMapper.getField("left_shoulder").getIndex("z").intValue());
    }

    /**
     * Testing extract pointND.
     */
    @Test
    public void testExtractPointND()
    {   
        // Creating the FieldMapper.
        String testColumnHeaders = ("time,right_wrist_x,left_shoulder_z");
        String testValues = ("0.02,0.238,2.11");
        FieldMapper testFieldMapper = new FieldMapper(testColumnHeaders.split(","));
        // Tests that the values match up with the values in PointND.
        Assert.assertEquals(0.02, testFieldMapper.extractPointND(testValues.split(","), 
                "time").getValue("").getDoubleValue(), 0.001);
        Assert.assertEquals(0.238, testFieldMapper.extractPointND(testValues.split(","), 
                "right_wrist").getValue("x").getDoubleValue(), 0.001);
    }

    /**
     * Testing size method.
     */
    @Test
    public void testSize()
    {
        String testColumnHeaders = ("time,right_wrist_x,left_shoulder_z");
        FieldMapper testFieldMapper = new FieldMapper(testColumnHeaders.split(","));
        Assert.assertEquals(3, testFieldMapper.size());
    }
    
    /**
     * Tests the iterator method.
     */
    @Test
    public void testIterator()
    {
        // Creating the FieldMapper.
        String testColumnHeaders = ("time,right_wrist_x,right_wrist_y,left_shoulder_z");
        FieldMapper testFieldMapper = new FieldMapper(testColumnHeaders.split(","));
        // Tests that it stores and has values.
        Assert.assertTrue(testFieldMapper.iterator().hasNext());
    }
}