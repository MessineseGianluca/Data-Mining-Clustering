package share;

/**
 * The ReadRequest class represents reading from file requests.
 * A ReadRequest object encapsulates all the information needed 
 * for a reading operation from file, whose name is set when the
 * object is created.
 * 
 * @see Request
 */
public class ReadRequest extends Request {
	
	/**
	 * The serial version ID needed to serialize instances of this
	 * class.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a new ReadRequest object by setting the service 
     * to demand and the name of file used for reading operation.
	 * 
	 * @param fileName The file name used for the reading operation.
	 */
	public ReadRequest(String fileName) {
        super(fileName);
    }   
}