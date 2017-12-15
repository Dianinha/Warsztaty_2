package mypackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectorToDatabase {

	public static Connection getConnection(){
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pschool?useSSL=false","root","coderslab");
		} catch (SQLException e) {
			System.out.println("Połączenie z bazą danych nie udało się." + e.getMessage());
		}
		return conn;
	}
}
