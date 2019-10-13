package threelogic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
	
	static Connection connection;
	DbConnection() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
		String url = "jdbc:sqlserver://127.0.0.1:1433;DatabaseName=GUPIAO;";
		connection=DriverManager.getConnection(url,"sa","qhq15623282518");
		/*Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
		String url = "jdbc:sqlserver://rds.sqlserver.rds.aliyuncs.com:3433;DatabaseName=GUPIAO;";
		connection=DriverManager.getConnection(url,"admin","qhq000402");*/
	}
	
}
