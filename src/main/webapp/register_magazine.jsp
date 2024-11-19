<%@page import="main.java.tsi.daw.dao.CaixaDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="main.java.tsi.daw.model.Caixa"%>
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
	
    <main class="form-container">
    	
    	<% 
            List<Caixa> caixas = new ArrayList<Caixa>();
            try {
                CaixaDAO caixaDAO = new CaixaDAO();
                caixas = caixaDAO.list();
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            request.setAttribute("caixas", caixas);
        %>


		<form action="${pageContext.request.contextPath}/controller" method="post" class="register-form">

			<h1>Cadastro de Revista</h1>

			<c:choose>

				<%-- Verifica se há caixas cadastradas --%>
				<c:when test="${empty caixas}">
					<p class="error-message">Não é possível realizar o cadastro, pois não há caixas disponíveis</p>
				</c:when>

				<%-- Se houver caixas cadastradas --%>
				<c:otherwise>

					<div class="form-group">
						<label for="colecao">Coleção:</label> 
						<input type="text" id="colecao" name="colecao" placeholder="Digite a coleção da revista" required>
					</div>

					<div class="form-group">
						<label for="num-edicao">Número da Edição:</label> 
						<input type="number" id="num-edicao" name="num-edicao" placeholder="Digite o número da edição" required>
					</div>

					<div class="form-group">
						<label for="ano-revista">Ano da Revista:</label> 
						<input type="number" id="ano-revista" name="ano-revista" placeholder="Digite o ano da revista" required>
					</div>

					<div class="form-group">
						<label for="idcaixa">Caixa:</label> <select id="idcaixa" name="idcaixa" required>
							<option value="" disabled selected>Escolha uma caixa</option>
							<c:forEach items="${caixas}" var="caixa">
								<option value="${caixa.idCaixa}">${caixa.cor}</option>
							</c:forEach>
						</select>
					</div>

					<button type="submit" class="btn-submit">Cadastrar Revista</button>

					<input type="text" hidden="true" name="service" value="RegisterMagazine">
				</c:otherwise>
			</c:choose>
		</form>

	</main>
    
    <!-- Modal de Mensagem -->
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