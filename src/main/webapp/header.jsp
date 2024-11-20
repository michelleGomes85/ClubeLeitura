<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	
	<!-- Link para o Favicon -->
	<link rel="icon" href="${pageContext.request.contextPath}/img/favicon.png" type="image/x-icon">
	
	<!-- Incluindo a biblioteca de ícones do Font Awesome -->
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
	
	<!-- Arquivos de estilo -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/options_styles.css"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/registrations_styles.css"/>
</head>
<body>
	<!-- Menu de navegação -->
	<nav class="navbar">
		<div class="navbar-container">
			<a href="${pageContext.request.contextPath}/home_page.jsp" class="navbar-logo">
				<img class="img-log" src="${pageContext.request.contextPath}/img/logo.png" alt="Logo do Clube da Leitura"> 
				Clube da Leitura
			</a>
			<div class="navbar-links">
			    <a href="${pageContext.request.contextPath}/home_page.jsp" class="nav-link home-link" title="Voltar para a página inicial">
			        <i class="fas fa-home"></i>
			    </a>
			    <a href="${pageContext.request.contextPath}/options.jsp" class="nav-link options-link" title="Opções do Sistema">
			        <i class="fas fa-list"></i>
			    </a>
			    <a href="${pageContext.request.contextPath}/controller?service=EfetuaLogout" class="nav-link logout-link" title="Sair da conta">
			        <i class="fas fa-sign-out-alt"></i>
			    </a>
			</div>
		</div>
	</nav>
</body>
</html>
