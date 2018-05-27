package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbAccess {
	private static final String DBMS = "jdbc:mysql";
	private static final String SERVER = "localhost";
	private static final String DATABASE = "MapDB";
	private static final String PORT = "3306";
	private static final String USER_ID = "MapUser";
	private static final String PASSWORD = "map";
	private static Connection conn;
	String DRIVER_CLASS_NAME = "org.gjt.mm.mysql.Driver";
	
	public static void initConnection() throws
	DatabaseConnectionException, ClassNotFoundException, IllegalAccessException, 
	InstantiationException, SQLException {
		String url = DBMS + "://" + SERVER + ":" + PORT + "/" + DATABASE + "?user=" + USER_ID + "&password=" + PASSWORD;
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		Connection conn = DriverManager.getConnection(url);
		if (conn == null) throw new DatabaseConnectionException(); // Check if a fail in getting connection can be considered as the return of a null pointer. 
	}
	
	public static Connection getConnection() {
		return conn;
	}
	
	public static void closeConnection() throws SQLException {
		conn.close();
	}
}
