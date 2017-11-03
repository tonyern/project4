import org.junit.Assert;
import org.junit.Test;
import java.io.IOException;
/**
 * Test for the Infant class.
 * 
 * @author Tony Nguyen and Dustin Sengkhamvilay
 * @version 10/08/17
 *
 */
public class InfantTest
{
    /**
     * Test the empty constructor.
     * @throws IOException if we can't get the file.
     */
    @Test
    public void testConstructor() throws IOException
    {
        Infant testInfant = new Infant("mydata", "k3");
        // Testing that there is data actually loaded.
        Assert.assertEquals(0.248297, 
                testInfant.getItem(0).getItem(0).getValue("left_wrist", "x").getDoubleValue(), 0.001);
        // Test it exists.
        Assert.assertNotNull(testInfant);
    }
    
    /**
     * Test the getSize method.
     * @throws IOException if we can't get the file.
     */
    @Test
    public void testGetSize() throws IOException
    {   
        Infant testInfant = new Infant("mydata", "k3");
        // Since the final output of trials & weeks are unknown we are testing that getSize can return.
        int x = testInfant.getSize();
        Assert.assertEquals(x, testInfant.getSize());
    }
    
    /**
     * Test the getItem method.
     * @throws IOException if we can't get the file.
     */
    @Test
    public void testGetItem() throws IOException
    {   
        Infant testInfant = new Infant("mydata", "k3");
        //Testing that a specific value can be obtained.
        Assert.assertEquals(0.291787, 
                testInfant.getItem(0).getItem(0).getValue("left_elbow", "x").getDoubleValue(), 0.001);
        // Get the correct Filename.
        Assert.assertEquals("mydata/subject_k3_w01.csv", testInfant.getItem(0).getFileName());
    }
    
    /**
     * Test the getInfantID method.
     * @throws IOException if we can't get the file.
     */
    @Test
    public void testInfantIDGetter() throws IOException
    {
        Infant testInfant = new Infant("mydata", "k3");
        // Get the correct infant ID.
        Assert.assertEquals("k3", testInfant.getInfantID());
    }
    
    /**
     * Test the iterator.
     */
    @Test
    public void testIterator()
    {
        
    }
}