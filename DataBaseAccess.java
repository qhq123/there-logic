package threelogic;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseAccess {
	String sql = "select * from Table1";
	int colcount ;
	static int rowcount;
	static Statement s;
	static String[] data;
	DataBaseAccess() throws SQLException
	{
		Statement s=DbConnection.connection.createStatement();
		ResultSet rs =s.executeQuery(sql);
		ResultSetMetaData rsmd = rs.getMetaData();
		colcount = rsmd.getColumnCount();
		rowcount=0;
		while(rs.next()) {rowcount++;}
		data=new String[4*rowcount];
		int m=0;
		ResultSet y=s.executeQuery(sql);
	    while(y.next())
	    {	
	    	for (int i = 1; i < colcount+1; i++) {//以列的数值为终止行，来打印！
	            if(i==3||i==6||i==7||i==13)
	            {
	            	data[m]=y.getString(i);
	            	m++;
	            }
	        }
	    }
	}
}
