package main.java.tsi.daw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.tsi.daw.bd.ConnectionFactory;
import main.java.tsi.daw.bd.DataBaseSchema;
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
    private static final String ERROR_INVALID_USER = "Erro usuário invalido";
    
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
    	
        String sql = 
        		String.format("INSERT INTO %s (%s, %s) VALUES (?, ?)", 
        		DataBaseSchema.USUARIO.getTableName(), 
        		DataBaseSchema.USUARIO.getColumns()[1],
        		DataBaseSchema.USUARIO.getColumns()[2]);
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, usuario.getUsuario());
            preparedStatement.setString(2, usuario.getSenha());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(ERROR_INSERT);
        }
    }
    
    /**
     * Atualiza um usuario existente no banco de dados.
     *
     * @param usuario Objeto Usuario com os dados atualizados
     */
    public void update(Usuario usuario) {
    	
        String sql = 
        		String.format("UPDATE %s SET %s = ?, SET %s = ? WHERE %s = ?", 
				DataBaseSchema.USUARIO.getTableName(), 
        		DataBaseSchema.USUARIO.getColumns()[1],
        		DataBaseSchema.USUARIO.getColumns()[2],
        		DataBaseSchema.USUARIO.getColumns()[0]);
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, usuario.getUsuario());
            preparedStatement.setString(2, usuario.getSenha());
            preparedStatement.setLong(3, usuario.getIdUsuario());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(ERROR_UPDATE);
        }
    }
    
    /**
     * Deleta um usuario do banco de dados.
     *
     * @param usuario Objeto Usuario a ser deletado
     */
    public void delete(Usuario usuario) {
    	
        String sql = 
        		String.format("DELETE FROM %s WHERE %s = ?", 
				DataBaseSchema.USUARIO.getTableName(), 
        		DataBaseSchema.USUARIO.getColumns()[0]);
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, usuario.getIdUsuario());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(ERROR_DELETE);
        }
    }
    
    /**
	 * Metódo auxiliar que transforma um resultSet de consulta em um objeto Usuario
	 * 
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	private Usuario mapResultSetToUsuario(ResultSet resultSet) throws SQLException {
		Usuario usuario = new Usuario();
        usuario.setIdUsuario(resultSet.getLong(DataBaseSchema.USUARIO.getColumns()[0]));
        usuario.setUsuario(resultSet.getString(DataBaseSchema.USUARIO.getColumns()[1]));
        usuario.setSenha(resultSet.getString(DataBaseSchema.USUARIO.getColumns()[2]));
        return usuario;
	}
    
    /**
     * Busca um usuario pelo seu ID.
     *
     * @param id ID do Usuario a ser encontrado
     * @return Objeto Usuario correspondente ao ID, ou null se não encontrado
     */
    public Usuario findById(long id) {
    	
        String sql = 
        		String.format("SELECT * FROM %s WHERE %s = ?", 
        		DataBaseSchema.USUARIO.getTableName(), 
        		DataBaseSchema.USUARIO.getColumns()[0]);
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        	
            preparedStatement.setLong(1, id);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next())
            	return mapResultSetToUsuario(resultSet);
            
        } catch (SQLException e) {
            throw new RuntimeException(ERROR_FIND_BY_ID);
        }
        
        return null; 
    }
    
    /**
     * Lista todas os usuarios cadastradas no banco de dados.
     *
     * @return Lista de objetos Usuarios
     */
    public List<Usuario> list() {
    	
        List<Usuario> listUsuarios = new ArrayList<>();
        
        String sql = 
        		String.format("SELECT * FROM %s", 
        		DataBaseSchema.USUARIO.getTableName());
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
        	ResultSet resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next())
                listUsuarios.add(mapResultSetToUsuario(resultSet));
            
        } catch (SQLException e) {
            throw new RuntimeException(ERROR_LIST);
        }
        
        return listUsuarios;
    }

	/**
	 * Valida as credencias de login de um usuário no banco de dados.
	 * 
	 * @param login o nome de usuário informado pelo cliente
	 * @param senha a senha associada ao nome de usuário
	 * @return um objeto {@link Usuario} contendo os dados do usuário autenticado,
	 *         ou {@code null} se as credenciais forem inválidas
	 *         
	 * @throws RuntimeException se ocorrer um erro ao acessar o banco de dados
	 */
	public Usuario validateCredential(String login, String senha) {
		
		String sql = 
				String.format("SELECT * FROM %s WHERE usuario = ? and senha = ?", 
				DataBaseSchema.USUARIO.getTableName());
		
		Usuario usuario = null;
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setString(1, login);
			preparedStatement.setString(2, senha);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				usuario = new Usuario();
				usuario.setUsuario(resultSet.getString(DataBaseSchema.USUARIO.getColumns()[1]));
				usuario.setSenha(resultSet.getString(DataBaseSchema.USUARIO.getColumns()[2]));
			}
		} catch (SQLException e) {
			throw new RuntimeException(ERROR_INVALID_USER);
		}

		return usuario;
	}
}