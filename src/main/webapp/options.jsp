<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="authentication.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
	<title>Clube da Leitura - Opções</title>
	
	<!-- Link para o Favicon -->
    <link rel="icon" href="img/favicon.png" type="image/x-icon">
	
	<!-- Incluindo a biblioteca de ícones do Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    
    <!-- style - meu -->
    <link rel="stylesheet" href="styles/options_styles.css"/>
</head>
<body>

	<!-- Menu de navegação -->
	<nav class="navbar">
		<div class="navbar-container">
			<a href="home_page.jsp" class="navbar-logo">
				<img class="img-log" src="img/logo.png"> 
				Clube da Leitura
			</a>
			<div class="navbar-links">
				<a href="home_page.jsp" class="nav-link home-link" title="Voltar para a página inicial"><i class="fas fa-home"></i></a>
				<a href="controller?service=EfetuaLogout" class="nav-link logout-link" title="Sair da conta"><i class="fas fa-sign-out-alt"></i></a>
			</div>
		</div>
	</nav>

	<!-- Grid de opções -->
	<div class="grid">
		
		<a href="op_register_box.jsp" class="grid-item">
			<i class="fas fa-box"></i> Cadastrar Caixa
		</a>
		
		<a href="op_register_person.jsp" class="grid-item">
			<i class="fas fa-user"></i> Cadastrar Pessoa
		</a>
		
		<a href="op_register_magazine.jsp" class="grid-item">
			<i class="fas fa-book"></i> Cadastrar Revista
		</a>
		
		<a href="op_register_loan.jsp" class="grid-item">
			<i class="fas fa-hand-holding"></i> Realizar Empréstimo
		</a>
		
		<a href="op_list_borrowed_magazines.jsp" class="grid-item">
			<i class="fas fa-book-reader"></i> Listar Revistas Emprestadas
		</a>
		
		<a href="op_list_borrowed_magazines_late.jsp" class="grid-item">
			<i class="fas fa-exclamation-triangle"></i> Listar Empréstimos em Atraso
		</a>
		
		<a href="op_list_loan_person.jsp" class="grid-item">
			<i class="fas fa-search"></i> Listar Revistas de Pessoa Específica
		</a>
	</div> <!--grid -->
</body>
</html>