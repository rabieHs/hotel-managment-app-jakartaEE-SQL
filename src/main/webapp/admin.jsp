<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="Models.Account" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Page Admin</title>
    <link rel="stylesheet" href="styles/admin_styles.css">
</head>
<body>
<div class="container">
    <h1>Liste des Agents</h1>
    <table>
        <thead>
        <tr>
            <th>Nom d'utilisateur</th>
            <th>Mot de passe</th>
            <th>Rôle</th>
            <th>Email</th>
            <th>Id</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="agent" items="${agents}">
            <tr>
                <td>${agent.username}</td>
                <td>${agent.password}</td>
                <td>${agent.role}</td>
                <td>${agent.email}</td>
                <td>${agent.id}</td>
                <td>
                    <form action="DeleteAgentServlet" method="get">
                        <input type="hidden" name="id" value="${agent.id}">
                        <input type="submit" value="Supprimer">
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <h2>Créer un Nouvel Agent</h2>
    <form action="AdminServlet" method="post">
        <label for="username">Nom d'utilisateur :</label>
        <input type="text" id="username" name="username" required>

        <label for="password">Mot de passe :</label>
        <input type="password" id="password" name="password" required>

        <label for="role">Rôle :</label>
        <input type="text" id="role" name="role" required>

        <label for="email">Email :</label>
        <input type="email" id="email" name="email" required>

        <input type="submit" value="Créer">
    </form>
</div>
</body>
</html>
