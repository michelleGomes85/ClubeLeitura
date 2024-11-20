<%@page import="main.java.tsi.daw.dao.EmprestimoDAO"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@page import="main.java.tsi.daw.dao.RevistaDAO"%>
<%@page import="main.java.tsi.daw.model.Revista"%>
<%@page import="java.util.List"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ include file="authentication.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">

	<!-- Incluindo a biblioteca de ícones do Font Awesome -->
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
	
	<!-- Arquivos de estilo -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/options_styles.css"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/registrations_styles.css"/>
</head>
<body>

	<c:import url="header.jsp"></c:import>

    <%
    	Map<Long, Revista> emprestimoRevistas = new HashMap<>();
        try {
        	EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
        	emprestimoRevistas = emprestimoDAO.listRevistaEmprestimo();
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("emprestimoRevistas", emprestimoRevistas);
    %>

    <main class="form-container">
    	
    	<div  class="register-form">

			<h1>Lista de Revistas</h1>

			<c:choose>
				<%-- Mensagem para lista vazia --%>
				<c:when test="${empty emprestimoRevistas}">
					<p class="error-message">Não há nenhuma revista emprestada no momento.</p>
				</c:when>

				<%-- Tabela com os dados --%>
				<c:otherwise>
					<table class="revista-table">
						<thead>
							<tr>
								<th>ID</th>
								<th>Coleção</th>
								<th>Edição</th>
								<th>Ano</th>
								<th>Ações</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="entry" items="${emprestimoRevistas}">
								<tr>
									<td><c:out value="${entry.value.idRevista}" /></td>
									<td><c:out value="${entry.value.colecao}" /></td>
									<td><c:out value="${entry.value.numeroEdicao}" /></td>
									<td><c:out value="${entry.value.anoRevista}" /></td>
									<td>
										<!-- Botão para devolver -->
										<form action="${pageContext.request.contextPath}/controller"
											method="post" class="class_return">
											<input type="hidden" name="idEmprestimo" value="${entry.key}" />
											<input type="hidden" name="idRevista" value="${entry.value.idRevista}" />
											<button type="submit" class="btn-devolver" title="Devolver">
												<i class="fas fa-undo"></i>
											</button>
											<input type="text" hidden="true" name="service" value="RegisterReturn">
										</form>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:otherwise>
			</c:choose>


		</div>
    </main>

    <c:if test="${not empty messageReturn}">
        <div id="messageModal" class="modal">
            <div class="modal-content">
                <h2>Mensagem Informativa</h2>
                <p><c:out value="${messageReturn}" /></p>
                <button onclick="closeModal()" class="btn-submit">Fechar</button>
            </div>
        </div>
    </c:if>

    <script type="text/javascript" src="${pageContext.request.contextPath}/script/script.js"></script>
	
</body>
</html>