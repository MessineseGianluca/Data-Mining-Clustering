package mining;
import java.io.Serializable;

import data.Data;
import data.Tuple;
import data.OutOfRangeSampleSize;

/**
 * A ClusterSet instance contains the set of clusters
 * currently in the system. A clusterSet is serializable.
 */

public class ClusterSet implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Array of Clusters.
	 * @see Cluster
	 */
	private Cluster C[];
	
	/**
	 * The index of the next Cluster to
	 * add in the system.
	 */
	private int nextCluster = 0;
	
	/**
	 * ClusterSet's constructor
	 * @param k The number of clusters to store.
	 */
	ClusterSet(int k) {
		C = new Cluster[k];
	}
	
	/**
	 * Adds a new cluster in C.
	 * @param c The cluster to add.
	 */
	void add(Cluster c) {
		C[nextCluster] = c;
		nextCluster++;
	}
	
	Cluster get(int i) {
		return C[i];
	}
	
	/**
	 * Initialize centroids for the clusters stored in C.
	 * It assigns for each cluster a random transaction chosen
	 * from data.
	 * @param data The whole set of transactions on which the system
	 * works.
	 * @throws OutOfRangeSampleSize when the number k of clusters stored
	 * in C is greater than the number of transactions in data.
	 */
	void initializeCentroids(Data data) throws OutOfRangeSampleSize {
		// random choice of transactions in data to use as centroids
		int centroidIndexes[] = data.sampling(C.length);
		for(int i = 0; i < centroidIndexes.length; i++) {
			// generate the centroid strating from the chosen 
			// transaction in data
			Tuple centroid = data.getItemSet(centroidIndexes[i]);
			add(new Cluster(centroid));
		}
	}
	
	/**
	 * Given a tuple, it computes the nearest cluster for it
	 * @param tuple The tuple to match
	 * @return The index of the nearest cluster for tuple
	 */
	Cluster nearestCluster(Tuple tuple) {
		Cluster nearest = C[0];
		double dist = tuple.getDistance(C[0].getCentroid());
		double comp;
		// look for the nearest cluster of tuple.
		for(int i = 1; i < C.length; i++) {
			comp = tuple.getDistance(C[i].getCentroid());
			if(comp < dist) {
				dist = comp;
				nearest = C[i];
			}
		}
		return nearest;
	}
	
	/**
	 * @param id The id of the tuple
	 * @return the index of the cluster on which the
	 * tuple is "stored"
	 */
	Cluster currentCluster(int id) {
		boolean found = false;
		int i = 0;
		// checks if the tuple identified by id is in a cluster stored in C.
		while(i < C.length && !found) {
			found = C[i].contain(id);
			i++;
		}
		if(found) {
			return C[i - 1];
		}
		else {
			return null;
		}
	}
	
	/**
	 * Based on the transactions in the clusters, it computes a new
	 * centroid for each of them
	 * @param data The whole set of transactions on which the system
	 * works.
	 */
	void updateCentroids(Data data) {
		int i;
		for(i = 0; i < C.length; i++) {
			C[i].computeCentroid(data);
		}
	}
	
	/**
	 * @return The string representing the centroids' values
	 * of the clusters stored in C.
	 */
	public String toString() {
		int i;
		String str = "";
		for(i = 0; i < C.length; i++) {
			str += i + ":" + C[i].getCentroid() + "\n";
		}
		return str;
	}
	
	/**
	 * @param data The whole set of transactions on which the system
	 * works.
	 * @return The string representing the whole information
	 * of the clusters stored in C.
	 */
	public String toString(Data data) {
		int i;
		String str = "";
		for(i = 0; i < C.length; i++) {
			if(C[i] != null) {
				str += i + ") " + C[i].toString(data) + "\n";		
			}
		}
		return str;		
	}
}
