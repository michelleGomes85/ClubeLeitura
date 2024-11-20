<%@page import="main.java.tsi.daw.model.Emprestimo"%>
<%@page import="main.java.tsi.daw.dao.EmprestimoDAO"%>
<%@page import="main.java.tsi.daw.dao.RevistaDAO"%>
<%@page import="main.java.tsi.daw.dao.PessoaDAO"%>
<%@page import="main.java.tsi.daw.model.Revista"%>
<%@page import="main.java.tsi.daw.model.Pessoa"%>
<%@page import="java.util.List"%>

<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ include file="authentication.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Revistas Emprestadas</title>

    <!-- Incluindo a biblioteca de ícones do Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    
    <!-- Arquivos de estilo -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/options_styles.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/registrations_styles.css"/>
</head>
<body>

    <c:import url="header.jsp"></c:import>
    
    <%
        PessoaDAO pessoaDao = new PessoaDAO();
        List<Pessoa> pessoas = pessoaDao.list();
        request.setAttribute("pessoas", pessoas);
        
        String idPessoaParam = request.getParameter("idPessoa");
        
        request.setAttribute("idPessoaParam", idPessoaParam);
        
        if (idPessoaParam != "") {
     		
        	List<Emprestimo> emprestimos = null;

            if (idPessoaParam != null) {
                Long idPessoa = Long.parseLong(idPessoaParam);
				
                EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
                emprestimos = emprestimoDAO.getEmprestimosPorPessoa(idPessoa);
                request.setAttribute("emprestimos", emprestimos);
            }
        }
        
     %>

	<main class="register-form min">

		<h1>Lista de emprestimos por pessoa</h1>

		<c:choose>

			<c:when test="${not empty pessoas}">

				<form method="POST" action="op_list_loan_person.jsp">
					<label for="idPessoa">Selecione uma pessoa:</label> <select
						name="idPessoa" id="idPessoa" onchange="this.form.submit()">
						<option value="" selected>Escolha uma pessoa</option>
						<c:forEach items="${pessoas}" var="pessoa">
							<option value="${pessoa.idPessoa}"
								<c:if test="${pessoa.idPessoa == param.idPessoa}">selected</c:if>>
								${pessoa.nome}</option>
						</c:forEach>
					</select>
				</form>

				<c:choose>

					<%-- Tabela para exibir as revistas da pessoa selecionada --%>
					<c:when test="${not empty emprestimos}">
						<table class="revista-table">
							<thead>
								<tr>
									<th>Caixa</th>
									<th>Coleção</th>
									<th>Edição</th>
									<th>Ano</th>
									<th>Data Emprestimo</th>
									<th>Data Devolução</th>
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
											<c:choose>
												<c:when test="${emprestimo.dataDevolucao == null}">
	                   								PENDENTE
	               								</c:when>
	               								
												<c:otherwise>
													<fmt:formatDate value="${emprestimo.dataDevolucao}" pattern="dd/MM/yyyy" />
												</c:otherwise>
											</c:choose>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:when>

					<c:otherwise>
						<c:if test="${not empty idPessoaParam and empty emprestimos}">
							<p class="error-message space">Não há revistas emprestadas para esta pessoa.</p>
						</c:if>
					</c:otherwise>
				</c:choose>

			</c:when>

			<c:otherwise>
				<p class="error-message space">Ainda não há pessoas cadastradas</p>
			</c:otherwise>
		</c:choose>
	</main>

</body>
</html>