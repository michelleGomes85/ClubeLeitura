package main.java.tsi.daw.service;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import main.java.tsi.daw.dao.UsuarioDAO;
import main.java.tsi.daw.model.Usuario;

public class EfetuaLogin implements Service {
	
	private static final String PARAMETER_LOGIN = "username";
	private static final String PARAMETER_SENHA = "password";
	
	private static final String PARAMETER_ATTRIBUTE_STATUS = "status";
	private static final String PARAMETER_ATTRIBUTE_NAME = "nome";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String login = request.getParameter(PARAMETER_LOGIN);
		String senha = request.getParameter(PARAMETER_SENHA);
		
		String url = "login.jsp";
		
		UsuarioDAO userDao = new UsuarioDAO();
		Usuario user = userDao.validateCredential(login, senha);
		
		if (user != null) {
			
			HttpSession session = request.getSession();
			
			session.setMaxInactiveInterval(2*60);
			session.setAttribute(PARAMETER_ATTRIBUTE_STATUS, true);
			session.setAttribute(PARAMETER_ATTRIBUTE_NAME, login);
			
			url = "home_page.jsp";
		}
		
		return url;
	}
}
