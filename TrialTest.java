import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
/**
* Test class for Trial class.
* 
* @author Tony Nguyen and Michael Morgan
* @version 2017-10-23
* 
 */
public class TrialTest 
{
    /**
     * Tests that the constructor can accept a CSV without error.
     * @throws IOException Throws if it cannot grab data file.
     */
    @Test
    public void testTrialConstructor() throws IOException
    {	
        // Creates an object to see if it can be passed without error.
        Trial testTrial = new Trial(null, "mydata", "k3", 1);
        assertEquals(0.247648, testTrial.getItem(0).getValue("left_wrist", "z").getDoubleValue(), 0.001);
    }
    
    /**
     * Tests that getState can properly obtain specific values.
     * @throws IOException Throws if it cannot grab data file.
     */
    @Test
    public void testGetItem() throws IOException
    {
        // Creates Trial and fills it with data.
        Trial testTrial = new Trial(null, "mydata", "k3", 1);
        // Making sure it gets the correct data corresponding to the field and subfield.
        assertEquals(-0.018634, testTrial.getItem(0).getValue("right_wrist", "z").getDoubleValue(), 0.001);
    }

    /**
     * Tests getSize and whether the entire file is being read.
     * @throws IOException Throws if it cannot grab data file.
     */
    @Test
    public void testGetSize() throws IOException
    {
        // Creates Trial and fills it with data.
        Trial testTrial = new Trial(null, "mydata", "k3", 1);
        // Compares the size of the CSV file to intended.
        Assert.assertEquals(11, testTrial.getSize(), 0.0001);
    }

    /**
     * Tests if infantID is properly being stored.
     * @throws IOException Throws if it cannot grab data file.
     */
    @Test
    public void testGetInfant() throws IOException
    {
        // Creates Trial and fills it with data.
        Trial testTrial = new Trial(null, "mydata", "k3", 1);
        // Compares infant to intended.
        Assert.assertEquals(null, testTrial.getInfant());
    }

    /**
     * Tests if number of weeks is properly being stored.
     * @throws IOException Throws if it cannot grab data file.
     */
    @Test
    public void testGetWeek() throws IOException
    {
        // Creates Trial and fills it with data.
        Trial testTrial = new Trial(null, "mydata", "k3", 1);
        // Compares week to week intended.
        Assert.assertEquals(1, testTrial.getWeek());
    }

    /**
     * Tests if we stored the correct file name.
     * @throws IOException Throws if it cannot grab data file.
     */
    @Test
    public void testGetFileName() throws IOException
    {
        // Creates Trial and fills it with data.
        Trial testTrial = new Trial(null, "mydata", "k3", 1);
        // Compare file name to our own created file name.
        Assert.assertEquals("mydata/subject_k3_w01.csv", testTrial.getFileName());
    }
    
    /**
     * Tests the getMaxState method.
     * Also tests the getMaxState method within MultipleItemAbstract.
     * 
     * @throws IOException Throws if file cannot be obtained.
     */
    @Test
    public void testGetMaxState() throws IOException
    {
        // Creates a State object.
        String testColumnHeaders = ("time,right_wrist_x,left_shoulder_z");
        String testValues = ("0.02,0.238,2.11");
        FieldMapper testFieldMapper = new FieldMapper(testColumnHeaders.split(","));
        State testState = new State(null, testFieldMapper, testValues);
        // Creates a Trial object.
        Trial testTrial = new Trial(null, "mydata", "k3", 1);
        // Checks if a State object containing the value corresponding to field and subfield is returned.
        assertEquals(0.238, 
                testState.getMaxState("right_wrist", "x")
                .getValue("right_wrist", "x").getDoubleValue(), 0.001);
        // Checks if the correct maximum value is obtained for the field and subfield.
        assertEquals(0.214482, 
                testTrial.getMaxState("right_wrist", "x")
                .getPoint("right_wrist").getValue("x").getDoubleValue(), 0.001);
    }
    
    /**
     * Tests the getMinState method.
     * Also tests the getMinState method within MultipleItemAbstract.
     * 
     * @throws IOException Throws if file cannot be obtained.
     */
    @Test
    public void testGetMinState() throws IOException
    {
        // Creates a State object.
        String testColumnHeaders = ("time,right_wrist_x,left_shoulder_z");
        String testValues = ("0.02,0.238,2.11");
        FieldMapper testFieldMapper = new FieldMapper(testColumnHeaders.split(","));
        State testState = new State(null, testFieldMapper, testValues);
        // Creates a Trial object.
        Trial testTrial = new Trial(null, "mydata", "k3", 1);
        // Checks if a State object containing the value corresponding to field and subfield is returned.
        assertEquals(2.11, 
                testState.getMinState("left_shoulder", "z")
                .getValue("left_shoulder", "z").getDoubleValue(), 0.001);
        // Checks if the correct minimum value is obtained for the field and subfield.
        assertEquals(0.212279, 
                testTrial.getMinState("right_wrist", "x")
                .getPoint("right_wrist").getValue("x").getDoubleValue(), 0.001);
    }
    
    /**
     * Tests the getAverageValue method.
     * Also tests the getAverageValue method within MultipleItemAbstract.
     * 
     * @throws IOException Throws if file cannot be obtained.
     */
    @Test
    public void testGetAverageValue() throws IOException
    {
        // Creates a Trial object.
        Trial testTrial = new Trial(null, "mydata", "k3", 1);
        // Checks if the correct average value is obtained.
        assertEquals(0.21329163636363635, 
                testTrial.getAverageValue("right_wrist", "x").getDoubleValue(), 0.001);
    }
    
    /**
     * Test the toString method to return something like "Week 03".
     * 
     * @throws IOException Throws if file cannot be obtained.
     */
    @Test
    public void testToString() throws IOException
    {
        // Creates a Trial object.
        Trial testTrial = new Trial(null, "mydata", "k3", 1);
        // Checks if it returns "Week 01".
        assertEquals("Week 01", testTrial.toString());
        
        // Creates a Trial object.
        Trial testTrial2 = new Trial(null, "mydata", "k3", 10);
        // Checks if it returns "Week 10".
        assertEquals("Week 10", testTrial2.toString());
        
        // Creates a Trial object.
        Trial testTrial3 = new Trial(null, "mydata", "k3", 9);
        // Checks if it returns "Week 09".
        assertEquals("Week 09", testTrial3.toString());
    }
    
    /**
     * Test fieldMapper
     */
    @Test
    public void testGetFieldMapper() throws IOException
    {
        Trial testTrial = new Trial(null, "mydata", "k3", 1);
        assertNull(testTrial.getFieldMapper());
    }
}