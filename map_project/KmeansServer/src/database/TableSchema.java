package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TableSchema {
	List<Column> tableSchema = new ArrayList<Column>(); // list of fields of the table
	
	public class Column {
		private String name;
		private String type;
		
		Column(String name, String type) {
			this.name = name;
			this.type = type;
		}
		
		public String getColumnName() {
			return name;
		}
		
		public boolean isNumber() {
			return type.equals("number");
		}
		
		public String toString() {
			String str = name + ":" + type;
			return str;
		}
	}
	
	public TableSchema(String tableName) throws SQLException {
		HashMap<String, String> SQL_JAVATypes = new HashMap<String, String>();
		// http://java.sun.com/j2se/1.3/docs/guide/jdbc/getstart/mapping.html
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
		     


