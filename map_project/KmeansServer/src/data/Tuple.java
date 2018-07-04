package data;
import java.io.Serializable;

/**
 * The tuple represents a sequence of items. So it can be represented
 * as a row of the matrix data.
 * This class is serializable.
 *	
 * @see Item		
 */
public class Tuple implements Serializable {
	private static final long serialVersionUID = 1L;
	private Item tuple[];
	
	/**
	 * Constructor of tuple
	 * 
	 * @param size, maximum number of items in a tuple
	 */
    public Tuple(int size) {
        tuple = new Item[size];
    }
    
    public int getLength() {
        return tuple.length;
    }
  
    public Item get(int i) {
        return tuple[i];
    }
    
    public void add(Item c, int i) {
        tuple[i] = c;
    }
    
    /**
     * This method calculates the distance between the current tuple
     * and the obj tuple given by the caller of the method.
     * This value is obtained by summing each item distance of the two
     * tuples. So their length must be the same in order to have a distance.
     * 
     * @param obj, the tuple given by the caller
     * @return the distance value between the two tuples in double format
     */
    public double getDistance(Tuple obj) {
        double dist = 0;
        int i;
        if(obj.getLength() == getLength()) {
            for(i = 0; i < getLength(); i++) {
                dist += tuple[i].distance(obj.get(i));
            }
        }
        return dist;
    }  
    
    /**
     * avgDistance method calculate the average of the distances between
     * the current tuple and each tuples of data, which are indexed by
     * the clusteredData array.
     * 
     * @param data, matrix which contains all the values associated with attributes
     * @param clusteredData, array which contains the indexes of data rows
     * @return The avgDistance in double format
     */
    public double avgDistance(Data data, Integer clusteredData[]) {
        double p = 0.0, sumD = 0.0;
        for(int i = 0; i < clusteredData.length; i++) {
            double d = getDistance(data.getItemSet(clusteredData[i]));
            sumD += d;
        }
        p = sumD / clusteredData.length;
        return p;
    }
    
    /**
     * Overriding of the toString function in order to return the entire
     * tuple values as a string
     * 
     * @return The current tuple as a string
     */
    public String toString() {
    	String str = "( ";
    	for(int i = 0; i < tuple.length; i++) {
    		str += get(i).toString() + " ";
    	}
    	str += ")";
    	return str;
    }
}