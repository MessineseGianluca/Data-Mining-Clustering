package database;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Example implements Comparable<Example> {
	private List<Object> example = new ArrayList<Object>();

	public void add(Object o) {
		example.add(o);
	}
	
	public Object get(int i) {
		return example.get(i);
	}
	
    public Iterator<Object> getIterator() {
        return example.iterator();
    }
	
	public int compareTo(Example ex) {
        Iterator<Object> iter = example.iterator();
        Iterator<Object> iter2 = ex.getIterator();
        int match = 0;
        while(iter.hasNext() && match == 0) {
            Object obj = iter.next();
            Object obj2 = iter2.next();
            match = ((Comparable<Object>)obj).compareTo(obj2);
        }
        return match;
	}
	
	public String toString() {
		String str = "";
		for(Object o: example) {
			str += o.toString() + " ";
		}
		return str;
	}
	
}