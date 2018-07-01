package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The DbAccess class is useful to carry out the access 
 * to a database. 
 * This class has several class fields used to initialize
 * the connection to a database management system and 
 * to access it with user credentials. 
 * DbAccess class methods are necessary to execute 
 * every needed operation to handle the connection.
 */
public class DbAccess {
	
	/**
	 * The name of the database management system to connect to.
	 */
	private static final String DBMS = "jdbc:mysql";
	
	/**
	 * The IP address of the server machine where
	 * the database to access is located.
	 */
	private static final String SERVER = "localhost";
	
	/**
	 * The name of the database to access.
	 */
	private static final String DATABASE = "MapDB";
	
	/**
	 * The port number on which the database management system
	 * accepts connections.
	 */
	private static final String PORT = "3306";
	
	/**
	 * The user name necessary to access the database.
	 */
	private static final String USER_ID = "MapUser";
	
	/**
	 * The password used for the identification of the user.
	 */
	private static final String PASSWORD = "map";
	
	/**
	 * The name of the driver used for the connection to 
	 * a database management system.
	 */
	String DRIVER_CLASS_NAME = "org.gjt.mm.mysql.Driver";
	
	/**
	 * A Connection class object necessary to handle 
	 * the connection to a database management system.
	 * This object is initialized with {@value} .
	 * 
	 * @see Connection
	 */
	private static Connection conn = null;
	
	/**
	 * Initializes the connection to a specific database 
	 * management system.
	 * First of all, the method gives to class loader the order 
	 * to load the driver of the database management system, 
	 * then it initializes the connection with the Connection's object
	 * created by the driver manager. This object will be used 
	 * to execute all the operations towards the database.
	 * 
	 * @throws DatabaseConnectionException thrown when an error in getting connection occurs
	 * @throws ClassNotFoundException thrown when the driver class cannot be found
	 * @throws IllegalAccessException thrown when an instance of a class cannot be created
	 * 		   because it is not accessible
	 * @throws InstantiationException thrown when a class cannot be instantiated
	 * @throws SQLException thrown when an error in accessing the database occurs or when 
	 * 		   given url is null or incomplete.
	 */
	public static void initConnection() 
		throws DatabaseConnectionException, ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException 
	{	
		String url = DBMS + "://" + SERVER + ":" + PORT + "/" + DATABASE + "?user=" + USER_ID + "&password=" + PASSWORD;
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		conn = DriverManager.getConnection(url);
		if (conn == null) throw new DatabaseConnectionException(); // Check if a fail in getting connection can be considered as the return of a null pointer. 
	}
	
	public static Connection getConnection() {
		return conn;
	}
	
	/**
	 * Closes the connection to the database by releasing 
	 * the Connection's object.
	 * 
	 * @throws SQLException thrown when trying to close a not
	 * 	       initialized connection.
	 */
	public static void closeConnection() throws SQLException {
		conn.close();
	}
}
