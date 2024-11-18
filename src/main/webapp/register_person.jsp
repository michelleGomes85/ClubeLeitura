<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="authentication.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>Clube da Leitura - Registro Pessoa</title>

<!-- Link para o Favicon -->
<link rel="icon" href="${pageContext.request.contextPath}/img/favicon.png" type="image/x-icon">

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
            
            <h1>Cadastro de Pessoa</h1>
            
            <!-- Campo de Nome -->
            <div class="form-group">
                <label for="nome">Nome:</label>
                <input type="text" id="nome" name="nome" placeholder="Digite o nome completo" required>
            </div>
            
            <!-- Campo de Telefone -->
            <div class="form-group">
                <label for="telefone">Telefone:</label>
                <input type="tel" id="telefone" name="telefone" placeholder="Digite o número de telefone" required maxlength="15" oninput="formatPhone(this)">
            </div>
            
            <!-- Botão de Cadastro -->
            <button type="submit" class="btn-submit">Cadastrar Pessoa</button>
            
            <!-- Campo Hidden para Identificar a Ação -->
            <input type="text" hidden="true" name="service" value="RegisterPerson">
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