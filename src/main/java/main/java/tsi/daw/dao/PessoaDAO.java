package main.java.tsi.daw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.tsi.daw.bd.ConnectionFactory;
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
    
    private static final String TABLE_NAME = "pessoa";
    private static final String COLUMN_ID = "idpessoa";
    private static final String COLUMN_NOME = "nome";
    private static final String COLUMN_TELEFONE = "telefone";
    
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
    	
        String sql = String.format("INSERT INTO %s (%s, %s) VALUES (?, ?)", TABLE_NAME, COLUMN_NOME, COLUMN_TELEFONE);
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, pessoa.getNome());
            preparedStatement.setString(2, pessoa.getTelefone());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(ERROR_INSERT, e);
        }
    }
    
    /**
     * Atualiza uma pessoa existente no banco de dados.
     *
     * @param pessoa Objeto Pessoa com os dados atualizados
     */
    public void update(Pessoa pessoa) {
    	
        String sql = String.format("UPDATE %s SET %s = ?, %s = ? WHERE %s = ?", TABLE_NAME, COLUMN_NOME, COLUMN_TELEFONE, COLUMN_ID);
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, pessoa.getNome());
            preparedStatement.setString(2, pessoa.getTelefone());
            preparedStatement.setLong(3, pessoa.getIdPessoa());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(ERROR_UPDATE, e);
        }
    }
    
    /**
     * Deleta uma Pessoa do banco de dados.
     *
     * @param pessoa Objeto Pessoa a ser deletado
     */
    public void delete(Pessoa pessoa) {
    	
        String sql = String.format("DELETE FROM %s WHERE %s = ?", TABLE_NAME, COLUMN_ID);
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, pessoa.getIdPessoa());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(ERROR_DELETE, e);
        }
    }
    
    /**
     * Lista todas as pessoas cadastradas no banco de dados.
     *
     * @return Lista de objetos Pessoa
     */
    public List<Pessoa> list() {
    	
        List<Pessoa> listPessoas = new ArrayList<>();
        
        String sql = String.format("SELECT * FROM %s", TABLE_NAME);
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        	
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                Pessoa pessoa = new Pessoa();
                pessoa.setIdPessoa(resultSet.getLong(COLUMN_ID));
                pessoa.setNome(resultSet.getString(COLUMN_NOME));
                pessoa.setTelefone(resultSet.getString(COLUMN_TELEFONE));
                listPessoas.add(pessoa);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException(ERROR_LIST, e);
        }
        
        return listPessoas;
    }

    /**
     * Busca uma pessoa pelo seu ID.
     *
     * @param id ID da Pessoa a ser encontrada
     * @return Objeto Pessoa correspondente ao ID, ou null se não encontrado
     */
    public Pessoa findById(long id) {
    	
        String sql = String.format("SELECT * FROM %s WHERE %s = ?", TABLE_NAME, COLUMN_ID);
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        	
            preparedStatement.setLong(1, id);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
            	Pessoa pessoa = new Pessoa();
                pessoa.setIdPessoa(resultSet.getLong(COLUMN_ID));
                pessoa.setNome(resultSet.getString(COLUMN_NOME));
                pessoa.setTelefone(resultSet.getString(COLUMN_TELEFONE));
                
                return pessoa;
            }
            
        } catch (SQLException e) {
            throw new RuntimeException(ERROR_FIND_BY_ID, e);
        }
        
        return null; 
    }
} // class PessoaDAO
