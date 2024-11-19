<%@page import="main.java.tsi.daw.dao.RevistaDAO"%>
<%@page import="main.java.tsi.daw.model.Revista"%>
<%@page import="main.java.tsi.daw.dao.PessoaDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="main.java.tsi.daw.model.Pessoa"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="authentication.jsp"%>

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	
	<title>Clube da Leitura - Registro Emprestimo</title>
	
	<!-- Link para o Favicon -->
	<link rel="icon" href="${pageContext.request.contextPath}/img/favicon.png" type="image/x-icon">
	
	<!-- Incluindo a biblioteca de ícones do Font Awesome -->
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
	
	<!-- Arquivos de estilo -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/options_styles.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/registrations_styles.css" />
</head>

<body>

	<c:import url="header.jsp"></c:import>

	<main class="form-container">

		<%
			List<Pessoa> pessoas = new ArrayList<>();
			try {
				PessoaDAO pessoaDAO = new PessoaDAO();
				pessoas = pessoaDAO.listPessoasSemEmprestimos();
			} catch (Exception e) {
				e.printStackTrace();
			}
	
			request.setAttribute("pessoas", pessoas);
	
			List<Revista> revistas = new ArrayList<>();
			try {
				RevistaDAO revistaDAO = new RevistaDAO();
				revistas = revistaDAO.listByDisponibilidade(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
	
			request.setAttribute("revistas", revistas);
		%>

		<form action="${pageContext.request.contextPath}/controller" method="post" class="register-form">
			
			<h1>Realizar Empréstimo</h1>

			<c:choose>
			
				<%-- Caso as listas estejam vazias --%>
				<c:when test="${empty pessoas || empty revistas}">
					<p class="error-message">Não é possível realizar o cadastro, pois não há pessoas ou revistas disponíveis.</p>
				</c:when>

				<%-- Caso as listas contenham dados --%>
				<c:otherwise>
				
					<div class="form-group">
						<label for="pessoa">Selecione uma Pessoa:</label> 
						
						<select	name="pessoa" id="pessoa" required>
							<option value="" disabled selected>Escolha uma pessoa</option>
							<c:forEach items="${pessoas}" var="pessoa">
								<option value="${pessoa.idPessoa}">${pessoa.nome}</option>
							</c:forEach>
						</select>
					</div>

					<div class="form-group">
						<label for="revista">Selecione uma Revista:</label> 
						
						<select	name="revista" id="revista" required>
							<option value="" disabled selected>Escolha uma revista</option>
							<c:forEach var="revista" items="${revistas}">
								<option value="${revista.idRevista}">${revista.colecao}</option>
							</c:forEach>
						</select>
					</div>

					<button type="submit" class="btn-submit">Realizar Empréstimo</button>
				</c:otherwise>
			</c:choose>

			<input type="hidden" name="service" value="RegisterLoan">
		</form>
	</main>

	<c:if test="${not empty messageReturn}">
		<div id="messageModal" class="modal">
			<div class="modal-content">
				<h2>Messagem Informativa</h2>
				<p>
					<c:out value="${messageReturn}" />
				</p>
				<button onclick="closeModal()" class="btn-submit">Fechar</button>
			</div>
		</div>
	</c:if>

	<script type="text/javascript" src="${pageContext.request.contextPath}/script/script.js"></script>
</body>
</html>