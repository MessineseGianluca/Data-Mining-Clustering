public abstract class Attribute {
  
	protected String name; // symbolic name of the attribute
	protected int index; // numeric id of the attribute
	
	Attribute(String name, int index) {
		this.name = name;
		this.index = index;
	}
	
    String getName() {
		return name;
	}
	
	int getIndex() {
		return index;
	}
	
	public String toSTring() {
		return name;
	}
}
