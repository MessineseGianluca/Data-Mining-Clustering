package data;
import java.io.Serializable;

/**
 * Each instance of Attribute, represent a specific 
 * attribute for the tuples(transactions) handled 
 * in the system.
 * Attribute is serializable.
 */

public abstract class Attribute implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * The symbolic name of the attribute
	 */
	protected String name;
	
	/**
	 * The numeric id of the attribute
	 */
	protected int index;
	
	/**
	 * The number of attributes for tuples handled in the 
	 * system
	 */
	private static int count = 0;
	
	/**
	 * Attribute's constructor
	 * @param name The attribute's symbolic  name.
	 */
	public Attribute(String name) {
		this.name = name;
		index = count;
		count++;
	}
	
    String getName() {
		return name;
	}
	
	int getIndex() {
		return index;
	}
	
	static void resetAttributesCount() {
		count = 0;
	}
	
	public String toString() {
		return name;
	}
	
	/**
	 * Decrements count before the garbage collector
	 * is executed
	 */
	protected void finalize ()  {
        count--;
    }
}
