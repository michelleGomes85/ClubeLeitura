package main.java.tsi.daw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.tsi.daw.bd.ConnectionFactory;
import main.java.tsi.daw.model.Revista;
import main.java.tsi.daw.model.Caixa;

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

	private static final String TABLE_NAME = "revista";
	
	private static final String COLUMN_ID = "idrevista";
	private static final String COLUMN_COLECAO = "colecao";
	private static final String COLUMN_NUMERO_EDICAO = "num_edicao";
	private static final String COLUMN_ANO_REVISTA = "ano_revista";
	private static final String COLUMN_DISPONIBILIDADE = "disponibilidade";
	private static final String COLUMN_CAIXA_ID = "idcaixa";

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

		String sql = String.format("INSERT INTO %s (%s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?)", 
				TABLE_NAME,	COLUMN_COLECAO, COLUMN_NUMERO_EDICAO, COLUMN_ANO_REVISTA, 
				COLUMN_DISPONIBILIDADE, COLUMN_CAIXA_ID);

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setString(1, revista.getColecao());
			preparedStatement.setInt(2, revista.getNumeroEdicao());
			preparedStatement.setInt(3, revista.getAnoRevista());
			preparedStatement.setBoolean(4, revista.isDisponibilidade());
			preparedStatement.setLong(5, revista.getCaixa().getIdCaixa());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(ERROR_INSERT, e);
		}
	}

	/**
	 * Atualiza uma revista existente no banco de dados.
	 *
	 * @param revista Objeto Revista com os dados atualizados
	 */
	public void update(Revista revista) {
		
		String sql = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?", 
				TABLE_NAME, COLUMN_COLECAO, COLUMN_NUMERO_EDICAO, COLUMN_ANO_REVISTA, 
				COLUMN_DISPONIBILIDADE, COLUMN_CAIXA_ID, COLUMN_ID);

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setString(1, revista.getColecao());
			preparedStatement.setInt(2, revista.getNumeroEdicao());
			preparedStatement.setInt(3, revista.getAnoRevista());
			preparedStatement.setBoolean(4, revista.isDisponibilidade());
			preparedStatement.setLong(5, revista.getCaixa().getIdCaixa());
			preparedStatement.setLong(6, revista.getIdRevista());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(ERROR_UPDATE, e);
		}
	}

	/**
	 * Deleta uma revista do banco de dados.
	 *
	 * @param revista Objeto Revista a ser deletado
	 */
	public void delete(Revista revista) {
		
		String sql = String.format("DELETE FROM %s WHERE %s = ?", TABLE_NAME, COLUMN_ID);

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setLong(1, revista.getIdRevista());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(ERROR_DELETE, e);
		}
	}

	/**
	 * Lista todas as revistas cadastradas no banco de dados.
	 *
	 * @return Lista de objetos Revista
	 */
	public List<Revista> list() {
		
		List<Revista> listRevistas = new ArrayList<>();

		String sql = String.format("SELECT * FROM %s", TABLE_NAME);

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				
				Revista revista = new Revista();
				revista.setIdRevista(resultSet.getLong(COLUMN_ID));
				revista.setColecao(resultSet.getString(COLUMN_COLECAO));
				revista.setNumeroEdicao(resultSet.getInt(COLUMN_NUMERO_EDICAO));
				revista.setAnoRevista(resultSet.getInt(COLUMN_ANO_REVISTA));
				revista.setDisponibilidade(resultSet.getBoolean(COLUMN_DISPONIBILIDADE));

				Caixa caixa = new Caixa();
				caixa.setIdCaixa(resultSet.getLong(COLUMN_CAIXA_ID));
				revista.setCaixa(caixa);

				listRevistas.add(revista);
			}
		} catch (SQLException e) {
			throw new RuntimeException(ERROR_LIST, e);
		}

		return listRevistas;
	}

	/**
	 * Busca uma revista pelo seu ID.
	 *
	 * @param id ID da Revista a ser encontrada
	 * @return Objeto Revista correspondente ao ID, ou null se não encontrado
	 */
	public Revista findById(long id) {
		
		String sql = String.format("SELECT * FROM %s WHERE %s = ?", TABLE_NAME, COLUMN_ID);

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setLong(1, id);

			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				Revista revista = new Revista();
				revista.setIdRevista(resultSet.getLong(COLUMN_ID));
				revista.setColecao(resultSet.getString(COLUMN_COLECAO));
				revista.setNumeroEdicao(resultSet.getInt(COLUMN_NUMERO_EDICAO));
				revista.setAnoRevista(resultSet.getInt(COLUMN_ANO_REVISTA));
				revista.setDisponibilidade(resultSet.getBoolean(COLUMN_DISPONIBILIDADE));

				Caixa caixa = new Caixa();
				caixa.setIdCaixa(resultSet.getLong(COLUMN_CAIXA_ID));
				revista.setCaixa(caixa);

				return revista;
			}
		} catch (SQLException e) {
			throw new RuntimeException(ERROR_FIND_BY_ID, e);
		}

		return null;
	}
	
	/**
	 * Lista revistas com base na disponibilidade.
	 *
	 * @param disponibilidade Disponibilidade das revistas (true para disponíveis, false para indisponíveis)
	 * @return Lista de objetos Revista filtrados pela disponibilidade
	 */
	public List<Revista> listByDisponibilidade(boolean disponibilidade) {

	    List<Revista> listRevistas = new ArrayList<>();

	    String sql = String.format("SELECT * FROM %s WHERE %s = ?", TABLE_NAME, COLUMN_DISPONIBILIDADE);

	    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	        preparedStatement.setBoolean(1, disponibilidade);
	        ResultSet resultSet = preparedStatement.executeQuery();

	        while (resultSet.next()) {
	            Revista revista = new Revista();
	            revista.setIdRevista(resultSet.getLong(COLUMN_ID));
	            revista.setColecao(resultSet.getString(COLUMN_COLECAO));
	            revista.setNumeroEdicao(resultSet.getInt(COLUMN_NUMERO_EDICAO));
	            revista.setAnoRevista(resultSet.getInt(COLUMN_ANO_REVISTA));
	            revista.setDisponibilidade(resultSet.getBoolean(COLUMN_DISPONIBILIDADE));

	            Caixa caixa = new Caixa();
	            caixa.setIdCaixa(resultSet.getLong(COLUMN_CAIXA_ID));
	            revista.setCaixa(caixa);

	            listRevistas.add(revista);
	        }
	    } catch (SQLException e) {
	        throw new RuntimeException(ERROR_LIST, e);
	    }

	    return listRevistas;
	}
	
	/**
	 * Lista revistas associadas a uma pessoa específica.
	 *
	 * @param idPessoa ID da pessoa cujas revistas serão buscadas
	 * @return Lista de objetos Revista associados à pessoa
	 */
	public List<Revista> listByPessoa(long idPessoa) {
		
	    String sql = "SELECT r.idrevista, r.colecao, r.num_edicao, r.ano_revista, r.disponibilidade "
	               + "FROM emprestimo e "
	               + "INNER JOIN revista r ON e.idRevista = r.idRevista "
	               + "WHERE e.idPessoa = ? AND e.datadevolucao IS NULL";

	    List<Revista> revistas = new ArrayList<>();

	    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	        preparedStatement.setLong(1, idPessoa);

	        ResultSet resultSet = preparedStatement.executeQuery();

	        while (resultSet.next()) {
	            Revista revista = new Revista();
	            revista.setIdRevista(resultSet.getLong("idrevista"));
	            revista.setColecao(resultSet.getString("colecao"));
	            revista.setNumeroEdicao(resultSet.getInt("num_edicao"));
	            revista.setAnoRevista(resultSet.getInt("ano_revista"));
	            revista.setDisponibilidade(resultSet.getBoolean("disponibilidade"));
	            revistas.add(revista);
	        }
	    } catch (SQLException e) {
	        throw new RuntimeException("Erro ao listar revistas por pessoa", e);
	    }

	    return revistas;
	}
} // class RevistaDAO
