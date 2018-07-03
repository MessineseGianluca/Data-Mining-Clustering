package data;

import java.util.Set;

/**
 * ContinuousAttribute extends Attribute, in order to 
 * obtain objects representing continue numeric values 
 * such as	double, long, float, etc..
 * 
 * @see Attribute
 */
public class ContinuousAttribute extends Attribute {
	private static final long serialVersionUID = 1L;
	
	/**
	 * The max value of the range of values
	 */
	private double max;
	
	/**
	 * The min value of the range of values
	 */
    private double min;
    
    /**
     * ContinuousAttribute's constructor
     * 
     * @param name The symbolic name of the attribute
     * @param min The inferior end
     * @param max the superior end
     */
    public ContinuousAttribute(String name, double min, double max) {
    	super(name); // invokes Attribute's constructor
    	this.min = min;
    	this.max = max;
    }
    
    /**
     * @param v The value to scale
     * @return The scaled value of a given value v
     */
    double getScaledValue(double v) {
    	return (v - min) / (max - min);
    }
    
    /**
     * 
     * @param data The whole set of tuples in the system
     * @param idList The set of tuples to analyze
     * @return The average value of the given attribute for a set of tuples
     */
    double getAVG(Data data, Set<Integer> idList) {
        Double sum = 0.0;
        int count = 0;
        int i;
        for(i = 0; i < data.getNumberOfExamples(); i++) {  
            if(idList.contains(i)) {
                sum += (Double)data.getAttributeValue(i, super.getIndex());
                count++;
            }
        }
        return sum / count;  // Eventually add a try & catch for count = 0
    }
}
