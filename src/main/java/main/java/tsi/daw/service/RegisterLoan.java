package main.java.tsi.daw.service;

import java.io.IOException;
import java.util.Date;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.java.tsi.daw.dao.EmprestimoDAO;
import main.java.tsi.daw.dao.RevistaDAO;
import main.java.tsi.daw.model.Emprestimo;
import main.java.tsi.daw.model.Pessoa;
import main.java.tsi.daw.model.Revista;

public class RegisterLoan implements Service {

	private static final String PARAMETER_PERSON = "pessoa";
	private static final String PARAMETER_MAGAZINE = "revista";
	
	private static final String SUCCESS_MESSAGE_STR = "Revista emprestada com sucesso!!";
	private static final String MESSAGE_RETURN = "messageReturn";
	
	private static final String PAGE_BACK = "op_register_loan.jsp";
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String person = request.getParameter(PARAMETER_PERSON);
		String magazine = request.getParameter(PARAMETER_MAGAZINE);
		
		EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
		RevistaDAO revistaDAO = new RevistaDAO();
		Emprestimo emprestimo = new Emprestimo();
		
		Pessoa pessoa = new Pessoa();
		pessoa.setIdPessoa(Long.valueOf(person));
		emprestimo.setPessoa(pessoa);
		
		Long idRevista = Long.valueOf(magazine);
		
		Revista revista = revistaDAO.findById(idRevista);
		revista.setDisponibilidade(false);
		emprestimo.setRevista(revista);
		emprestimo.setDataEmprestimo(new Date());

		try {
			revistaDAO.update(revista);
			emprestimoDAO.insert(emprestimo);
			request.setAttribute(MESSAGE_RETURN, SUCCESS_MESSAGE_STR);
		} catch (RuntimeException e) {
			request.setAttribute(MESSAGE_RETURN, e);
		}
		
		return PAGE_BACK;
	}
}