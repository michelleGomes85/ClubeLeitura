package main.java.tsi.daw.service;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.java.tsi.daw.dao.PessoaDAO;
import main.java.tsi.daw.model.Pessoa;

public class RegisterPerson implements Service {

	private static final String PARAMETER_NAME = "nome";
	private static final String PARAMETER_PHONE = "telefone";
	private static final String SUCCESS_MESSAGE_STR = "Pessoa %s cadastrada com sucesso!";
	private static final String MESSAGE_RETURN = "messageReturn";
	
	private static final String PAGE_BACK = "op_register_person.jsp";
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String name = request.getParameter(PARAMETER_NAME);
		String phone = request.getParameter(PARAMETER_PHONE);
		
		PessoaDAO pessoaDAO = new PessoaDAO();
		Pessoa pessoa = new Pessoa();
		
		pessoa.setNome(name);
		pessoa.setTelefone(phone);
		
		try {
			pessoaDAO.insert(pessoa);
			request.setAttribute(MESSAGE_RETURN, String.format(SUCCESS_MESSAGE_STR, name));
		} catch (RuntimeException e) {
			request.setAttribute(MESSAGE_RETURN, e);
		}
		
		return PAGE_BACK;
	}
}
