package data;

public class ContinuousItem extends Item {
	private static final long serialVersionUID = 1L;

	public ContinuousItem(ContinuousAttribute attribute, Double value) {
		super(attribute, value);
	}
	
	double distance(Object a) {
		ContinuousAttribute ca = (ContinuousAttribute)this.getAttribute();
		double scaledValue = ca.getScaledValue((Double)((Item) a).getValue());
		double scaledValueOfThis = ca.getScaledValue((Double)this.getValue());
		return Math.abs(scaledValue - scaledValueOfThis);
	}
}
