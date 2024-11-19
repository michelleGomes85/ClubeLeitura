package main.java.tsi.daw.service;

import java.io.IOException;
import java.time.LocalDate;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.java.tsi.daw.dao.EmprestimoDAO;
import main.java.tsi.daw.dao.RevistaDAO;
import main.java.tsi.daw.model.Emprestimo;
import main.java.tsi.daw.model.Revista;

public class RegisterReturn implements Service {

	private static final String PARAMETER_IDREVISTA = "idRevista";
	private static final String PARAMETER_IDEMPRESTIMO = "idEmprestimo";
	
	private static final String SUCCESS_MESSAGE_STR = "Revista devolvida com sucesso!";
	private static final String MESSAGE_RETURN = "messageReturn";
	
	private static final String PAGE_BACK = "op_list_borrowed_magazines.jsp";
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String idRevista = request.getParameter(PARAMETER_IDREVISTA);
		String idEmprestimo = request.getParameter(PARAMETER_IDEMPRESTIMO);
		
		RevistaDAO revistaDAO = new RevistaDAO();
		EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
		
		try {
			Revista revista = revistaDAO.findById(Long.valueOf(idRevista));
			revista.setDisponibilidade(true);
			
			revistaDAO.update(revista);
			
			Emprestimo emprestimo = emprestimoDAO.findById(Long.valueOf(idEmprestimo));
			emprestimo.setDataDevolucao(LocalDate.now());
			
			emprestimoDAO.update(emprestimo);
			
			request.setAttribute(MESSAGE_RETURN, SUCCESS_MESSAGE_STR);
		} catch (RuntimeException e) {
			request.setAttribute(MESSAGE_RETURN, e);
		}
		
		return PAGE_BACK;
	}
}
