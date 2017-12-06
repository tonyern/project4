import java.util.Iterator;
import java.util.TreeMap;
/**
 * FieldMapper that maps data. It helps with the parsing process.
 *
 * @author Tony Nguyen
 * @version 2017-10-18
 */
public class FieldMapper implements Iterable<String>
{
    /**
     * Map that stores the string and corresponding field name.
     */
    private TreeMap<String, Field> fieldMap = new TreeMap<String, Field>();

    /**
     * Here we take in a list of individual columns. Column names are related to field and subfield.
     * The constructor will create the entire data structures.
     * @param columnNames is the columns in the file.
     */
    public FieldMapper(String[] columnNames)
    {
        // Loops for as many headers/columns there are.
        for (int x = 0; columnNames.length > x; x++)
        {
            // If the second to last character is _ then adds a subfield.
            if (columnNames[x].charAt(columnNames[x].length() - 2) == '_')
            {
                // Holds the field.
                String mainField = columnNames[x].substring(0, columnNames[x].length() - 2);
                // Holds the subfield.
                String subFieldLetter = Character.toString(columnNames[x].charAt(
                        columnNames[x].length() - 1));
                if (!fieldMap.containsKey(mainField))
                {
                    fieldMap.put(mainField, new Field());
                    fieldMap.get(mainField).addSubField(subFieldLetter, x);
                }
                else
                {
                    fieldMap.get(mainField).addSubField(subFieldLetter, x);
                }
            }
            // If there is no subfield present, adds an empty value to subfield.
            else
            {
                fieldMap.put(columnNames[x], new Field());
                fieldMap.get(columnNames[x]).addSubField("", x);
            }
        }
    }

    /**
     * Get the Field stored with a string.
     * @param fieldName is for example left_wrist_x, fieldMapper would split 
     * that and left_wrist is the field.
     * @return Field given the String.
     */
    public Field getField(String fieldName)
    {
        return fieldMap.get(fieldName);
    }

    /**
     * Gets the string values and field name from PointND.
     * This method constructs PointND object for the type field it received.
     * @param stringValues this is a list of values extracted from the files.
     * @param fieldName is for example left_wrist_x and it is split then left_wrist is the fieldName.
     * @return PointND this is represented as one value of left_wrist with x, y, and z values.
     */
    public PointND extractPointND(String[] stringValues, String fieldName)
    {
        // Create pointND object.
        PointND pointMaker = new PointND();
        // Adds the subfield and the corresponding value from the stringValues by determining column index.
        for (String subFieldName: getField(fieldName))
        {
            GeneralValue temp = new GeneralValue(stringValues[getField(fieldName).getIndex(subFieldName)]);
            pointMaker.add(subFieldName, temp);
        }
        return pointMaker;
    }

    /**
     * Get the current size of the map.
     * @return size of the fieldMap.
     */
    public int size()
    {
        return fieldMap.size();
    }

    /**
     * Iterators through the fieldMap Map all field names.
     * It overrides the method defined in Iterator interface.
     * @return loopThrough
     */
    @Override
    public Iterator<String> iterator()
    {
        return fieldMap.keySet().iterator();
    }
}