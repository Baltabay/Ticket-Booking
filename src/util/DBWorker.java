package util;


import java.sql.*; 
public class DBWorker {
	
	private final String HOST = "jdbc:mysql://localhost:3306/dbcinema";
	private final String USERNAME = "root";
	private final String PASSWORD = "root";
	
	private Connection connection;
	
	public DBWorker() {
		try {
			connection = DriverManager.getConnection(HOST, USERNAME, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		return connection;
	}
}
