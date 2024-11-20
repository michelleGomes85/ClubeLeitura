package main.java.tsi.daw.model;

import java.util.Date;

public class Emprestimo {
	
	private Long idEmprestimo;
	private Pessoa pessoa;
	private Revista revista;
	private Date dataEmprestimo;
	private Date dataDevolucao;
	
	public Emprestimo() {}

	public Long getIdEmprestimo() {
		return idEmprestimo;
	}

	public void setIdEmprestimo(Long idEmprestimo) {
		this.idEmprestimo = idEmprestimo;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Revista getRevista() {
		return revista;
	}

	public void setRevista(Revista revista) {
		this.revista = revista;
	}

	public Date getDataEmprestimo() {
		return dataEmprestimo;
	}

	public void setDataEmprestimo(Date dataEmprestimo) {
		this.dataEmprestimo = dataEmprestimo;
	}

	public Date getDataDevolucao() {
		return dataDevolucao;
	}

	public void setDataDevolucao(Date dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}
}
