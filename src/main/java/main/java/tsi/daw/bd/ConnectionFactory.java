package main.java.tsi.daw.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	private static final String NAME_DATABASE = "clube_leitura";
	private static final String URL_POSTGRESQL = "jdbc:postgresql://localhost/" + NAME_DATABASE;
	private static final String USER = "aluno";
	private static final String PASSWORD = "aluno";
	
	public static Connection getConnection() {

		try {
			Class.forName("org.postgresql.Driver");
			return DriverManager.getConnection(URL_POSTGRESQL, USER, PASSWORD);
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println(e);
		}
		
		return null;
	}
}
