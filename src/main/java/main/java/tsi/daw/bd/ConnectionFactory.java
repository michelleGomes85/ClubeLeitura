package main.java.tsi.daw.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A classe ConnectionFactory fornece um método para criar e obter uma conexão
 * com o banco de dados. Ela utiliza a URL, usuário e senha para se conectar ao
 * banco de dados PostgreSQL.
 */
public class ConnectionFactory {

	private static final String NAME_DATABASE = "clube_leitura";
	private static final String URL_POSTGRESQL = "jdbc:postgresql://localhost/" + NAME_DATABASE;
	private static final String USER = "aluno";
	private static final String PASSWORD = "aluno";
	
	private static final String DRIVE_BD = "org.postgresql.Driver";
	
    /**
     * Método que retorna uma conexão com o banco de dados PostgreSQL.
     * Este método carrega o driver JDBC do PostgreSQL e cria a conexão
     * usando a URL, usuário e senha definidos.
     * 
     * @return Connection objeto de conexão com o banco de dados ou null em caso de erro.
     */
	public static Connection getConnection() {

		try {
			Class.forName(DRIVE_BD);
			return DriverManager.getConnection(URL_POSTGRESQL, USER, PASSWORD);
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println(e);
		}
		
		return null;
	}
}
