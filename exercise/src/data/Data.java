package data;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeSet;
import java.util.Set;
import database.*;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;


public class Data {
    private List<Example> data; // list of transactions
    private int numberOfExamples; // number of transactions
    private List<Attribute> attributeSet;
    public Data(String table) throws SQLException {       
        attributeSet = new LinkedList<Attribute>();
        TreeSet<Example> tempData = new TreeSet<Example>();
        
        /* Populate attributeSet */
        TreeSet<String> outLookValues = new TreeSet<String>();
        outLookValues.add("overcast");
        outLookValues.add("rain");
        outLookValues.add("sunny");
        attributeSet.add(new DiscreteAttribute("Outlook", outLookValues));
        
        
        attributeSet.add(new ContinuousAttribute("Temperature", 3.2, 38.7));
        
        TreeSet<String> humidityValues = new TreeSet<String>();
        humidityValues.add("normal");
        humidityValues.add("high");
        attributeSet.add(new DiscreteAttribute("Humidity", humidityValues));
        
        TreeSet<String> windValues = new TreeSet<String>();
        windValues.add("weak");
        windValues.add("strong");
        attributeSet.add(new DiscreteAttribute("Wind", windValues));
        
        TreeSet<String> playTennisValues = new TreeSet<String>();
        playTennisValues.add("yes");
        playTennisValues.add("no");
        attributeSet.add(new DiscreteAttribute("Play Tennis", playTennisValues));
        
        /* Add examples */
        Statement stmt = DbAccess.getConnection().createStatement();
        TableSchema schema = new TableSchema(, table);
        String query = "SELECT DISTINCT * FROM" + table;
        ResultSet rs = stmt.executeQuery(query);
        data = new ArrayList<Example>();
        while(rs.next()) {
          Example ex = new Example();
            for(int i = 0; i < schema.getNumberOfAttributes(); i++) {
                if(schema.getColumn(i).isNumber()) {
                  ex.add(rs.getFloat(i));
                } else {
                  ex.add(rs.getString(i));
                }
            }
            data.add(ex);
        }
        numberOfExamples = data.size();  
    }
    
    public int getNumberOfExamples() {
        return numberOfExamples;
    }
    
    public int getNumberOfAttributes() {
        return attributeSet.size();
    }
    
    public Object getAttributeValue(int exampleIndex, int attributeIndex) {
        return data.get(exampleIndex).get(attributeIndex);
    }
    
    Attribute getAttribute(int index) {
        return attributeSet.get(index);
    }
    
    public String toString() {
        String str = "";
        // print attributes
        for(int i = 0; i < attributeSet.size(); i++) {
            str = str + attributeSet.get(i).toString() + "  ";
        }
        // print data
        str = str + "\n";
        for(int i = 0; i < numberOfExamples; i++) {
            str = str + (i + 1) + ") ";
            for(int j = 0; j < attributeSet.size(); j++) {
                str = str + data.get(i).get(j) + "   ";
            }
            str = str + "\n";
        }
        return str;
    }
    
    public Tuple getItemSet(int index) {
        Tuple tuple = new Tuple(attributeSet.size());
        int i;
        for(i = 0; i < attributeSet.size(); i++) {
        	Attribute attr = attributeSet.get(i);
            if(attr instanceof ContinuousAttribute) {
            	tuple.add(
                        new ContinuousItem((ContinuousAttribute)attr, (Double)data.get(index).get(i)),
                        i
                );
            } else {
            	tuple.add(
                        new DiscreteItem((DiscreteAttribute)attr, (String)data.get(index).get(i)),
                        i
                );
            }
        }
        return tuple;
    }
    
    /* k is the number of cluster to generate. This method return a k dimension array, whose 
     * elements represent the index of the tuples(row index of data matrix) which
     * have been initially chosen as centroids(first step of k-means)
     */

    public int[] sampling(int k) throws OutOfRangeSampleSize {
        // exception
        if(k <= 0 || k > numberOfExamples) throw new OutOfRangeSampleSize();
        int centroidIndexes[] = new int[k];
        // choose k random different centroids in data.
        Random rand = new Random();
        rand.setSeed(System.currentTimeMillis());
        // init k random centroids.
        for(int i = 0; i < k; i++) {
            boolean found = false;
            int c;
            do {
                found = false;
                // chose a random index.
                c = rand.nextInt(getNumberOfExamples());
                // verify that centroid[c] is not equal to a centroid already stored in CentroidIndexes.
                for(int j = 0; j < i; j++) {
                    if(compare(centroidIndexes[j], c)) {
                        found = true;
                        break;
                    }
                }
            } while(found);        
            centroidIndexes[i] = c;
        }
        return centroidIndexes;
    }
    
    private boolean compare(int i, int j) {
        boolean equal = true;
        int k = 0;
        while(k < attributeSet.size() && equal) {
            if(getAttributeValue(i, k) != getAttributeValue(j, k)) {
                equal = false;
            }
            k++;
        }
        return equal;
    }

    Object computePrototype(Set<Integer> idList, Attribute attribute) { //By using RTTI, he can decide if the attribute is Discrete or Continuous
        if(attribute instanceof ContinuousAttribute) {
        	return computePrototype(idList, (ContinuousAttribute) attribute);
        } else {
        	return computePrototype(idList, (DiscreteAttribute) attribute);
        }
    }
    
    private String computePrototype(Set<Integer> idList, DiscreteAttribute attribute) {
        int comp;
        int freq = 0;
        String centroid = "";
        // Look for the most frequent value of attribute in tuples stored in idList.
        for(String str: attribute) {
            comp = attribute.frequency(this, idList, str);
            if(comp > freq) {
                freq = comp;
                centroid = str;
            }
        }
        return centroid;
    }
    
    private Double computePrototype(Set<Integer> idList, ContinuousAttribute attribute) {
    	return attribute.getAVG(this, idList);
    }
}

