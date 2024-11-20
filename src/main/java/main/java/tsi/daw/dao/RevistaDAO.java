package main.java.tsi.daw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.tsi.daw.bd.ConnectionFactory;
import main.java.tsi.daw.bd.DataBaseSchema;
import main.java.tsi.daw.model.Caixa;
import main.java.tsi.daw.model.Revista;

/**
 * Classe de acesso a dados (DAO) para manipulação de objetos {@link Revista} no
 * banco de dados.
 * 
 * A classe fornece métodos para realizar operações CRUD (Create, Read, Update,
 * Delete) na tabela de revistas do banco de dados.
 */
public class RevistaDAO {

	private static final String ERROR_INSERT = "Erro ao inserir revista no banco de dados";
	private static final String ERROR_UPDATE = "Erro ao atualizar revista no banco de dados";
	private static final String ERROR_DELETE = "Erro ao deletar revista no banco de dados";
	private static final String ERROR_LIST = "Erro ao listar revistas do banco de dados";
	private static final String ERROR_FIND_BY_ID = "Erro ao buscar revista pelo ID no banco de dados";

	private Connection connection;

	public RevistaDAO() {
		this.connection = ConnectionFactory.getConnection();
	}

	/**
	 * Insere uma nova revista no banco de dados.
	 *
	 * @param revista Objeto Revista a ser inserido no banco de dados
	 */
	public void insert(Revista revista) {

		String sql = 
				String.format("INSERT INTO %s (%s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?)", 
				DataBaseSchema.REVISTA.getTableName(),
				DataBaseSchema.REVISTA.getColumns()[1],
				DataBaseSchema.REVISTA.getColumns()[2],
				DataBaseSchema.REVISTA.getColumns()[3],
				DataBaseSchema.REVISTA.getColumns()[4],
				DataBaseSchema.REVISTA.getColumns()[5]);

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setString(1, revista.getColecao());
			preparedStatement.setInt(2, revista.getNumeroEdicao());
			preparedStatement.setInt(3, revista.getAnoRevista());
			preparedStatement.setBoolean(4, revista.isDisponibilidade());
			preparedStatement.setLong(5, revista.getCaixa().getIdCaixa());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(ERROR_INSERT);
		}
	}

	/**
	 * Atualiza uma revista existente no banco de dados.
	 *
	 * @param revista Objeto Revista com os dados atualizados
	 */
	public void update(Revista revista) {
		
		String sql = 
				String.format("UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?", 
				DataBaseSchema.REVISTA.getTableName(),
				DataBaseSchema.REVISTA.getColumns()[1],
				DataBaseSchema.REVISTA.getColumns()[2],
				DataBaseSchema.REVISTA.getColumns()[3],
				DataBaseSchema.REVISTA.getColumns()[4],
				DataBaseSchema.REVISTA.getColumns()[5],
				DataBaseSchema.REVISTA.getColumns()[0]);

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setString(1, revista.getColecao());
			preparedStatement.setInt(2, revista.getNumeroEdicao());
			preparedStatement.setInt(3, revista.getAnoRevista());
			preparedStatement.setBoolean(4, revista.isDisponibilidade());
			preparedStatement.setLong(5, revista.getCaixa().getIdCaixa());
			preparedStatement.setLong(6, revista.getIdRevista());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(ERROR_UPDATE);
		}
	}

	/**
	 * Deleta uma revista do banco de dados.
	 *
	 * @param revista Objeto Revista a ser deletado
	 */
	public void delete(Revista revista) {
		
		String sql = 
				String.format("DELETE FROM %s WHERE %s = ?", 
				DataBaseSchema.REVISTA.getTableName(),
				DataBaseSchema.REVISTA.getColumns()[0]);

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setLong(1, revista.getIdRevista());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(ERROR_DELETE);
		}
	}
	
	/**
	 * Metódo auxiliar que transforma um resultSet de consulta em um objeto Revista
	 * 
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	private Revista mapResultSetToRevista(ResultSet resultSet) throws SQLException {
		
	    Revista revista = new Revista();
	    revista.setIdRevista(resultSet.getLong(DataBaseSchema.REVISTA.getColumns()[0]));
	    revista.setColecao(resultSet.getString(DataBaseSchema.REVISTA.getColumns()[1]));
	    revista.setNumeroEdicao(resultSet.getInt(DataBaseSchema.REVISTA.getColumns()[2]));
	    revista.setAnoRevista(resultSet.getInt(DataBaseSchema.REVISTA.getColumns()[3]));
	    revista.setDisponibilidade(resultSet.getBoolean(DataBaseSchema.REVISTA.getColumns()[4]));

	    Caixa caixa = new Caixa();
	    caixa.setIdCaixa(resultSet.getLong(DataBaseSchema.REVISTA.getColumns()[5]));
	    revista.setCaixa(caixa);

	    return revista;
	}


	/**
	 * Busca uma revista pelo seu ID.
	 *
	 * @param id ID da Revista a ser encontrada
	 * @return Objeto Revista correspondente ao ID, ou null se não encontrado
	 */
	public Revista findById(long id) {
		
		String sql = 
				String.format("SELECT * FROM %s WHERE %s = ?", 
				DataBaseSchema.REVISTA.getTableName(),
				DataBaseSchema.REVISTA.getColumns()[0]);
		
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setLong(1, id);

			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next())
	            return mapResultSetToRevista(resultSet);
		} catch (SQLException e) {
			throw new RuntimeException(ERROR_FIND_BY_ID);
		}

		return null;
	}
	
	/**
	 * Lista todas as revistas cadastradas no banco de dados.
	 *
	 * @return Lista de objetos Revista
	 */
	public List<Revista> list() {
		
		List<Revista> listRevistas = new ArrayList<>();

		String sql = 
				String.format("SELECT * FROM %s", 
				DataBaseSchema.REVISTA.getTableName());

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next())
	            listRevistas.add(mapResultSetToRevista(resultSet));
		} catch (SQLException e) {
			throw new RuntimeException(ERROR_LIST);
		}

		return listRevistas;
	}
	
	/**
	 * Lista revistas com base na disponibilidade.
	 *
	 * @param disponibilidade Disponibilidade das revistas (true para disponíveis, false para indisponíveis)
	 * @return Lista de objetos Revista filtrados pela disponibilidade
	 */
	public List<Revista> listByDisponibilidade(boolean disponibilidade) {

	    List<Revista> listRevistas = new ArrayList<>();

	    String sql = 
	    		String.format("SELECT * FROM %s WHERE %s = ?", 
				DataBaseSchema.REVISTA.getTableName(),
				DataBaseSchema.REVISTA.getColumns()[4]);

	    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	    	
	    	
	        preparedStatement.setBoolean(1, disponibilidade);
	        ResultSet resultSet = preparedStatement.executeQuery();

	        while (resultSet.next())
	            listRevistas.add(mapResultSetToRevista(resultSet));
	        
	    } catch (SQLException e) {
	        throw new RuntimeException(ERROR_LIST);
	    }

	    return listRevistas;
	}
} // class RevistaDAO