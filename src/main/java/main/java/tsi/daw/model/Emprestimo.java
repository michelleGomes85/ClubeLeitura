package main.java.tsi.daw.model;

import java.time.LocalDate;

public class Emprestimo {
	
	private Long idEmprestimo;
	private Pessoa pessoa;
	private Revista revista;
	private LocalDate dataEmprestimo;
	private LocalDate dataDevolucao;
	
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

	public LocalDate getDataEmprestimo() {
		return dataEmprestimo;
	}

	public void setDataEmprestimo(LocalDate dataEmprestimo) {
		this.dataEmprestimo = dataEmprestimo;
	}

	public LocalDate getDataDevolucao() {
		return dataDevolucao;
	}

	public void setDataDevolucao(LocalDate dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}
}
