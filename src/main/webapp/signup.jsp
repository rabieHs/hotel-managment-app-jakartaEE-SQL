<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Créer un compte</title>
</head>
<body>
    <h2>Créer un compte</h2>
    <form action="SignUpServlet" method="POST">
        <label for="username">Nom d'utilisateur :</label>
        <input type="text" name="username" required>
        <br>
        <label for="password">Mot de passe :</label>
        <input type="password" name="password" required>
        <br>
        <label for="phone">Numéro de téléphone :</label>
        <input type="tel" name="phone" required>
        <br>
        <button type="submit">Créer un compte</button>
    </form>
</body>
</html>
