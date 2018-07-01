package mining;
import data.Data;
import data.Tuple;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Cluster class instance represents a set of 
 * transactions whose values are very close to 
 * each other.
 * Each cluster is represented by a centroid.
 * Each cluster has a different centroid
 * from the others.
 */
public class Cluster implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * A centroid is a Tuple instance representing 
	 * the average values of the transactions in the 
	 * cluster'set. Each cluster in the system has 
	 * a different centroid.
	 * 
	 * @see data.Tuple
	 */
	private Tuple centroid;
	
	/**
	 * Contains the indexes of the transactions
	 * which belong to the cluster.
	 */
	private Set<Integer> clusteredData;
	
	/** 
	 * Cluster's constructor 
	 */
	Cluster(Tuple centroid) {
		this.centroid = centroid;
		clusteredData = new HashSet<Integer>();
	}
		
	Tuple getCentroid() {
		return centroid;
	}
	
	/** 
	 * It computes a new centroid based on the clusteredData's
	 * transactions.
	 * 
	 * @param data The whole set of transactions on which the system
	 * is working.
	 * */
	void computeCentroid(Data data) {
		for(int i = 0; i < centroid.getLength(); i++) {
			centroid.get(i).update(data, clusteredData);
		}
	}
	
	/** 
	 * Adds an index on the clusteredData. 
	 * 
	 * @param id The index of the transaction to add to the 
	 * cluster
	 * @return true if the transaction changes the cluster.
	 * */
	boolean addData(int id) {
		return clusteredData.add(id);
	}
	
	/**
	 * Verifies if the transaction is clustered in clusteredData.
	 * 
	 * @param id The index of the transaction to check
	 * @return true if the transaction is registered in clusterData
	 */
	boolean contain(int id) {
		return clusteredData.contains(id);
	}
	
	/**
	 * Removes the transaction.
	 * @param id The index of the transaction to remove
	 */
	void removeTuple(int id) {
		clusteredData.remove(id);	
	}
	
	/**
	 * @return A String containing the centroid's values.
	 */
	public String toString() {
		String str = "Centroid = ";
		str += centroid.toString();
		return str;
	}
	
	/**
	 * @param data The whole set of transactions on which the system
	 * is working.
	 * @return A string containing the centroid's values,
	 * along with the transactions' values in the Cluster
	 */
	public String toString(Data data) {
		String str = "Centroid = ";
		str += centroid.toString();
		str += "\nExamples:\n";
		Integer array[] = clusteredData.toArray(new Integer[0]);
		for(int i = 0; i < array.length; i++) {
			str += "[ ";
			for(int j = 0; j < data.getNumberOfAttributes(); j++)
				str += data.getAttributeValue(array[i], j) + " ";
			str += "] => dist=" + getCentroid().getDistance(data.getItemSet(array[i])) + "\n";	
		}
		str += "AvgDistance = " + getCentroid().avgDistance(data, array) + "\n";
		return str;	
	}
}
