package data;

/**
 * ContinuosItem extends Item, in order to manipulate Item with a discrete
 * value associated to itself. Continuous values are such as double, long, float, etc..
 * ContinuousItem is serializable.
 * @see Item
 */
public class ContinuousItem extends Item {
	/**
	 * The serial version ID needed to serialize instances of this
	 * class.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of ContinuousItem
	 * 
	 * @param attribute, Continuous Attribute of the item
	 * @param value, Continuous Value associated with the attribute of the item
	 */
	public ContinuousItem(ContinuousAttribute attribute, Double value) {
		super(attribute, value);
	}
	
	/**
	 * This method implements the abstract method of the Item Class.
	 * In this case of the continuous Items the method calculates the distance
	 * from the current item and the object a by calculating the absolute difference
	 * between the scaled values of each Item.
	 * 
	 * @param a , the object from which the method calculates the distance
	 * @return The distance between the two Continuous Items.
	 */
	double distance(Object a) {
		
		/**
		 * Getting the Continuous attribute from the current item by casting
		 */
		ContinuousAttribute ca = (ContinuousAttribute)this.getAttribute();
		
		/**
		 * Getting the scaled value of the item a
		 */
		double scaledValue = ca.getScaledValue((Double)((Item) a).getValue());
		
		/**
		 * Getting the scaled value of the current item
		 */
		double scaledValueOfThis = ca.getScaledValue((Double)this.getValue());
		
		/**
		 * Calculates the absolute difference between the scaled values of each item 
		 */
		return Math.abs(scaledValue - scaledValueOfThis);
	}
}
