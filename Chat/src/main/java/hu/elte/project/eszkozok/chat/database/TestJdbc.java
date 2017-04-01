package hu.elte.project.eszkozok.chat.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestJdbc {

	public static void main(String[] args) {
		String user = "project_chat";
		String pass = "project_chat";
		String jdbcUrl = "jdbc:mysql://localhost:3306/project_chat?useSSL=false";
		
		try (Connection myCon = DriverManager.getConnection(jdbcUrl, user, pass)) {
			System.out.println("Connecting to database: " + jdbcUrl);
			System.out.println("Connection successfull!");
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}
}
