package data;

/**
 * DiscreteItem extends Item, in order to manipulate Item with a discrete
 * value associated to itself. 
 *
 * 
 * @see Attribute
 */
public class DiscreteItem extends Item {
	
	/**
	 * The serial version ID needed to serialize instances of this
	 * class.
	 */
	private static final long serialVersionUID = 1L;

	public DiscreteItem(DiscreteAttribute attribute, String value) {
        super(attribute, value);
    }
  
    double distance (Object a) {
        double v = 0;
        if (!((Item) a).getValue().equals(getValue())) {
            v = 1;
        }
        return v;
    }
}

