public class WriteRequest extends Request {
    private String DBtableName;
    private int numberOfClusters;
    
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
