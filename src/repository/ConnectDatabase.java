package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDatabase {

	Connection dbConnection = null;
	String dbName = "jdbc:mysql://localhost/footballsportscomplex";
	String userName = "root";
	String password = "";

	public Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();

			dbConnection = DriverManager.getConnection(dbName, userName, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dbConnection;
	}
}
