package main.java.tsi.daw.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.tsi.daw.bd.ConnectionFactory;
import main.java.tsi.daw.bd.DataBaseSchema;
import main.java.tsi.daw.model.Caixa;
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
    private static final String ERROR_LIST_ACTIVE = "Erro ao listar empréstimos ativos do banco de dados";
    private static final String ERROR_LIST_LATE = "Erro ao listar empréstimos atrasados do banco de dados";
    private static final String ERROR_LIST_PERSON = "Erro ao listar empréstimos por pessoa no banco de dados";
    private static final String ERROR_FIND_BY_ID = "Erro ao buscar empréstimo pelo ID no banco de dados";
    
    private static final Integer NUM_DAYS_DELAY = 10;
    
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
        
        String sql = 
        		String.format("INSERT INTO %s (%s, %s, %s, %s) VALUES (?, ?, ?, ?)",
        		DataBaseSchema.EMPRESTIMO.getTableName(),
        		DataBaseSchema.EMPRESTIMO.getColumns()[1],
        		DataBaseSchema.EMPRESTIMO.getColumns()[2],
        		DataBaseSchema.EMPRESTIMO.getColumns()[3],
        		DataBaseSchema.EMPRESTIMO.getColumns()[4]);
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
        	preparedStatement.setLong(1, emprestimo.getPessoa().getIdPessoa());
            preparedStatement.setLong(2, emprestimo.getRevista().getIdRevista());
            preparedStatement.setDate(3, new Date(emprestimo.getDataEmprestimo().getTime()));
            
            if (emprestimo.getDataDevolucao() != null)
            	preparedStatement.setDate(4, new Date(emprestimo.getDataDevolucao().getTime()));
            else
                preparedStatement.setNull(4, java.sql.Types.DATE);
            
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(ERROR_INSERT);
        }
    }

    
    /**
     * Atualiza um empréstimo existente no banco de dados.
     *
     * @param emprestimo Objeto Emprestimo com os dados atualizados
     */
    public void update(Emprestimo emprestimo) {
    	
        String sql = 
        		String.format("UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?", 
				DataBaseSchema.EMPRESTIMO.getTableName(),
        		DataBaseSchema.EMPRESTIMO.getColumns()[1],
        		DataBaseSchema.EMPRESTIMO.getColumns()[2],
        		DataBaseSchema.EMPRESTIMO.getColumns()[3],
        		DataBaseSchema.EMPRESTIMO.getColumns()[4],
        		DataBaseSchema.EMPRESTIMO.getColumns()[0]);
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, emprestimo.getPessoa().getIdPessoa());
            preparedStatement.setLong(2, emprestimo.getRevista().getIdRevista());
            preparedStatement.setDate(3, new Date(emprestimo.getDataEmprestimo().getTime()));
            preparedStatement.setDate(4, new Date(emprestimo.getDataDevolucao().getTime()));
            preparedStatement.setLong(5, emprestimo.getIdEmprestimo());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(ERROR_UPDATE);
        }
    }
    
    /**
     * Deleta um empréstimo do banco de dados.
     *
     * @param emprestimo Objeto Emprestimo a ser deletado
     */
    public void delete(Emprestimo emprestimo) {
    	
        String sql = 
        		String.format("DELETE FROM %s WHERE %s = ?", 
        		DataBaseSchema.EMPRESTIMO.getTableName(),
        		DataBaseSchema.EMPRESTIMO.getColumns()[0]);
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, emprestimo.getIdEmprestimo());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(ERROR_DELETE);
        }
    }
    
    /**
     * Busca um empréstimo pelo seu ID.
     *
     * @param id ID do Emprestimo a ser encontrado
     * @return Objeto Emprestimo correspondente ao ID, ou null se não encontrado
     */
    public Emprestimo findById(long id) {
    	
        String sql = 
        		String.format("SELECT * FROM %s WHERE %s = ?", 
        		DataBaseSchema.EMPRESTIMO.getTableName(), 
        		DataBaseSchema.EMPRESTIMO.getColumns()[0]);
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
        	preparedStatement.setLong(1, id);
        	
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
               
            	Emprestimo emprestimo = new Emprestimo();
                emprestimo.setIdEmprestimo(resultSet.getLong(DataBaseSchema.EMPRESTIMO.getColumns()[0]));
                
                Pessoa pessoa = new Pessoa();
                pessoa.setIdPessoa(resultSet.getLong(DataBaseSchema.EMPRESTIMO.getColumns()[1]));
                
                emprestimo.setPessoa(pessoa);
                
                Revista revista = new Revista();
                revista.setIdRevista(resultSet.getLong(DataBaseSchema.EMPRESTIMO.getColumns()[2]));
                
                emprestimo.setRevista(revista);
                emprestimo.setDataEmprestimo(resultSet.getDate(DataBaseSchema.EMPRESTIMO.getColumns()[3]));
                
                Date dateReturn = resultSet.getDate(DataBaseSchema.EMPRESTIMO.getColumns()[4]);
                
                if (dateReturn != null)
                	emprestimo.setDataDevolucao(dateReturn);
            	
                return emprestimo;
            }
            
        } catch (SQLException e) {
            throw new RuntimeException(ERROR_FIND_BY_ID);
        }
        
        return null; 
    }
    
    /**
	 * Metódo auxiliar que transforma um resultSet de consulta em um objeto Emprestimo
	 * 
	 * @param resultSet O resultado de uma consulta SQL contendo os dados do empréstimo.
	 * @return Um objeto Emprestimo preenchido com os dados do ResultSet.
	 * 
	 * @throws SQLException Se ocorrer algum erro ao acessar os dados do ResultSet
	 */
	private Emprestimo mapResultSetToEmprestimo(ResultSet resultSet) throws SQLException {
		
		Emprestimo emprestimo = new Emprestimo();
		
		emprestimo.setIdEmprestimo(resultSet.getLong(DataBaseSchema.EMPRESTIMO.getColumns()[0]));
		emprestimo.setDataEmprestimo(resultSet.getDate(DataBaseSchema.EMPRESTIMO.getColumns()[3]));
		emprestimo.setDataDevolucao(resultSet.getDate(DataBaseSchema.EMPRESTIMO.getColumns()[4]));
		
		Pessoa pessoa = new Pessoa();
		pessoa.setNome(resultSet.getString(DataBaseSchema.PESSOA.getColumns()[1]));
		
		Revista revista = new Revista();
		
		revista.setIdRevista(resultSet.getLong(DataBaseSchema.REVISTA.getColumns()[0]));
		revista.setColecao(resultSet.getString(DataBaseSchema.REVISTA.getColumns()[1]));
		revista.setNumeroEdicao(resultSet.getInt(DataBaseSchema.REVISTA.getColumns()[2]));
		revista.setAnoRevista(resultSet.getInt(DataBaseSchema.REVISTA.getColumns()[3]));
		
		Caixa caixa = new Caixa();
		caixa.setCor(resultSet.getString(DataBaseSchema.CAIXA.getColumns()[1]));
		revista.setCaixa(caixa);
		
		emprestimo.setRevista(revista);
		emprestimo.setPessoa(pessoa);

		return emprestimo;
	}
    
	/**
	 * Este método consulta a base de dados para buscar empréstimos com base em uma
	 * cláusula WHERE opcional e parâmetros adicionais.
	 * 
	 * @param whereClause Condição para a consulta SQL (opcional). Pode incluir
	 *                    filtros como id, status do empréstimo, etc.
	 * @param params      Valores associados aos placeholders na cláusula WHERE.
	 * @return Uma lista de objetos Emprestimo representando os dados encontrados.
	 * 
	 * @throws SQLException -> Se ocorrer algum erro na consulta
	 */
    private List<Emprestimo> getEmprestimos(String whereClause, Object... params) throws SQLException {
    	
        String sql = String.format("""
                SELECT 
                    %s.%s,
                    %s.%s,
                    %s.%s,
                    %s.%s,
                    %s.%s,
                    %s.%s,
                    %s.%s,
                    %s.%s,
                    %s.%s
                FROM 
                    %s 
                INNER JOIN 
                    %s ON %s.%s = %s.%s
                INNER JOIN 
                    %s ON %s.%s = %s.%s
                INNER JOIN 
                    %s ON %s.%s = %s.%s
                %s
            """,
            
            // Dados
            DataBaseSchema.EMPRESTIMO.getTableName(), DataBaseSchema.EMPRESTIMO.getColumns()[0],
            DataBaseSchema.PESSOA.getTableName(), DataBaseSchema.PESSOA.getColumns()[1],
            DataBaseSchema.REVISTA.getTableName(), DataBaseSchema.REVISTA.getColumns()[0],
            DataBaseSchema.REVISTA.getTableName(), DataBaseSchema.REVISTA.getColumns()[1],
            DataBaseSchema.REVISTA.getTableName(), DataBaseSchema.REVISTA.getColumns()[2],
            DataBaseSchema.REVISTA.getTableName(), DataBaseSchema.REVISTA.getColumns()[3],
            DataBaseSchema.CAIXA.getTableName(), DataBaseSchema.CAIXA.getColumns()[1],
            DataBaseSchema.EMPRESTIMO.getTableName(), DataBaseSchema.EMPRESTIMO.getColumns()[3],
            DataBaseSchema.EMPRESTIMO.getTableName(), DataBaseSchema.EMPRESTIMO.getColumns()[4],
            
            // FROM
            DataBaseSchema.EMPRESTIMO.getTableName(),
            
            // INNER JOIN + Pessoa
            DataBaseSchema.PESSOA.getTableName(),
            DataBaseSchema.EMPRESTIMO.getTableName(), DataBaseSchema.EMPRESTIMO.getColumns()[1],
            DataBaseSchema.PESSOA.getTableName(), DataBaseSchema.PESSOA.getColumns()[0],
            
            // INNER JOIN + Revista
            DataBaseSchema.REVISTA.getTableName(),
            DataBaseSchema.EMPRESTIMO.getTableName(), DataBaseSchema.EMPRESTIMO.getColumns()[2],
            DataBaseSchema.REVISTA.getTableName(), DataBaseSchema.REVISTA.getColumns()[0],
            
            // INNER JOIN + Caixa
            DataBaseSchema.CAIXA.getTableName(),
            DataBaseSchema.REVISTA.getTableName(), DataBaseSchema.REVISTA.getColumns()[5],
            DataBaseSchema.CAIXA.getTableName(), DataBaseSchema.CAIXA.getColumns()[0],
            
            whereClause != null ? "WHERE " + whereClause : ""
        );
        
        List<Emprestimo> emprestimos = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
        	for (int i = 0; i < params.length; i++)
                preparedStatement.setObject(i + 1, params[i]);
        	
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
                emprestimos.add(mapResultSetToEmprestimo(resultSet));
            
        } 

        return emprestimos;
    }
    
	/**
	 * Retorna uma lista de todos os empréstimos do banco de dados
	 * 
	 * @return Uma lista de objetos Emprestimo
	 */
    public List<Emprestimo> list() {
    	try {
			return getEmprestimos(null);
		} catch (SQLException e) {
			throw new RuntimeException(ERROR_LIST);
		}
    }
    
	/**
	 * Retorna uma lista de todos os empréstimos ativos, ou seja, empréstimos cuja
	 * data de devolução (dataDevolucao) é NULL.
	 * 
	 * @return Uma lista de objetos Emprestimo representando os empréstimos ativos.
	 */
    public List<Emprestimo> getEmprestimosAtivos() {
    	
        String whereClause = DataBaseSchema.EMPRESTIMO.getColumns()[4] + " IS NULL";
        
        try {
			return getEmprestimos(whereClause);
		} catch (SQLException e) {
			throw new RuntimeException(ERROR_LIST_ACTIVE);
		}
    }
    
	/**
	 * Retorna uma lista de todos os empréstimos atrasados. Considera que um
	 * empréstimo está atrasado se:
	 * 
	 * 1. Não há data de devolução (dataDevolucao é NULL). 2. A data do empréstimo
	 * (dataEmprestimo) somada ao prazo permitido (NUM_DAYS_DELAY) já passou da data
	 * atual.
	 * 
	 * @return Uma lista de objetos Emprestimo representando os empréstimos
	 *         atrasados.
	 */
    public List<Emprestimo> getEmprestimosAtrasados() {
        String whereClause = String.format("%s IS NULL AND %s + INTERVAL '%d days' < CURRENT_DATE",
            DataBaseSchema.EMPRESTIMO.getColumns()[4],
            DataBaseSchema.EMPRESTIMO.getColumns()[3],
            NUM_DAYS_DELAY
        );
        try {
			return getEmprestimos(whereClause);
		} catch (SQLException e) {
			throw new RuntimeException(ERROR_LIST_LATE);
		}
    }
    
	/**
	 * Retorna uma lista de empréstimos associados a uma pessoa específica,
	 * identificada pelo idPessoa.
	 * 
	 * @param idPessoa O identificador único da pessoa cujos empréstimos serão consultados.
	 * 
	 * @return Uma lista de objetos Emprestimo relacionados à pessoa especificada.
	 */
    public List<Emprestimo> getEmprestimosPorPessoa(Long idPessoa) {
    	
        String whereClause = String.format(" %s.%s = ?", DataBaseSchema.EMPRESTIMO.getTableName(), DataBaseSchema.EMPRESTIMO.getColumns()[1]);
        try {
			return getEmprestimos(whereClause, idPessoa);
		} catch (SQLException e) {
			throw new RuntimeException(ERROR_LIST_PERSON);
		}
    }
} // class EmprestimoDAO