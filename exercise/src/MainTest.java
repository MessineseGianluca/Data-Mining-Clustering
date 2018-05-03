public class MainTest {
	public static void main(String[] args) {
		Data data = new Data();
		int k = 3; // 3 clusters
		System.out.println(data);
		KmeansMiner kmeans = new KmeansMiner(k);
		int numIter = kmeans.kmeans(data);
		System.out.println("Numero di Iterazioni: " + numIter);
		System.out.println(kmeans.getC().toString(data));	
	}
}
