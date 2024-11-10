package main.java.tsi.daw.model;

public class Revista {
	
    private Long idRevista;
    private String colecao;
    private int numeroEdicao;
    private int anoRevista;
    private boolean disponibilidade;
    private Caixa caixa;
    
    public Revista() {}
    
	public Long getIdRevista() {
		return idRevista;
	}

	public void setIdRevista(Long idRevista) {
		this.idRevista = idRevista;
	}

	public String getColecao() {
		return colecao;
	}
	
	public void setColecao(String colecao) {
		this.colecao = colecao;
	}
	
	public int getNumeroEdicao() {
		return numeroEdicao;
	}
	
	public void setNumeroEdicao(int numeroEdicao) {
		this.numeroEdicao = numeroEdicao;
	}
	
	public int getAnoRevista() {
		return anoRevista;
	}
	
	public void setAnoRevista(int anoRevista) {
		this.anoRevista = anoRevista;
	}
	
	public boolean isDisponibilidade() {
		return disponibilidade;
	}
	
	public void setDisponibilidade(boolean disponibilidade) {
		this.disponibilidade = disponibilidade;
	}
	
	public Caixa getCaixa() {
		return caixa;
	}
	
	public void setCaixa(Caixa caixa) {
		this.caixa = caixa;
	}
}