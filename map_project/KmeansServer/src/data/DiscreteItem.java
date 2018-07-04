package data;

/**
 * DiscreteItem extends Item, in order to manipulate Item with a discrete
 * value associated to itself. Discrete values are such as Strings, chars,
 * etc...
 * 
 * @see Item
 */
public class DiscreteItem extends Item {
	
	/**
	 * The serial version ID needed to serialize instances of this
	 * class.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of DiscreteItem
	 * 
	 * @param attribute, Discrete Attribute of the item
	 * @param value, Discrete Value associated with the attribute of the item
	 */
	public DiscreteItem(DiscreteAttribute attribute, String value) {
        super(attribute, value);
    }
	
	/**
	 * This method implements the abstract method of the Item Class.
	 * In this case of the Discrete Items the method calculates the distance
	 * from the current item and the object a by comparing them with the equals()
	 * method.
	 * 
	 * @param a , the object from which the method calculates the distance
	 * @return The distance between the two Discrete Items.
	 */
    double distance (Object a) {
    	/**
    	 * Local variable for the return value
    	 */
        double v = 0;
        
        /**
         * Comparing the two items by casting the object given by the caller
         */
        if (!((Item) a).getValue().equals(getValue())) {
            v = 1;
        }
        return v;
    }
}

