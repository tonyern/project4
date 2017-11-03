import static org.junit.Assert.*;
import org.junit.Test;
/**
 * Test case for State.
 * 
 * @author Tony Nguyen and Dustin Sengkhamvilay
 * @version 2017-10-27
 */
public class StateTest 
{
    /**
     * Testing the constructor.
     */
    @Test
    public void testConstructor() 
    {
        String testColumnHeaders = ("time,right_wrist_x,left_shoulder_z");
        String testValues = ("0.02,0.238,2.11");
        FieldMapper testFieldMapper = new FieldMapper(testColumnHeaders.split(","));
        State testState = new State(null, testFieldMapper, testValues);
        assertEquals(testFieldMapper.extractPointND(testValues.split(","), "left_shoulder").
        		getValue("z").getDoubleValue(), testState.getPoint("left_shoulder").
        		getValue("z").getDoubleValue(), 0.001);
    }
    
    /**
     * Testing the get trial.
     */
    @Test
    public void testGetTrial()
    {

        String testColumnHeaders = ("time,right_wrist_x,left_shoulder_z");
        String testValues = ("0.02,0.238,2.11");
        FieldMapper testFieldMapper = new FieldMapper(testColumnHeaders.split(","));
        State testState = new State(null, testFieldMapper, testValues);
        assertEquals(null, testState.getTrial());
    }
    
    /**
     * Testing get point.
     */
    @Test
    public void testGetPoint()
    {

        String testColumnHeaders = ("time,right_wrist_x,left_shoulder_z");
        String testValues = ("0.02,0.238,2.11");
        FieldMapper testFieldMapper = new FieldMapper(testColumnHeaders.split(","));
        State testState = new State(null, testFieldMapper, testValues);
        assertEquals(testFieldMapper.extractPointND(testValues.split(","), "right_wrist").
        		getValue("x").getDoubleValue(), testState.getPoint("right_wrist").
        		getValue("x").getDoubleValue(), 0.001);
    }
    
    /**
     * Testing the get value.
     */
    @Test
    public void testGetValue()
    {

        String testColumnHeaders = ("time,right_wrist_x,left_shoulder_z");
        String testValues = ("0.02,0.238,2.11");
        FieldMapper testFieldMapper = new FieldMapper(testColumnHeaders.split(","));
        State testState = new State(null, testFieldMapper, testValues);
        assertEquals(new GeneralValue(2.11).getDoubleValue(), 
                testState.getValue("left_shoulder", "z").getDoubleValue(), 0.001);
    }
    
    /**
     * Testing get max.
     */
    @Test
    public void testGetMaxState()
    {

        String testColumnHeaders = ("time,right_wrist_x,left_shoulder_z");
        String testValues = ("0.02,0.238,2.11");
        FieldMapper testFieldMapper = new FieldMapper(testColumnHeaders.split(","));
        State testState = new State(null, testFieldMapper, testValues);
        assertEquals(0.238, testState.getMaxState("right_wrist", "x").getValue("right_wrist", "x").
        		getDoubleValue(), 0.001);
    }
    
    /**
     * Testing get min.
     */
    @Test
    public void testGetMinState()
    {
    	String testColumnHeaders = ("time,right_wrist_x,left_shoulder_z");
        String testValues = ("0.02,0.238,2.11");
        FieldMapper testFieldMapper = new FieldMapper(testColumnHeaders.split(","));
        State testState = new State(null, testFieldMapper, testValues);
        assertEquals(0.02, testState.getMinState("time", "").getValue("time", "").getDoubleValue(), 0.001);
    }
    
    /**
     * Testing get average.
     */
    @Test
    public void testGetAverageState()
    {
    	String testColumnHeaders = ("time,right_wrist_x,left_shoulder_z");
        String testValues = ("0.02,0.238,2.11");
        FieldMapper testFieldMapper = new FieldMapper(testColumnHeaders.split(","));
        State testState = new State(null, testFieldMapper, testValues);
        assertEquals(0.02, testState.getMinState("time", "").getValue("time", "").getDoubleValue(), 0.001);
    }
    
    /**
     * Test toString.
     */
    @Test
    public void testToString()
    {
    	String testColumnHeaders = ("time,right_wrist_x,left_shoulder_z");
        String testValues = ("0.02,0.238,2.11");
        FieldMapper testFieldMapper = new FieldMapper(testColumnHeaders.split(","));
        State testState = new State(null, testFieldMapper, testValues);
        assertEquals("left_shoulder(z = 2.110; )\nright_wrist(x = 0.238; )\ntime( = 0.020; )\n", testState.toString());
    }
}