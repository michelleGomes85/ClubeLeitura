package main.java.tsi.daw.service;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.java.tsi.daw.dao.CaixaDAO;
import main.java.tsi.daw.model.Caixa;

public class RegisterCash implements Service {

	private static final String PARAMETER_COLOR = "cor";
	private static final String SUCCESS_MESSAGE_STR = "Caixa %s cadastrada com sucesso!";
	private static final String MESSAGE_RETURN = "messageReturn";
	
	private static final String PAGE_BACK = "cash_register.jsp";
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String color = request.getParameter(PARAMETER_COLOR);
		
		CaixaDAO caixaDAO = new CaixaDAO();
		Caixa caixa = new Caixa();
		
		caixa.setCor(color);
		
		try {
			caixaDAO.insert(caixa);
			request.setAttribute(MESSAGE_RETURN, String.format(SUCCESS_MESSAGE_STR, color));
		} catch (RuntimeException e) {
			request.setAttribute(MESSAGE_RETURN, e);
		}
		
		return PAGE_BACK;
	}
}