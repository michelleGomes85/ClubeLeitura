package main.java.tsi.daw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.tsi.daw.bd.ConnectionFactory;
import main.java.tsi.daw.bd.DataBaseSchema;
import main.java.tsi.daw.model.Pessoa;

/**
 * Classe de acesso a dados (DAO) para manipulação de objetos {@link Pessoa} no
 * banco de dados.
 * 
 * A classe fornece métodos para realizar operações CRUD (Create, Read, Update,
 * Delete) na tabela de pessoas do banco de dados
 */
public class PessoaDAO {
	
	private static final String ERROR_INSERT = "Erro ao inserir uma pessoa no banco de dados";
    private static final String ERROR_UPDATE = "Erro ao atualizar uma pessoa no banco de dados";
    private static final String ERROR_DELETE = "Erro ao deletar uma pessoa no banco de dados";
    private static final String ERROR_LIST = "Erro ao listar as pessoas do banco de dados";
    private static final String ERROR_FIND_BY_ID = "Erro ao buscar uma pessoa pelo ID no banco de dados";
    
    private Connection connection;
    
    public PessoaDAO() {
        this.connection = ConnectionFactory.getConnection();
    }
    
    /**
     * Insere uma nova pessoa no banco de dados.
     *
     * @param pessoa Objeto Pessoa a ser inserido no banco de dados
     */
    public void insert(Pessoa pessoa) {
    	
        String sql = 
        		String.format("INSERT INTO %s (%s, %s) VALUES (?, ?)", 
        		DataBaseSchema.PESSOA.getTableName(),
        		DataBaseSchema.PESSOA.getColumns()[1],
        		DataBaseSchema.PESSOA.getColumns()[2]);
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, pessoa.getNome());
            preparedStatement.setString(2, pessoa.getTelefone());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(ERROR_INSERT);
        }
    }
    
    /**
     * Atualiza uma pessoa existente no banco de dados.
     *
     * @param pessoa Objeto Pessoa com os dados atualizados
     */
    public void update(Pessoa pessoa) {
    	
        String sql = 
        		String.format("UPDATE %s SET %s = ?, %s = ? WHERE %s = ?", 
				DataBaseSchema.PESSOA.getTableName(),
        		DataBaseSchema.PESSOA.getColumns()[1],
        		DataBaseSchema.PESSOA.getColumns()[2],
        		DataBaseSchema.PESSOA.getColumns()[0]);
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, pessoa.getNome());
            preparedStatement.setString(2, pessoa.getTelefone());
            preparedStatement.setLong(3, pessoa.getIdPessoa());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(ERROR_UPDATE);
        }
    }
    
    /**
     * Deleta uma Pessoa do banco de dados.
     *
     * @param pessoa Objeto Pessoa a ser deletado
     */
    public void delete(Pessoa pessoa) {
    	
        String sql = 
        		String.format("DELETE FROM %s WHERE %s = ?", 
        		DataBaseSchema.PESSOA.getTableName(),
        		DataBaseSchema.PESSOA.getColumns()[0]);
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, pessoa.getIdPessoa());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(ERROR_DELETE);
        }
    }
    
    /**
	 * Metódo auxiliar que transforma um resultSet de consulta em um objeto Pessoa
	 * 
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	private Pessoa mapResultSetToPessoa(ResultSet resultSet) throws SQLException {
		Pessoa pessoa = new Pessoa();
        pessoa.setIdPessoa(resultSet.getLong(DataBaseSchema.PESSOA.getColumns()[0]));
        pessoa.setNome(resultSet.getString(DataBaseSchema.PESSOA.getColumns()[1]));
        pessoa.setTelefone(resultSet.getString(DataBaseSchema.PESSOA.getColumns()[2]));
        
        return pessoa;
	}
	
    /**
     * Busca uma pessoa pelo seu ID.
     *
     * @param id ID da Pessoa a ser encontrada
     * @return Objeto Pessoa correspondente ao ID, ou null se não encontrado
     */
    public Pessoa findById(long id) {
    	
        String sql = 
        		String.format("SELECT * FROM %s WHERE %s = ?", 
        		DataBaseSchema.PESSOA.getTableName());
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        	
            preparedStatement.setLong(1, id);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) 
            	return mapResultSetToPessoa(resultSet);
            
        } catch (SQLException e) {
            throw new RuntimeException(ERROR_FIND_BY_ID);
        }
        
        return null; 
    }
    
    /**
     * Método auxiliar para executar consultas SQL e retornar uma lista de objetos Pessoa.
     *
     * @param sql A consulta SQL a ser executada.
     * @return Lista de objetos Pessoa correspondente à consulta SQL.
     */
    private List<Pessoa> listAux(String sql) {
    	
    	List<Pessoa> listPessoas = new ArrayList<>();
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        	
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next())
                listPessoas.add(mapResultSetToPessoa(resultSet));
            
        } catch (SQLException e) {
            throw new RuntimeException(ERROR_LIST);
        }
        
        return listPessoas;
    }
    
    /**
     * Lista todas as pessoas cadastradas no banco de dados.
     *
     * @return Lista de objetos Pessoa
     */
    public List<Pessoa> list() {
    	return listAux(
    			String.format("SELECT * FROM %s", 
    		    DataBaseSchema.PESSOA.getTableName()));
    }
    
    /**
     * Lista todas as pessoas que não possuem empréstimos pendentes.
     * Inclui pessoas que não estão na tabela de empréstimos e pessoas cujo
     * campo de data_devolucao está preenchido (empréstimos concluídos).
     *
     * @return Lista de objetos Pessoa sem empréstimos pendentes.
     */
    public List<Pessoa> listWithoutLoans() {
        
    	String sql = String.format(
            """
            SELECT * FROM %s  
            WHERE NOT EXISTS (
                SELECT 1 FROM %s
                WHERE %s.%s = %s.%s AND %s.%s IS NULL
            )
            """,
            DataBaseSchema.PESSOA.getTableName(), 
            DataBaseSchema.EMPRESTIMO.getTableName(),
            DataBaseSchema.EMPRESTIMO.getTableName(), DataBaseSchema.EMPRESTIMO.getColumns()[1],
            DataBaseSchema.PESSOA.getTableName(), DataBaseSchema.PESSOA.getColumns()[0],
            DataBaseSchema.EMPRESTIMO.getTableName(), DataBaseSchema.EMPRESTIMO.getColumns()[4]
        );

        return listAux(sql);
    }

} // class PessoaDAO
