<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
        <form action="${pageContext.request.contextPath}/controller" method="post" class="register-form">
            
            <h1>Cadastro de Caixa</h1>
            
            <div class="form-group">
                <label for="cor">Cor da Caixa:</label>
                <input type="text" id="cor" name="cor" placeholder="Digite a cor da caixa" required>
            </div>
            
            <button type="submit" class="btn-submit">Cadastrar Caixa</button>
            
            <input type="text" hidden="true" name="service" value="RegisterBox">
        </form>
    </main>
    
    <c:if test="${not empty messageReturn}">
	    <div id="messageModal" class="modal">
	        <div class="modal-content">
	            <h2>Messagem Informativa</h2>
	            <p><c:out value="${messageReturn}" /></p>
	            <button onclick="closeModal()" class="btn-submit">Fechar</button>
	        </div>
	    </div>
	</c:if>

	<script type="text/javascript" src="${pageContext.request.contextPath}/script/script.js"></script>
</body>
</html>