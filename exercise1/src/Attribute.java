public abstract class Attribute {
  
	protected String name; // symbolic name of the attribute
	protected int index; // numeric id of the attribute
	private static int count = 0;
	
	Attribute(String name) {
		this.name = name;
		count++;
		this.index = count;
	}
	
    String getName() {
		return name;
	}
	
	int getIndex() {
		return index;
	}
	
	public String toString() {
		return name;
	}
}
