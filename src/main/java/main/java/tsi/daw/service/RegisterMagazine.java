package main.java.tsi.daw.service;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.java.tsi.daw.dao.RevistaDAO;
import main.java.tsi.daw.model.Caixa;
import main.java.tsi.daw.model.Revista;

public class RegisterMagazine implements Service {

	private static final String PARAMETER_COLECTION = "colecao";
	private static final String PARAMETER_EDITION_NUMBER = "num-edicao";
	private static final String PARAMETER_YEAR = "ano-revista";
	private static final String PARAMETER_BOX = "idcaixa";
	
	private static final String SUCCESS_MESSAGE_STR = "Revista colecao %s cadastrada com sucesso!";
	private static final String MESSAGE_RETURN = "messageReturn";
	
	private static final String PAGE_BACK = "op_register_magazine.jsp";
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String colection = request.getParameter(PARAMETER_COLECTION);
		String editionNumber = request.getParameter(PARAMETER_EDITION_NUMBER);
		String year = request.getParameter(PARAMETER_YEAR);
		String box = request.getParameter(PARAMETER_BOX);
		
		RevistaDAO revistaDAO = new RevistaDAO();
		Revista revista = new Revista();
		
		revista.setColecao(colection);
		revista.setNumeroEdicao(Integer.valueOf(editionNumber));
		revista.setAnoRevista(Integer.valueOf(year));
		
		Caixa caixa = new Caixa();
		caixa.setIdCaixa(Long.valueOf(box));
		revista.setCaixa(caixa);
		revista.setDisponibilidade(true);
		
		try {
			revistaDAO.insert(revista);
			request.setAttribute(MESSAGE_RETURN, String.format(SUCCESS_MESSAGE_STR, colection));		
			} catch (RuntimeException e) {
				request.setAttribute(MESSAGE_RETURN, e);
		}
		
		return PAGE_BACK;
	}
}
