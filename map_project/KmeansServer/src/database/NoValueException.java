package database;

/**
 * Thrown when tuples contained in a ResultSet returned by
 * the execution of a query do not have values in some fields.
 * 
 * @see Exception
 */
public class NoValueException extends Exception {
	
	/**
	 * The serial version ID needed to serialize instances of this
	 * class.
	 */
	private static final long serialVersionUID = 1L;
}
