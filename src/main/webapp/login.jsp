<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
	<title>Clube da Leitura</title>
	
	<!-- Link para o Favicon -->
    <link rel="icon" href="img/favicon.png" type="image/x-icon">

	<!-- Incluindo a biblioteca de ícones do Font Awesome -->
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
	
	<!-- style - meu -->
    <link rel="stylesheet" href="styles/login_styles.css"/>
</head>
<body>
	<div class="login-container">
        <div class="login-box">
            <h2>Login</h2>
            <form action="" method="POST">
                <div class="input-group">
                    <i class="fas fa-user"></i>
                    <input type="text" placeholder="Usuário" name="username" required>
                </div>
                <div class="input-group">
                    <i class="fas fa-lock"></i>
                    <input type="password" placeholder="Senha" name="password" id="password" required>
                    <i class="fas fa-eye password-toggle" id="togglePassword" onclick="togglePasswordVisibility()"></i>
                </div>
                <button type="submit">Entrar</button>
            </form>
        </div>
    </div>
    
    <script type="text/javascript" src="script/script.js"></script>
</body>
</html>