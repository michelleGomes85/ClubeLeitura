package main.java.tsi.daw.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.java.tsi.daw.service.Service;

/**
 * Controlador que processa a requisição e executa a lógica apropriada
 * com base no parâmetro 'service'.
 */
@WebServlet("/controller")
public class ClubeLeituraController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * Processa a requisição, carrega a classe lógica e executa o serviço.
     * 
     * @param request A requisição HTTP.
     * @param response A resposta HTTP.
     * 
     * @throws ServletException Se ocorrer um erro na execução do servlet.
     * @throws IOException Se ocorrer erro na entrada/saída de dados.
     */
    @SuppressWarnings("deprecation")
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtém o nome da classe lógica a ser executada
        String nameClass = "main.java.tsi.daw.service." + request.getParameter("service");
        
        String url = "";

        try {
            Class<?> classObj = Class.forName(nameClass);
            Service service = (Service) classObj.newInstance();
            url = service.execute(request, response);

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher(url).forward(request, response);
    }
}
