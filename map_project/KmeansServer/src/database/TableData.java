package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;



import database.TableSchema.Column;


public class TableData {
	public List<Example> getDistinctTransactions(String table) throws SQLException, EmptySetException {
		List<Example> dataList = new ArrayList<Example>();
		TableSchema schema = new TableSchema(table);
		String query = "SELECT DISTINCT * FROM" + table;
		Statement stmt = DbAccess.getConnection().createStatement();
		ResultSet rs = stmt.executeQuery(query);
		while(rs.next()) {
			Example ex = new Example();
			for(int i = 0; i < schema.getNumberOfAttributes(); i++) {
				if(schema.getColumn(i).isNumber()) {
					ex.add(rs.getFloat(i));
				}
				else {
					ex.add(rs.getString(i));
				}
			}
			dataList.add(ex);
		}
		rs.close();
		return dataList;
	}

	public Set<Object> getDistinctColumnValues(String table, Column column) throws SQLException {
		Set<Object> valuesOfTheColumn = new TreeSet<Object>();
		TableSchema schema = new TableSchema(table);
		String query = "SELECT DISTINCT" + column.getColumnName() + "FROM" + "ORDER BY ASC";
		Statement stmt = DbAccess.getConnection().createStatement();
		ResultSet rs = stmt.executeQuery(query);
		int i = 0; 
        boolean flag = false;
        while(i < schema.getNumberOfAttributes() && !flag) {
            if(column.getColumnName().equals(schema.getColumn(i).getColumnName())) {
                flag = true;
            }
            i++; 
        }
        if(schema.getColumn(i).isNumber()) {
            while(rs.next()) {
                valuesOfTheColumn.add(rs.getFloat(i));
            }
        } else {
            while(rs.next()) {
                valuesOfTheColumn.add(rs.getString(i));
            }
        }
        rs.close();
        return valuesOfTheColumn;    
	}

	public Object getAggregateColumnValue(String table, Column column, QueryType aggregate) throws SQLException, NoValueException {
		TableSchema schema = new TableSchema(table);
        String query = "SELECT" + aggregate + "(" + column.getColumnName() + ")" + "FROM" + table + "ORDER BY ASC";
        Statement stmt = DbAccess.getConnection().createStatement();
        ResultSet rs = stmt.executeQuery(query);
        int i = 0; 
        boolean flag = false;
        while(i < schema.getNumberOfAttributes() && !flag) {
            if(column.getColumnName().equals(schema.getColumn(i).getColumnName())) {
                flag = true;
            }
            i++; 
        }
        if(schema.getColumn(i).isNumber()) {
        	float number = rs.getFloat(i);
        	rs.close();
        	return number;
        } else {
            String str = rs.getString(i);
            rs.close();
            return str;
        }      
	}
}