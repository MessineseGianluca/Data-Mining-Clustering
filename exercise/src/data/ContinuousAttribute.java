package data;

import java.util.Set;

public class ContinuousAttribute extends Attribute {
    private double max; // superior end of the range
    private double min; // inferior end of the range
    
    public ContinuousAttribute(String name, double min, double max) {
    	super(name); // invokes Attribute's constructor
    	this.min = min;
    	this.max = max;
    }
    
    double getScaledValue(double v) {
    	return (v - min) / (max - min);
    }
    
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
