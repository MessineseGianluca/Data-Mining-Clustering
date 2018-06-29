package data;
import java.io.Serializable;

public abstract class Attribute implements Serializable {
	private static final long serialVersionUID = 1L;
	protected String name; // symbolic name of the attribute
	protected int index; // numeric id of the attribute
	private static int count = 0;
	
	public Attribute(String name) {
		this.name = name;
		index = count;
		count++;
	}
	
    String getName() {
		return name;
	}
	
	int getIndex() {
		return index;
	}
	
	static void resetAttributesCount() {
		count = 0;
	}
	
	public String toString() {
		return name;
	}
	
	protected void finalize ()  {
        count--;
    }
}
