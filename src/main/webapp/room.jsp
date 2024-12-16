<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestion des Chambres - ${hotelName}</title>
    <!-- Link to external CSS file -->
    <link rel="stylesheet" href="styles/room_styles.css"></head>
<body>
<div class="container">
    <h1>Gestion des Chambres pour Hôtel : ${hotelName}</h1>

    <!-- Liste des Chambres -->
    <h2>Liste des Chambres</h2>
    <table class="rooms-table">
        <thead>
        <tr>
            <th>Label</th>
            <th>Capacité</th>
            <th>Prix</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="room" items="${rooms}">
            <tr>
                <td>${room.label}</td>
                <td>${room.capacity}</td>
                <td><fmt:formatNumber value="${room.price}" type="currency" currencyCode="EUR"/></td>
                <td class="action-buttons">
                    <form action="RoomServlet" method="post">
                        <input type="hidden" name="action" value="deleteRoom">
                        <input type="hidden" name="roomId" value="${room.id}">
                        <input type="hidden" name="hotelId" value="${room.hotelId}">
                        <input type="submit" value="Supprimer">
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <!-- Ajouter une nouvelle chambre -->
    <h2>Ajouter une Chambre</h2>
    <form class="add-room-form" action="RoomServlet" method="post">
        <input type="hidden" name="action" value="addRoom">
        <input type="hidden" name="hotelId" value="${hotelId}">

        <label for="label">Label :</label>
        <input type="text" id="label" name="label" required>

        <label for="capacity">Capacité :</label>
        <input type="number" id="capacity" name="capacity" required>

        <label for="price">Prix :</label>
        <input type="number" id="price" step="0.01" name="price" required>

        <input type="submit" value="Ajouter">
    </form>
</div>
</body>
</html>
