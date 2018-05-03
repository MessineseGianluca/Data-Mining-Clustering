public class ClusterSet {
	private Cluster C[];
	private int i = 0;
	
	ClusterSet(int k) {
		C = new Cluster[k];
	}
	
	void add(Cluster c) {
		C[i] = c;
		i++;
	}
	
	Cluster get(int i) {
		return C[i];
	}
	
	void initializeCentroids(Data data) {
		int centroidIndexes[] = data.sampling(C.length);
		for(int i = 0; i < centroidIndexes.length; i++) {
			Tuple centroid = data.getItemSet(centroidIndexes[i]);
			add(new Cluster(centroid));
		}
	}
	
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
	
	Cluster currentCluster(int id) {
		boolean found = false;
		int i = 0;
		// check if the tuple identified by id is in a cluster stored in C.
		while(i < C.length && !found) {
			found = C[i].contain(id);
			i++;
		}
		if(found) {
			return C[i-1];
		}
		else {
			return null;
		}
	}
	
	void updateCentroids(Data data) {
		int i;
		for(i = 0; i < C.length; i++) {
			C[i].computeCentroid(data);
		}
	}
	
	public String toString() {
		int i;
		String str = "";
		for(i = 0; i < C.length; i++) {
			str += i + ":" + C[i].getCentroid() + "\n";
		}
		return str;	
	}
	
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
