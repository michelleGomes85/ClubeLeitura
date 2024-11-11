<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
			<a href="index.html" class="navbar-logo"> <img class="img-log"
				src="img/logo.png"> Clube da Leitura
			</a>
			<div class="navbar-links">
				<a href="logout.jsp" class="nav-link logout-link"
					title="Sair da conta"><i class="fas fa-sign-out-alt"></i></a>
			</div>
		</div>
	</nav>

	<div class="container">
        <h1>Bem-vindo ao Clube da Leitura!</h1>
        <p>Aplicação criada para organizar e compartilhar uma coleção de revistas em quadrinhos entre amigos. Ela permite o cadastro das revistas e o controle dos empréstimos, garantindo que nenhuma revista se perca.</p>
        <button onclick="window.location.href='options.jsp'">Entrar nas Opções</button>
    </div>
</body>
</html>
