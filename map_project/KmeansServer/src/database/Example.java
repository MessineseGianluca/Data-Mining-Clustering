package database;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The Example class is necessary to represent a tuple read
 * from a table stored in a database.
 * An Example object is a collection of values of different types,
 * where each type must be coherent with the type of 
 * the corresponding tuple's value.
 * When adding new values in the collection, their position is very 
 * important because it makes possible to give an exact copy of the 
 * information contained in a database's table.
 * The Example class has methods to add a new values in the collection
 * and to get one of them by accessing to a specific position 
 * of the collection. In addition, this class provides a method for 
 * comparing two Example's object, as well as a method for converting 
 * an example in a string. It is also possible to get an iterator 
 * to traverse the collection of values.
 * 
 * @see Comparable
 */
public class Example implements Comparable<Example> {
	
	/**
	 * The collection of values a tuple is composed by.
	 * It accepts Object's instance because added values
	 * could be of different types.
	 * 
	 * @see List
	 */
	private List<Object> example = new ArrayList<Object>();

	public void add(Object o) {
		example.add(o);
	}
	
	public Object get(int i) {
		return example.get(i);
	}
	
	/**
	 * Returns an iterator to traverse the collection of values.
	 * 
	 * @return an iterator over the elements in this list in proper sequence.
	 * @see List
	 * @see Iterator 
	 */
    public Iterator<Object> getIterator() {
        return example.iterator();
    }
	
    /**
     * Compares two Example's objects by comparing their values one by one.
     * 
     * @return the value 0 if this Example is equal to the argument Example; 
     * 		   a value less than 0 if this Example has a lesser value
     * 		   in order relation than the value of argument Example located 
     * 		   in the same position; and a value greater than 0 if this Example 
     *         has a greater value in order relation than the value of 
     *         argument Example located in the same position.
     */
    @SuppressWarnings("unchecked")
	public int compareTo(Example ex) {
        Iterator<Object> iter = example.iterator();
        Iterator<Object> iter2 = ex.getIterator();
        int match = 0;
        while(iter.hasNext() && match == 0) {
            Object obj = iter.next();
            Object obj2 = iter2.next();
            match = ((Comparable<Object>)obj).compareTo(obj2);
        }
        return match;
	}
	
    /**
     * Returns a string representing the tuple with all its values.
     * 
     * @return a string for this example.
     */
	public String toString() {
		String str = "";
		for(Object o: example) {
			str += o.toString() + " ";
		}
		return str;
	}
}