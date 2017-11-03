import org.junit.Assert;
import org.junit.Test;
/**
 * Test case for GeneralValue class.
 * 
 * @author Tony Nguyen and Dustin Sengkhamvilay
 * @version 10/09/17
 */
public class GeneralValueTest 
{    
    /**
     * Testing the first empty constructor if it returns false meaning invalid.
     */
    @Test
    public void testFirstGeneralValue() 
    {
        GeneralValue generalValue = new GeneralValue();
        Assert.assertEquals("It will return false.", false, generalValue.isValid());
    }
    
    /**
     * Testing the second overloaded constructor if it is false.
     */
    @Test
    public void testSecondGeneralValueFalse() 
    {
        GeneralValue generalValue = new GeneralValue("NaN");
        Assert.assertEquals(false, generalValue.isValid()); 
        try 
        {
            generalValue.getDoubleValue();
        }
        
        catch (InvalidValueException e) 
        {
            // Nothing should happen.
        }
    }
    
    /**
     * Test the double constructor with a NaN.
     */
    @Test
    public void testNanDubConstructor() 
    {
        // Test with a double NaN.
        GeneralValue g1 = new GeneralValue(Double.parseDouble("NaN"));
        // False.
        Assert.assertFalse(g1.isValid());
    }
    
    /**
     * Testing the second overloaded constructor if it is true.
     */
    @Test
    public void testSecondGeneralValueTrue() 
    {
        // Check valid constructor.
        GeneralValue generalValue = new GeneralValue(".12345");
        Assert.assertEquals(true, generalValue.isValid()); 
    }
    
    /**
     * Testing toString if it is true.
     */
    @Test
    public void testToStringTrue() 
    {   
        // Tests toString with valid value.
        GeneralValue generalValue = new GeneralValue(".45678"); 
        Assert.assertEquals("0.457", generalValue.toString()); 
    }
    
    /**
     * Testing toString if it is false.
     */
    @Test
    public void testToStringFalse()
    {
        GeneralValue generalValue = new GeneralValue();  
        // Tests invalid toString.
        Assert.assertEquals("invalid", generalValue.toString()); 
    }
    
    /**
     * Testing for the lessThan method.
     */
    @Test
    public void testLessThan()
    {
        // Make two general values.
        GeneralValue g1 = new GeneralValue(5.0);
        GeneralValue g2 = new GeneralValue(3.5);
        // Test
        Assert.assertTrue(g2.isLessThan(g1));
        Assert.assertFalse(g1.isLessThan(g2));
    }

    /**
     * Testing for the greaterThan method.
     */
    @Test
    public void testGreaterThan()
    {
        GeneralValue g1 = new GeneralValue(5.0);
        GeneralValue g2 = new GeneralValue(3.5);
        // Test.
        Assert.assertTrue(g1.isGreaterThan(g2));
        Assert.assertFalse(g2.isGreaterThan(g1));
    }
    
    /**
     * Test for invalid comparisons.
     */
    @Test
    public void testComparisons() 
    {
        GeneralValue g1 = new GeneralValue("NaN");
        GeneralValue g2 = new GeneralValue("5.0");
        GeneralValue g3 = new GeneralValue("NaN");
        
        // Comparing 5.0 greater than NaN should be true.
        Assert.assertTrue(g2.isGreaterThan(g1));
        // Comparing NaN greater than 5.0 should be false.
        Assert.assertFalse(g1.isGreaterThan(g2));
        
        // Comparing 5.0 less than NaN should be true.
        Assert.assertTrue(g2.isLessThan(g1));
        // Comparing NaN less than NaN should be false.
        Assert.assertFalse(g1.isLessThan(g2));
        
        // Comparing NaN greater than NaN should be false.
        Assert.assertFalse(g1.isGreaterThan(g3));
        // Comparing NaN greater than NaN should be false.
        Assert.assertFalse(g3.isGreaterThan(g1));
        // Comparing NaN less than NaN should be false.
        Assert.assertFalse(g3.isLessThan(g1));
        // Comparing NaN less than NaN should be false.
        Assert.assertFalse(g1.isLessThan(g3));
    }
}
