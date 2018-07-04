package data;
import java.io.Serializable;
import java.util.Set;

/**
 * Each object build from this class Item contains an Attribute
 * and a value associated with its attribute. This class is crucial
 * for the computation of the clusters, because we can link a value to its
 * abstract representation. Item is serializable.
 * 
 *@see Attribute
 */
public abstract class Item implements Serializable {
	/**
	 * The serial version ID needed to serialize instances of this
	 * class.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The attribute of the item
	 */
	protected Attribute attribute;
	
	/**
	 * The value associated with the attribute of the item
	 */
    protected Object value;
  
    /**
     * Constructor of the Item
     * 
     * @param attribute, Attribute of the item
     * @param value, Value associated with the attribute of the item
     */
    public Item(Attribute attribute, Object value) {
        this.attribute = attribute;
        this.value = value;
    }
  
    Attribute getAttribute() {
        return attribute;
    }
  
    Object getValue() {
        return value;
    }
  
    /**
     * Overriding of the toString function in order to return the value associated
     * with the item as a string
     * 
     * @return The current item as a string
     */
    public String toString() {
        return value.toString();
    }
    
    /**
     * Abstract method distance will be implemented in subclasses of Item.
     * This because Item doesn't know what types of value is going to manipulate
     * and the computation of distance changes with some different types.
     * This method calculate the distance from the current item and the 
     * Object a given in input from the caller.
     *  
     * @param a, the object from which the method calculates the distance
     * @return The distance from the items in double format
     */
    abstract double distance(Object a);
  
    /**
     * This method set the value to the result of computePrototype
     * called on the data object given by the caller.
     * 
     * @param data, a data object which contains all the values
     * @param clusteredData, indexes of the rows of data
     * 
     * @see data.computePrototype
     */
    public void update(Data data, Set<Integer> clusteredData) {
    	/**
    	 * Value is set to the result of computePrototype called on the
    	 * object data by giving as input: a clusteredData which contains 
    	 * all the indexes of the cluster and the attribute which represent
    	 * the column of the matrix of data.
    	 */
        value = data.computePrototype(clusteredData, attribute);
    }
}
