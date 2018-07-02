package data;
import java.util.Iterator;
import java.util.TreeSet; 
import java.util.Arrays;
import java.util.Set;

/**
 * DiscreteAttribute extends Attribute, in order to 
 * obtain objects representing discrete values such as
 * Strings, chars, etc...
 * 
 * @see Attribute
 */
public class DiscreteAttribute extends Attribute implements Iterable<String> {
	private static final long serialVersionUID = 1L;
	
	/**
	 * The set of possible values which a DiscreteAttribute can assume.
	 * 
	 * e.g. For the discrete attribute Color, possible values could
	 * be Red, Green and Blue.
	 */
	private TreeSet<String> values;
    
	/**
	 * DiscreteAttribute's constructor
	 * @param name the symbolic attribute's name.
	 * @param values the set of valid values for the discrete attribute
	 */
    public DiscreteAttribute(String name, TreeSet<String> values)  {
        super(name);
        this.values = values;
    }
    
    int getNumberOfDistinctValues() {
        return values.size();
    }
    
    String getValue(int i) {
    	String str[] = values.toArray(new String[0]);
    	return str[i];
    }
    
    /**
     * @param data The whole set of tuples in the system
     * @param idList The given set of tuples
     * @param v The value to compare
     * @return The number of ‎occurrences of v in a given set of tuples.
     */
    int frequency(Data data, Set<Integer> idList, String v) {
        int count = 0;
        int i;
        for(i = 0; i < data.getNumberOfExamples(); i++) {
            if(idList.contains(i)) {
                if(data.getAttributeValue(i, super.getIndex()).equals(v)) {
                    count++;
                }
            }
        }   
        return count;
    }
    
    /**
     * @return The iterator for values.
     */
    public Iterator<String> iterator() {
        return Arrays.asList(values.toArray(new String[0])).iterator();
    }
}

