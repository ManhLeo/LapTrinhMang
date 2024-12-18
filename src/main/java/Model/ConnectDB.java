package Model;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectDB {
	public static Connection connectDatabase() {
			
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			return DriverManager.getConnection("jdbc:mysql://localhost:3306/btckfinal", "root", "");
		}catch(Exception e) {
			System.out.print("Connection failed" + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
}
