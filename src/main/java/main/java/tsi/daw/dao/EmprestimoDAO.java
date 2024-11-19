package main.java.tsi.daw.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.tsi.daw.bd.ConnectionFactory;
import main.java.tsi.daw.model.Emprestimo;
import main.java.tsi.daw.model.Pessoa;
import main.java.tsi.daw.model.Revista;

/**
 * Classe de acesso a dados (DAO) para manipulação de objetos {@link Emprestimo} no
 * banco de dados.
 * 
 * A classe fornece métodos para realizar operações CRUD (Create, Read, Update,
 * Delete) na tabela de empréstimos do banco de dados.
 */
public class EmprestimoDAO {
    
    private static final String ERROR_INSERT = "Erro ao inserir empréstimo no banco de dados";
    private static final String ERROR_UPDATE = "Erro ao atualizar empréstimo no banco de dados";
    private static final String ERROR_DELETE = "Erro ao deletar empréstimo no banco de dados";
    private static final String ERROR_LIST = "Erro ao listar empréstimos do banco de dados";
    private static final String ERROR_FIND_BY_ID = "Erro ao buscar empréstimo pelo ID no banco de dados";
    
    private static final String TABLE_NAME = "emprestimo";
    private static final String TABLE_NAME_REVISTA = "revista";
    private static final String COLUMN_ID = "idemprestimo";
    private static final String COLUMN_PESSOA_ID = "idpessoa";
    private static final String COLUMN_REVISTA_ID = "idrevista";
    private static final String COLUMN_DATA_EMPRESTIMO = "dataemprestimo";
    private static final String COLUMN_DATA_DEVOLUCAO = "datadevolucao";
    
    private Connection connection;
    
    public EmprestimoDAO() {
        this.connection = ConnectionFactory.getConnection();
    }
    
    /**
     * Insere um novo empréstimo no banco de dados.
     *
     * @param emprestimo Objeto Emprestimo a ser inserido no banco de dados
     */
    public void insert(Emprestimo emprestimo) {
        
        String sql = String.format("INSERT INTO %s (%s, %s, %s, %s) VALUES (?, ?, ?, ?)", 
                                   TABLE_NAME, COLUMN_PESSOA_ID, COLUMN_REVISTA_ID, 
                                   COLUMN_DATA_EMPRESTIMO, COLUMN_DATA_DEVOLUCAO);
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, emprestimo.getPessoa().getIdPessoa());
            preparedStatement.setLong(2, emprestimo.getRevista().getIdRevista());
            preparedStatement.setDate(3, Date.valueOf(emprestimo.getDataEmprestimo()));
            
