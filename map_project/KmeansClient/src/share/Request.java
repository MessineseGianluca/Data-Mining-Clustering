package share;

import java.io.Serializable;

/**
 * Request is the base class for all requests which can be submitted
 * by a client application and handled by a server application.
 * The request represents a service demanded by the user and 
 * supplied by a specific server.
 * A Request object encapsulates all the information needed
 * for the supply of a particular service.
 * The Request class has methods to get the value of the service
 * chosen and the filename set for the specific request.
 */
public class Request implements Serializable {
	
	/**
	 * The serial version ID needed to serialize instances of this
	 * class.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The name of file chosen for read or write operation.
	 * In case of read operation it represents the source
	 * file of information, otherwise it represents the
	 * destination file used to save information.
	 */
    private String fileName;
    
    /**
     * Constructs a new Request object by setting the service 
     * to demand and the name of file used in the specific operation.
     * 
     * @param menuChoice The service chosen for the request.
     * @param fileName The name of file to use for the operation.
     */
    public Request(String fileName) {
        this.fileName = fileName;
    }
    
    public String getFileName() {
        return fileName;
    }
}