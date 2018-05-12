package mining;
import data.Data;
import data.Tuple;
import java.util.HashSet;
import java.util.Set;

public class Cluster {
	private Tuple centroid;
	Set<Integer> clusteredData; 

	Cluster(Tuple centroid) {
		this.centroid = centroid;
		clusteredData = new HashSet<Integer>();
	}
		
	Tuple getCentroid() {
		return centroid;
	}
	
	void computeCentroid(Data data) {
		for(int i = 0; i < centroid.getLength(); i++) {
			centroid.get(i).update(data, clusteredData);
		}
	}
	
	// return true if the tuple is changing cluster.
	boolean addData(int id) {
		return clusteredData.add(id);
	}
	
	// verify that a transaction is clustered in the current arrayset clusteredData.
	boolean contain(int id) {
		return clusteredData.contains(id);
	}
	
	// remove the tuple that has changed the cluster.
	void removeTuple(int id) {
		clusteredData.remove(id);	
	}
	
	public String toString() {
		String str = "Centroid = ";
		str += centroid.toString();
		return str;
	}
	
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