            if (emprestimo.getDataDevolucao() != null) {
                preparedStatement.setDate(4, Date.valueOf(emprestimo.getDataDevolucao()));
            } else {
                preparedStatement.setNull(4, java.sql.Types.DATE);
            }
            
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(ERROR_INSERT, e);
        }
    }

    
    /**
     * Atualiza um empréstimo existente no banco de dados.
     *
     * @param emprestimo Objeto Emprestimo com os dados atualizados
     */
    public void update(Emprestimo emprestimo) {
    	
        String sql = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?", 
                                   TABLE_NAME, COLUMN_PESSOA_ID, COLUMN_REVISTA_ID, 
                                   COLUMN_DATA_EMPRESTIMO, COLUMN_DATA_DEVOLUCAO, COLUMN_ID);
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, emprestimo.getPessoa().getIdPessoa());
            preparedStatement.setLong(2, emprestimo.getRevista().getIdRevista());
            preparedStatement.setDate(3, Date.valueOf(emprestimo.getDataEmprestimo()));
            preparedStatement.setDate(4, Date.valueOf(emprestimo.getDataDevolucao()));
            preparedStatement.setLong(5, emprestimo.getIdEmprestimo());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(ERROR_UPDATE, e);
        }
    }
    
    /**
     * Deleta um empréstimo do banco de dados.
     *
     * @param emprestimo Objeto Emprestimo a ser deletado
     */
    public void delete(Emprestimo emprestimo) {
    	
        String sql = String.format("DELETE FROM %s WHERE %s = ?", TABLE_NAME, COLUMN_ID);
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, emprestimo.getIdEmprestimo());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(ERROR_DELETE, e);
        }
    }
    
    /**
     * Lista todos os empréstimos cadastrados no banco de dados.
     *
     * @return Lista de objetos Emprestimo
     */
    public List<Emprestimo> listEmprestimo(String sql) {
    	
        List<Emprestimo> listEmprestimos = new ArrayList<>();
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                Emprestimo emprestimo = new Emprestimo();
                emprestimo.setIdEmprestimo(resultSet.getLong(COLUMN_ID));
                
                Pessoa pessoa = new Pessoa();
                pessoa.setIdPessoa(resultSet.getLong(COLUMN_PESSOA_ID));
                
                emprestimo.setPessoa(pessoa);
                
                Revista revista = new Revista();
                revista.setIdRevista(resultSet.getLong(COLUMN_REVISTA_ID));
                
                emprestimo.setRevista(revista);
                emprestimo.setDataEmprestimo(resultSet.getDate(COLUMN_DATA_EMPRESTIMO).toLocalDate());
                emprestimo.setDataDevolucao(resultSet.getDate(COLUMN_DATA_DEVOLUCAO).toLocalDate());
                listEmprestimos.add(emprestimo);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException(ERROR_LIST, e);
        }
        
        return listEmprestimos;
    }
    
    public List<Emprestimo> list() {
    	return listEmprestimo(String.format("SELECT * FROM %s", TABLE_NAME));
    }
    
    public Map<Long, Revista> listRevistaEmprestimo() {
    	
    	String sql = String.format("SELECT e.idemprestimo, "
                + "r.idrevista, r.colecao, r.num_edicao, r.ano_revista, r.disponibilidade "
                + "FROM %s e "
                + "INNER JOIN %s r ON e.idRevista = r.idRevista "
                + "WHERE r.disponibilidade = false AND e.datadevolucao IS NULL", TABLE_NAME, TABLE_NAME_REVISTA);
    	
    	 Map<Long, Revista> emprestimoRevistaMap = new HashMap<>();
    	 
    	 try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
    		 
    		 ResultSet resultSet = preparedStatement.executeQuery();
    		 
    		 while (resultSet.next()) {
    			 
    			Long idEmprestimo = resultSet.getLong("idemprestimo");
    			
	            Revista revista = new Revista();
	            revista.setIdRevista(resultSet.getLong("idrevista"));
	            revista.setColecao(resultSet.getString("colecao"));
	            revista.setNumeroEdicao(resultSet.getInt("num_edicao"));
	            revista.setAnoRevista(resultSet.getInt("ano_revista"));
	            revista.setDisponibilidade(resultSet.getBoolean("disponibilidade"));
	            
	            emprestimoRevistaMap.put(idEmprestimo, revista);
    		 }
    		 
    	 } catch (SQLException e) {
             throw new RuntimeException(ERROR_LIST, e);
         }
    	 
    	 return emprestimoRevistaMap;
    }
    
    public Map<Long, Revista> listRevistaEmprestimoAtrasado() {
    	
    	String sql = String.format("SELECT e.idemprestimo, "
    	        + "r.idrevista, r.colecao, r.num_edicao, r.ano_revista, r.disponibilidade "
    	        + "FROM %s e "
    	        + "INNER JOIN %s r ON e.idRevista = r.idRevista "
    	        + "WHERE r.disponibilidade = false "
    	        + "AND e.datadevolucao IS NULL "
    	        + "AND e.dataemprestimo + INTERVAL '10 days' < CURRENT_DATE", TABLE_NAME, TABLE_NAME_REVISTA);
    	
    	 Map<Long, Revista> emprestimoRevistaMap = new HashMap<>();
    	 
    	 try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
    		 
    		 ResultSet resultSet = preparedStatement.executeQuery();
    		 
    		 while (resultSet.next()) {
    			 
    			Long idEmprestimo = resultSet.getLong("idemprestimo");
    			
	            Revista revista = new Revista();
	            revista.setIdRevista(resultSet.getLong("idrevista"));
	            revista.setColecao(resultSet.getString("colecao"));
	            revista.setNumeroEdicao(resultSet.getInt("num_edicao"));
	            revista.setAnoRevista(resultSet.getInt("ano_revista"));
	            revista.setDisponibilidade(resultSet.getBoolean("disponibilidade"));
	            
	            emprestimoRevistaMap.put(idEmprestimo, revista);
    		 }
    		 
    	 } catch (SQLException e) {
             throw new RuntimeException(ERROR_LIST, e);
         }
    	 
    	 return emprestimoRevistaMap;
    }

    /**
     * Busca um empréstimo pelo seu ID.
     *
     * @param id ID do Emprestimo a ser encontrado
     * @return Objeto Emprestimo correspondente ao ID, ou null se não encontrado
     */
    public Emprestimo findById(long id) {
    	
        String sql = String.format("SELECT * FROM %s WHERE %s = ?", TABLE_NAME, COLUMN_ID);
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
        	preparedStatement.setLong(1, id);
        	
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
               
            	Emprestimo emprestimo = new Emprestimo();
                emprestimo.setIdEmprestimo(resultSet.getLong(COLUMN_ID));
                
                Pessoa pessoa = new Pessoa();
                pessoa.setIdPessoa(resultSet.getLong(COLUMN_PESSOA_ID));
                
                emprestimo.setPessoa(pessoa);
                
                Revista revista = new Revista();
                revista.setIdRevista(resultSet.getLong(COLUMN_REVISTA_ID));
                
                emprestimo.setRevista(revista);
                emprestimo.setDataEmprestimo(resultSet.getDate(COLUMN_DATA_EMPRESTIMO).toLocalDate());
                
                Date dateReturn = resultSet.getDate(COLUMN_DATA_DEVOLUCAO);
                
                if (dateReturn != null)
                	emprestimo.setDataDevolucao(dateReturn.toLocalDate());
            	
                return emprestimo;
            }
            
        } catch (SQLException e) {
            throw new RuntimeException(ERROR_FIND_BY_ID, e);
        }
        
        return null; 
    }
} // class EmprestimoDAO