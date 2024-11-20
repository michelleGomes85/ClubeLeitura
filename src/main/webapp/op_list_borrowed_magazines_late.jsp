<%@page import="main.java.tsi.daw.dao.PessoaDAO"%>
<%@page import="main.java.tsi.daw.model.Emprestimo"%>
<%@page import="main.java.tsi.daw.dao.EmprestimoDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="main.java.tsi.daw.dao.RevistaDAO"%>
<%@page import="main.java.tsi.daw.model.Revista"%>
<%@page import="java.util.List"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

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
	List<Emprestimo> emprestimos = new ArrayList<>();
	try {
		EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
		emprestimos = emprestimoDAO.getEmprestimosAtrasados();

		request.setAttribute("emprestimos", emprestimos);
	} catch (Exception e) {
		e.printStackTrace();
	}
	%>

	<main class="form-container">
    	
    	<div  class="register-form">

			<h1>Lista de Revistas Atrasadas</h1>

			<c:choose>
				<%-- Mensagem para lista vazia --%>
				<c:when test="${empty emprestimos}">
					<p class="error-message">Não há nenhuma revista atrasada no momento.</p>
				</c:when>

				<%-- Tabela com os dados --%>
				<c:otherwise>
					<table class="revista-table">
						<thead>
							<tr>
								<th>Caixa</th>
								<th>Coleção</th>
								<th>Edição</th>
								<th>Ano</th>
								<th>Data Emprestimo</th>
								<th>Pessoa</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="emprestimo" items="${emprestimos}">
								<tr>
									<td><c:out value="${emprestimo.revista.caixa.cor}" /></td>
									<td><c:out value="${emprestimo.revista.colecao}" /></td>
									<td><c:out value="${emprestimo.revista.numeroEdicao}" /></td>
									<td><c:out value="${emprestimo.revista.anoRevista}" /></td>
									<td>
										<fmt:formatDate value="${emprestimo.dataEmprestimo}" pattern="dd/MM/yyyy"/>
									</td>
									<td>
										<c:out value="${emprestimo.pessoa.nome}" />
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:otherwise>
			</c:choose>
		</div>
    </main>

    <script type="text/javascript" src="${pageContext.request.contextPath}/script/script.js"></script>
	
</body>
</html>