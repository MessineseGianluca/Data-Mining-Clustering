package data;
public class Tuple {
    private Item tuple[];
  
    Tuple(int size) {
        tuple = new Item[size];
    }
    
    public int getLength() {
        return tuple.length;
    }
  
    public Item get(int i) {
        return tuple[i];
    }
    
    void add(Item c, int i) {
        tuple[i] = c;
    }
  
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
  
    public double avgDistance(Data data, int clusteredData[]) {
        double p = 0.0, sumD = 0.0;
        for(int i = 0; i < clusteredData.length; i++) {
            double d = getDistance(data.getItemSet(clusteredData[i]));
            sumD += d;
        }
        p = sumD / clusteredData.length;
        return p;
    }
    
    public String toString() {
    	String str = "( ";
    	for(int i = 0; i < tuple.length; i++) {
    		str += get(i).toString() + " ";
    	}
    	str += ")";
    	return str;
    }
}