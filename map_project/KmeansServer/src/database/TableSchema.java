package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The TableSchema class is used to create a schema for a table
 * stored in a relational database.
 * A table schema collects information about fields of the table
 * which it refers to, such as the name of the field's type and 
 * the category this type belongs to; for example it could be a numeric
 * type or a string type.
 * The information about a singular field is represented by a 
 * column which can be considered as a couple composed by two strings:
 * the first string refers to the name of the field's type,
 * the second string refers to the type property.
 * The TableSchema class has a method to get the information about 
 * a specific column of the table and a method to get the number of columns
 * in the table.
 */
public class TableSchema {
	
	/**
	 * The collection of fields that compose the schema of a specific table.
	 * 
	 * @see List
	 */
	List<Column> tableSchema = new ArrayList<Column>();
	
	/**
	 * The Column class is useful to represent the information about fields
	 * of a database's table.
	 * This class has methods to get the name of a specific field's type, to verify
	 * if the field accepts numeric values and to get a string containing 
	 * all information about the specific field.
	 */
	public class Column {
		
		/**
		 * The name of the field's type.
		 */
		private String name;
		
		/**
		 * The category the field's type belongs to.
		 */
		private String type;
		
		/**
		 * Constructs a new Column object by setting the name of the field's type
		 * and its property.
		 * 
		 * @param name The string to set as name of the type.
		 * @param type The string representing type property.
		 */
		Column(String name, String type) {
			this.name = name;
			this.type = type;
		}
		
		public String getColumnName() {
			return name;
		}
		
		/**
		 * Checks if the type of a specific field is numeric or not.
		 * 
		 * @return true if type is set as number, false otherwise.
		 */
		public boolean isNumber() {
			return type.equals("number");
		}
		
		/**
		 * Returns a string representing this Column's values.
		 */
		public String toString() {
			String str = name + ":" + type;
			return str;
		}
	}
	
	/**
	 * Constructs a new TableSchema object by setting the name of table 
	 * it refers to and the information about fields of the table.
	 * To obtain the information about each field, it is indispensable to use
	 * a DatabaseMetaData's object which collects all information about tables 
	 * stored in a specific database. Moreover, to set the TableSchema object 
	 * it is necessary a type mapping phase because data types in SQL and 
	 * data types in the Java programming language are not identical and 
	 * there needs to be some mechanism for transferring data between 
	 * an application using Java types and a database using SQL types.
	 * 
	 * @param tableName The name of a specific table 
	 * @throws SQLException Thrown when an error occurs while trying to get
			   information about a specific table stored in a database.
	 * @see DatabaseMetaData
	 * @see HashMap
	 */
	public TableSchema(String tableName) throws SQLException {
		HashMap<String, String> SQL_JAVATypes = new HashMap<String, String>();
		// https://docs.oracle.com/javase/1.5.0/docs/guide/jdbc/getstart/mapping.html
		SQL_JAVATypes.put("CHAR", "string");
		SQL_JAVATypes.put("VARCHAR", "string");
		SQL_JAVATypes.put("LONGVARCHAR", "string");
		SQL_JAVATypes.put("BIT", "string");
		SQL_JAVATypes.put("SHORT", "number");
		SQL_JAVATypes.put("INT", "number");
		SQL_JAVATypes.put("LONG", "number");
		SQL_JAVATypes.put("FLOAT", "number");
		SQL_JAVATypes.put("DOUBLE", "number");
		
		Connection conn = DbAccess.getConnection();
		DatabaseMetaData meta = conn.getMetaData();
	    ResultSet res = meta.getColumns(null, null, tableName, null);   
	    while(res.next()) {
	    	if(SQL_JAVATypes.containsKey(res.getString("TYPE_NAME"))) {
	    		tableSchema.add(new Column(
	        		res.getString("COLUMN_NAME"),
	        		SQL_JAVATypes.get(res.getString("TYPE_NAME")))
	        	);
	    	}     
	    }
	    res.close();
	}
	
	public int getNumberOfAttributes() {
		return tableSchema.size();
	}
	
	public Column getColumn(int index) {
		return tableSchema.get(index);
	}
}
		     


