package main.java.tsi.daw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.tsi.daw.bd.ConnectionFactory;
import main.java.tsi.daw.model.Usuario;

/**
 * Classe de acesso a dados (DAO) para manipulação de objetos {@link Usuario} no
 * banco de dados.
 * 
 * A classe fornece métodos para realizar operações CRUD (Create, Read, Update,
 * Delete) na tabela de usuarios do banco de dados
 */
public class UsuarioDAO {
	private static final String ERROR_INSERT = "Erro ao inserir um usuário no banco de dados";
    private static final String ERROR_UPDATE = "Erro ao atualizar um usuário no banco de dados";
    private static final String ERROR_DELETE = "Erro ao deletar um usuário no banco de dados";
    private static final String ERROR_LIST = "Erro ao listar usuários do banco de dados";
    private static final String ERROR_FIND_BY_ID = "Erro ao buscar um usuário pelo ID no banco de dados";
    
    private static final String TABLE_NAME = "usuario";
    private static final String COLUMN_ID = "idusuario";
    private static final String COLUMN_USUARIO = "usuario";
    private static final String COLUMN_SENHA = "senha";
    
    private Connection connection;
    
    public UsuarioDAO() {
        this.connection = ConnectionFactory.getConnection();
    }
    
    /**
     * Insere um novo usuario no banco de dados.
     *
     * @param usuario Objeto Usuario a ser inserido no banco de dados
     */
    public void insert(Usuario usuario) {
    	
        String sql = String.format("INSERT INTO %s (%s, %s) VALUES (?, ?)", TABLE_NAME, COLUMN_USUARIO, COLUMN_SENHA);
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, usuario.getUsuario());
            preparedStatement.setString(2, usuario.getSenha());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(ERROR_INSERT, e);
        }
    }
    
    /**
     * Atualiza um usuario existente no banco de dados.
     *
     * @param usuario Objeto Usuario com os dados atualizados
     */
    public void update(Usuario usuario) {
    	
        String sql = String.format("UPDATE %s SET %s = ?, SET %s = ? WHERE %s = ?", TABLE_NAME, COLUMN_USUARIO, COLUMN_SENHA, COLUMN_ID);
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, usuario.getUsuario());
            preparedStatement.setString(2, usuario.getSenha());
            preparedStatement.setLong(3, usuario.getIdUsuario());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(ERROR_UPDATE, e);
        }
    }
    
    /**
     * Deleta um usuario do banco de dados.
     *
     * @param usuario Objeto Usuario a ser deletado
     */
    public void delete(Usuario usuario) {
    	
        String sql = String.format("DELETE FROM %s WHERE %s = ?", TABLE_NAME, COLUMN_ID);
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, usuario.getIdUsuario());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(ERROR_DELETE, e);
        }
    }
    
    /**
     * Lista todas os usuarios cadastradas no banco de dados.
     *
     * @return Lista de objetos Usuarios
     */
    public List<Usuario> list() {
    	
        List<Usuario> listUsuarios = new ArrayList<>();
        
        String sql = String.format("SELECT * FROM %s", TABLE_NAME);
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(resultSet.getLong(COLUMN_ID));
                usuario.setUsuario(resultSet.getString(COLUMN_USUARIO));
                usuario.setSenha(resultSet.getString(COLUMN_SENHA));
                
                listUsuarios.add(usuario);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException(ERROR_LIST, e);
        }
        
        return listUsuarios;
    }

    /**
     * Busca um usuario pelo seu ID.
     *
     * @param id ID do Usuario a ser encontrado
     * @return Objeto Usuario correspondente ao ID, ou null se não encontrado
     */
    public Usuario findById(long id) {
    	
        String sql = String.format("SELECT * FROM %s WHERE %s = ?", TABLE_NAME, COLUMN_ID);
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        	
            preparedStatement.setLong(1, id);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
            	Usuario usuario = new Usuario();
                usuario.setIdUsuario(resultSet.getLong(COLUMN_ID));
                usuario.setUsuario(resultSet.getString(COLUMN_USUARIO));
                usuario.setSenha(resultSet.getString(COLUMN_SENHA));
                return usuario;
            }
            
        } catch (SQLException e) {
            throw new RuntimeException(ERROR_FIND_BY_ID, e);
        }
        
        return null; 
    }
}
