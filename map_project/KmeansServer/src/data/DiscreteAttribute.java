package data;
import java.util.Iterator;
import java.util.TreeSet; 
import java.util.Arrays;
import java.util.Set;

public class DiscreteAttribute extends Attribute implements Iterable<String> {
	private static final long serialVersionUID = 1L;
	private TreeSet<String> values;
    
    public DiscreteAttribute(String name, TreeSet<String> values)  {
        super(name);
        this.values = values;
    }
    
    int getNumberOfDistinctValues() {
        return values.size();
    }
    
    String getValue(int i) {
    	String str[] = values.toArray(new String[0]);
    	return str[i];
    }
    
    int frequency(Data data, Set<Integer> idList, String v) {
        int count = 0;
        int i;
        for(i = 0; i < data.getNumberOfExamples(); i++) {
            if(idList.contains(i)) {
                if(data.getAttributeValue(i, super.getIndex()).equals(v)) {
                    count++;
                }
            }
        }   
        return count;
    }
    
    public Iterator<String> iterator() {
        return Arrays.asList(values.toArray(new String[0])).iterator();
    }
}

