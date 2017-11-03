import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
/**
 * The field class that represents the field name for computing statistics.
 *
 * @author Tony Nguyen and Dustin Sengkhamvilay
 * @version 2017-10-16
 */
public class Field implements Iterable<String>
{
    /**
     * Our map of string and Integer to store.
     */
    private Map<String, Integer> subFields;

    /**
     * Constructor creates an empty field.
     */
    public Field()
    {
        subFields = new TreeMap<String, Integer>();
    }

    /**
     * Add the sub field name along with its corresponding column number.
     * @param subFieldName is for example left_wrist_x, fieldMapper splits that and "x" is the subfield. 
     * @param columnIndex this is the column number in the files.
     */
    public void addSubField(String subFieldName, int columnIndex)
    {
        subFields.put(subFieldName, columnIndex);
    }

    /**
     * Get the value with the corresponding key.
     * @param subFieldName is for example left_wrist_x, fieldMapper splits that and "x" is the subfield. 
     * @return Integer value with the passing key.
     */
    public Integer getIndex(String subFieldName)
    {
        // Checking if subFieldName is in the map. If not then return null.
        if (subFields.containsKey(subFieldName))
        {
            return subFields.get(subFieldName);
        }
        else
        {
            return null;
        }
    }

    /**
     * Get the current size of the map.
     * @return size of subField map.
     */
    public int size()
    {
        return subFields.size();
    }

    /**
     * Iterators through the fieldMap Map all field names.
     * It overrides the method defined in Iterator interface.
     * @return loopThrough
     */
    @Override
    public Iterator<String> iterator()
    {
        return this.subFields.keySet().iterator();
    }

    /**
     * Return a string in the format - "SUBFIELD(INDEX); "
     * @return string
     */
    public String toString()
    {
        String out = "";
        
        for (String subField : this)
        {
            out += subField + "(" + subFields.get(subField) + "); ";
        }
        
        return out;
    }
}