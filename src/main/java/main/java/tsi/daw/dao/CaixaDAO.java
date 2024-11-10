package main.java.tsi.daw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.tsi.daw.bd.ConnectionFactory;
import main.java.tsi.daw.model.Caixa;

/**
 * Classe de acesso a dados (DAO) para manipulação de objetos {@link Caixa} no
 * banco de dados.
 * 
 * A classe fornece métodos para realizar operações CRUD (Create, Read, Update,
 * Delete) na tabela de caixas do banco de dados
 */
public class CaixaDAO {
    
    private static final String ERROR_INSERT = "Erro ao inserir caixa no banco de dados";
    private static final String ERROR_UPDATE = "Erro ao atualizar caixa no banco de dados";
    private static final String ERROR_DELETE = "Erro ao deletar caixa no banco de dados";
    private static final String ERROR_LIST = "Erro ao listar caixas do banco de dados";
    private static final String ERROR_FIND_BY_ID = "Erro ao buscar caixa pelo ID no banco de dados";
    
    private static final String TABLE_NAME = "caixa";
    private static final String COLUMN_ID = "idcaixa";
    private static final String COLUMN_COR = "cor";
    
    private Connection connection;
    
    public CaixaDAO() {
        this.connection = ConnectionFactory.getConnection();
    }
    
    /**
     * Insere uma nova caixa no banco de dados.
     *
     * @param caixa Objeto Caixa a ser inserido no banco de dados
     */
    public void insert(Caixa caixa) {
    	
        String sql = String.format("INSERT INTO %s (%s) VALUES (?)", TABLE_NAME, COLUMN_COR);
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, caixa.getCor());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(ERROR_INSERT, e);
        }
    }
    
    /**
     * Atualiza uma caixa existente no banco de dados.
     *
     * @param caixa Objeto Caixa com os dados atualizados
     */
    public void update(Caixa caixa) {
    	
        String sql = String.format("UPDATE %s SET %s = ? WHERE %s = ?", TABLE_NAME, COLUMN_COR, COLUMN_ID);
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, caixa.getCor());
            preparedStatement.setLong(2, caixa.getIdCaixa());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(ERROR_UPDATE, e);
        }
    }
    
    /**
     * Deleta uma caixa do banco de dados.
     *
     * @param caixa Objeto Caixa a ser deletado
     */
    public void delete(Caixa caixa) {
    	
        String sql = String.format("DELETE FROM %s WHERE %s = ?", TABLE_NAME, COLUMN_ID);
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, caixa.getIdCaixa());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(ERROR_DELETE, e);
        }
    }
    
    /**
     * Lista todas as caixas cadastradas no banco de dados.
     *
     * @return Lista de objetos Caixa
     */
    public List<Caixa> list() {
    	
        List<Caixa> listCaixas = new ArrayList<>();
        
        String sql = String.format("SELECT * FROM %s", TABLE_NAME);
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                Caixa caixa = new Caixa();
                caixa.setIdCaixa(resultSet.getLong(COLUMN_ID));
                caixa.setCor(resultSet.getString(COLUMN_COR));
                listCaixas.add(caixa);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException(ERROR_LIST, e);
        }
        
        return listCaixas;
    }

    /**
     * Busca uma caixa pelo seu ID.
     *
     * @param id ID da Caixa a ser encontrada
     * @return Objeto Caixa correspondente ao ID, ou null se não encontrado
     */
    public Caixa findById(long id) {
    	
        String sql = String.format("SELECT * FROM %s WHERE %s = ?", TABLE_NAME, COLUMN_ID);
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        	
            preparedStatement.setLong(1, id);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
                Caixa caixa = new Caixa();
                caixa.setIdCaixa(resultSet.getLong(COLUMN_ID));
                caixa.setCor(resultSet.getString(COLUMN_COR));
                return caixa;
            }
            
        } catch (SQLException e) {
            throw new RuntimeException(ERROR_FIND_BY_ID, e);
        }
        
        return null; 
    }
} // class CaixaDAO