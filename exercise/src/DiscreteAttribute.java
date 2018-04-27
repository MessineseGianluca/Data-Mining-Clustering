public class DiscreteAttribute extends Attribute {
	private String values[];
	
	public DiscreteAttribute(String name, String values[])  {
		super(name);
		this.values = values;
	}
	
	int getNumberOfDistinctValues() {
		return values.length;
	}
	
	String getValue(int i) {
		return values[i];
	}
}
