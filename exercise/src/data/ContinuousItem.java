package data;

public class ContinuousItem extends Item {
	public ContinuousItem(ContinuousAttribute attribute, Double value) {
		super(attribute, value);
	}
	
	double distance(Object a) {
		Double scaledValue = ((ContinuousAttribute)this.getAttribute()).getScaledValue((Double)a);	
		return Math.abs(scaledValue - (Double)this.getValue());
	}

}
