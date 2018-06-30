package share;

/**
 * The WriteRequest class represents writing on file requests.
 * A write request represents the writing operation of clustering
 * results on a specific file.
 * A WriteRequest object encapsulates all the information 
 * needed for data mining operation, such as the database table name
 * from which data are collected, the number of clusters to generate
 * in the computing phase and the name of file on which the result 
 * of the data mining activity should be saved.
 * The WriteRequest class has methods to get the database table name 
 * chosen to collect data and the number of clusters to generate.
 * 
 * @see Request
 */
public class WriteRequest extends Request {
	
	/**
	 * The serial version ID needed to serialize instances of this
	 * class.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The name of database table chosen as source to collect data.
	 */
	private String DBtableName;
	
	/**
	 * The number of clusters to generate in data mining operation.
	 */
    private int numberOfClusters;
    
    /**
     * Constructs a new WriteRequest object by setting the service 
     * to demand, the name of file used to write the result of data
     * mining operation, the number of clusters to generate and the
     * name of database table for acquiring of data.
     * 
     * @param menuChoice The service chosen for the request.
     * @param fileName The name of file to use for saving operation.
     * @param numberOfClusters The number of clusters to generate.
     * @param table The database table name.
     */
    public WriteRequest(int menuChoice, String fileName, int numberOfClusters, String table) {
        super(menuChoice, fileName);
        this.numberOfClusters = numberOfClusters; 
        DBtableName = table;
    }
    
    public String getDBtableName() {
        return DBtableName;
    }
    
    public int getNumberOfClusters() {
        return numberOfClusters;
    }
}
