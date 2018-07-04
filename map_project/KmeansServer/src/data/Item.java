package data;
import java.io.Serializable;
import java.util.Set;

/**
 * Each object build from this class Item contains an Attribute
 * and a value associated with its attribute. This class is crucial
 * for the computation of the clusters, because we can link a value to its
 * abstract representation.
 * 
 *@see Attribute
 */
public abstract class Item implements Serializable {
	private static final long serialVersionUID = 1L;
	protected Attribute attribute;
    protected Object value;
  
    public Item(Attribute attribute, Object value) {
        this.attribute = attribute;
        this.value = value;
    }
  
    Attribute getAttribute() {
        return attribute;
    }
  
    Object getValue() {
        return value;
    }
  
    public String toString() {
        return value.toString();
    }
  
    abstract double distance(Object a);
  
    public void update(Data data, Set<Integer> clusteredData) {
        value = data.computePrototype(clusteredData, attribute);
    }
}
