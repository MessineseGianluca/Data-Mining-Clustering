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
	/**
	 * The list of transactions in the system
	 */
    private List<Example> data;
    
    /**
     * The number of transactions in the system
     */
    private int numberOfExamples;
    
    /**
     * The set of attributes for the transactions
     */
    private List<Attribute> attributeSet;
    
    /**
     * Data's constructor.
     * Reads a set of transactions from the DB and
     * initializes attributeSet.
     * @param table The DB's table name
     * @throws SQLException When a SQL error occurs
     */
    public Data(String table) throws SQLException {
        attributeSet = new LinkedList<Attribute>();
        Attribute.resetAttributesCount();

        /******************* Populate attributeSet ***********************/
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

        /***************** Read transactions from the DB's table **************/
        Statement stmt = DbAccess.getConnection().createStatement();
        TableSchema schema = new TableSchema(table);
        String query = "SELECT DISTINCT * FROM " + table;
        ResultSet rs = stmt.executeQuery(query);
        data = new ArrayList<Example>();
        while(rs.next()) {
          Example ex = new Example();
            for(int i = 1; i < schema.getNumberOfAttributes() + 1; i++) {
                if(schema.getColumn(i - 1).isNumber()) {
                  ex.add(rs.getDouble(i));
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
    
    /** 
     * @param exampleIndex The index of the transaction
     * @param attributeIndex The index of the attribute of the transaction
     * @return The attribute's value of a transaction.
     */
    public Object getAttributeValue(int exampleIndex, int attributeIndex) {
        return data.get(exampleIndex).get(attributeIndex);
    }
    
    public Attribute getAttribute(int index) {
        return attributeSet.get(index);
    }
    
    /**
     * @return The string representing the transactions' info
     */
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
    
    /**
  	 * Generates a Tuple from the given transaction
     * @param index The index of the transaction to parse
     * @return The generated tuple
     */
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

    /** 
     * @param k The number of clusters to generate
     * @return A k dimension array whose
     * elements represent the indexes of the tuples which
     * have been initially chosen as centroids
     * @throws OutOfRangeSampleSize When k is greater than the number of transactions
     */
    public int[] sampling(int k) throws OutOfRangeSampleSize {
        if(k <= 0 || k > numberOfExamples) {
            System.out.println("Here");
            throw new OutOfRangeSampleSize();
        }
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
    
    /**
     * Compares two transactions
     * @param i The index of the first transaction
     * @param j The index of the second transaction
     * @return TRUE if the transactions are equal, FALSE otherwise
     */
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
    
    /**
     * Compute the centroid of a given attribute analyzing the given set of
     * transactions.
     * @param idList The set of transactions
     * @param attribute The attribute
     * @return computePrototype of a ContinuousAttribute if the attribute
     * is an instance of ContinuousAttribute, computePrototype of a 
     * DiscreteAttribute otherwise
     */
    Object computePrototype(Set<Integer> idList, Attribute attribute) {
    	// Decide if the attribute is Discrete or Continuous(using RTTI)
        if(attribute instanceof ContinuousAttribute) {
        	return computePrototype(idList, (ContinuousAttribute) attribute);
        } else {
        	return computePrototype(idList, (DiscreteAttribute) attribute);
        }
    }
    
    
    /**
     * Compute the most frequent value for the given attribute in idList
     * @param idList The set of transactions
     * @param attribute The attribute to analyze
     * @return The computed centroid
     */
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
    /**
     * Compute the average attribute's value for idList
     * @param idList The set of transactions
     * @param attribute The attribute to analyze
     * @return The computed value
     */
    private Double computePrototype(Set<Integer> idList, ContinuousAttribute attribute) {
    	return attribute.getAVG(this, idList);
    }
}
