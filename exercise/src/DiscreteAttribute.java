public class DiscreteAttribute extends Attribute {
    private String values[];
    
    public DiscreteAttribute(String name, String values[])  {
        super(name);
        this.values = values;
    }
    
    int getNumberOfDistinctValues() {
        return values.length;
    }
    
    String getValue(int i) {
        return values[i];
    }
    
    int frequency(Data data, ArraySet idList, String v) {
        int count = 0;
        int i;
        for(i = 0; i < data.getNumberOfExamples(); i++) {
            if(data.getAttributeValue(i, super.getIndex()) == v) { // Verify the result with .equals method (Runtime)
                count++;
                idList.add(i);
            }
        }   
        return count;  
    }
}

