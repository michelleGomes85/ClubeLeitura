<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ include file="authentication.jsp"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Clube da Leitura</title>
    
    <!-- Link para o Favicon -->
    <link rel="icon" href="img/favicon.png" type="image/x-icon">
    
    <!-- Incluindo a biblioteca de ícones do Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    
    <link href="https://fonts.googleapis.com/css2?family=Bangers&display=swap" rel="stylesheet"> 
    
    <link rel="stylesheet" href="styles/home_page_styles.css">
</head>
<body>

	<nav class="navbar">
		<div class="navbar-container">
			<a href="home_page.jsp" class="navbar-logo"> <img class="img-log"
				src="img/logo.png"> Clube da Leitura
			</a>
			<div class="navbar-links">
				<a href="controller?service=EfetuaLogout" class="nav-link logout-link"
					title="Sair da conta"><i class="fas fa-sign-out-alt"></i></a>
			</div>
		</div>
	</nav>

	<div class="container">
        <h1>Bem-vindo ao Clube da Leitura!</h1>
        <p>Aplicação criada para organizar e compartilhar uma coleção de revistas em quadrinhos entre amigos. Ela permite o cadastro das revistas e o controle dos empréstimos, garantindo que nenhuma revista se perca.</p>
        <button onclick="window.location.href='options.jsp'">Entrar nas Opções</button>
    </div>
    
    <button class="floating-button" title="Sobre mim">
	    <i class="fas fa-info"></i>
	</button>

	<div id="myModal" class="modal">
		<div class="modal-content">
			<span class="close">&times;</span>
			<h2>Sobre o Desenvolvedor</h2>
			<ul>
				<li>Este sistema foi criado por <strong>Michelle Cristina Gomes</strong></li>
				<li>Data de criação: <strong>20/11/2024</strong></li>
			</ul>

			<h3>Tecnologias Utilizadas</h3>
			<ul>
				<li>Java com Servlet</li>
				<li>JSP (Java Server Pages)</li>
				<li>HTML, CSS e JavaScript</li>
				<li>PostgreSQL</li>
			</ul>

			<h3>Funcionalidades</h3>
			<ul>
				<li>Cadastro de revistas, usuários e caixas</li>
				<li>Controle de empréstimos e devoluções</li>
				<li>Relatórios de revistas emprestadas e atrasadas</li>
				<li>Interface amigável e responsiva</li>
			</ul>

			<h3>Contato</h3>
			<p class="contact">
				<a href="https://mail.google.com/mail/?view=cm&fs=1&to=gmichele498@gmail.com&su=Contato pelo GitHub&body=Olá%20Tudo%20Bem!" target="_blank"><i class="fas fa-envelope"></i></a> 
				<a href="https://www.linkedin.com/in/michelleGomes85/" target="_blank"><i class="fab fa-linkedin"></i></a>
				<a href="https://github.com/michelleGomes85" target="_blank"	class="github-link"> <i class="fab fa-github"></i></a>
			</p>
		</div>
	</div>

	<script type="text/javascript" src="script/script.js"></script>
</body>
</html>
