<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Connexion - Gestion Hôtelière</title>
    <link rel="stylesheet" href="styles/login_styles.css">
</head>
<body>
<div class="login-container">
    <form action="LoginServlet" method="POST" class="login-form">
        <h2>Bienvenuee</h2>
        <p>Connectez-vous à votre espace de gestion hôtelière</p>

        <div class="form-group">
            <label for="username">Nom d'utilisateur</label>
            <input type="text" id="username" name="username" required
                   placeholder="Entrez votre nom d'utilisateur">
        </div>

        <div class="form-group">
            <label for="password">Mot de passe</label>
            <input type="password" id="password" name="password" required
                   placeholder="Entrez votre mot de passe">
        </div>

        <div class="form-actions">
            <button type="submit" class="login-btn">Se connecter</button>
            <a href="#" class="forgot-password">Mot de passe oublié ?</a>
        </div>

        <div class="signup-link">
            Vous n'avez pas de compte ? <a href="register.jsp">Inscrivez-vous</a>
        </div>
    </form>
</div>
</body>
</html>