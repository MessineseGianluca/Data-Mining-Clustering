package mining;
import java.io.*;
import data.Data;
import data.OutOfRangeSampleSize;

/**
 * A KmeansMiner instance is used for generating, handling and
 * storing a clusterSet of a given number of clusters. 
 * These clusters are computed based on the transactions 
 * in the system. 
 */

public class KmeansMiner {
    
	/**
	 * The set of clusters to handle.
	 * @see ClusterSet
	 */
	private ClusterSet C;
    
	/**
	 * KmeansMiner's constructor
	 * @param k The number of clusters to generate
	 */
    public KmeansMiner(int k) {
        C = new ClusterSet(k);
    }
    
    /**
     * KmeansMiner's constructor which initialize C reading data from a file.
     * @param fileName The name of the file from which the clusterSet is read.
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public KmeansMiner(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
        C = (ClusterSet)in.readObject();
        in.close();
    }
    
    /**
     * It stores C to a file.
     * @param filename The name of the file from which the clusterSet is read.
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void save(String filename) throws FileNotFoundException, IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
        out.writeObject(C);
        out.close();
    }
    
    public ClusterSet getC() {
        return C;
    }
    
    /**
     * Computes the most accurate clusterSet for data.
     * @param data The whole set of transactions on which the system
	 * works
     * @return The number of iterations to compute C
     * @throws OutOfRangeSampleSize when the number k of clusters
     * is greater than the number of transactions in data.
     */
    public int kmeans(Data data) throws OutOfRangeSampleSize {
        int numberOfIterations = 0;
        
        // Random initialization of k centroids
        C.initializeCentroids(data);
        boolean changedCluster;
        
        /* 
         * It goes on until the last iteration mantains the
         * the clusterSet unchanged. This means it computes
         * the best clusterSet for the transactions in data.
         */
        do {
            numberOfIterations++;
            // Computes the clusters based on their centroids
            changedCluster = false;
            for(int i = 0; i < data.getNumberOfExamples(); i++) {
                Cluster nearestCluster = C.nearestCluster(data.getItemSet(i));
                Cluster oldCluster = C.currentCluster(i);
                boolean currentChange = nearestCluster.addData(i);
                if(currentChange) {
                    changedCluster = true;
                }
                // Removes the tuple from the old cluster
                if(currentChange && oldCluster != null) {
                    oldCluster.removeTuple(i);            
                }
            }           
            // Updates centroids based on the transactions in the clusters
            C.updateCentroids(data);
        } while(changedCluster);
        return numberOfIterations;
    }
    
    public String toString() {
        return C.toString();
    }
}

