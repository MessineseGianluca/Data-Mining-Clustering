package data;

public class ContinuousItem extends Item {
	public ContinuousItem(ContinuousAttribute attribute, Double value) {
		super(attribute, value);
	}
	
	double distance(Object a) {
		ContinuousAttribute ca = (ContinuousAttribute)this.getAttribute();
		double scaledValue = ca.getScaledValue((Double)((Item) a).getValue());
		return Math.abs(scaledValue - (Double)this.getValue());
	}
}
