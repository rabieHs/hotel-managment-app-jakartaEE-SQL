<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Models.Hotel" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Agent - Gestion des Hôtels</title>
    <link rel="stylesheet" href="styles/agent_hotel_styles.css">
</head>
<body>
<div class="container">
    <h1>Gestion des Hôtels</h1>

    <!-- Formulaire de filtres -->
    <div class="filter-section">
        <form action="AgentServlet" method="get" class="filter-form">
            <label>Nom :</label>
            <input type="text" name="name" placeholder="Nom de l'hôtel">
            <label>Ville :</label>
            <input type="text" name="city" placeholder="Ville">
            <label>Étoiles :</label>
            <input type="number" name="stars" min="1" max="5" placeholder="Étoiles">
            <input type="submit" value="Filtrer">
        </form>
    </div>

    <!-- Liste des Hôtels -->
    <table class="hotels-table">
        <thead>
        <tr>
            <th>Image</th>
            <th>Nom</th>
            <th>Ville</th>
            <th>Étoiles</th>
            <th>Description</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <!-- Using JSTL to loop through the list of hotels -->
        <c:forEach var="hotel" items="${hotel}">
            <tr>
                <td>
                    <c:if test="${not empty hotel.image}">
                        <img src="${pageContext.request.contextPath}/${hotel.image}" alt="Hotel Image">
                    </c:if>
                    <c:if test="${empty hotel.image}">
                        Pas d'image
                    </c:if>
                </td>
                <td>${hotel.name}</td>
                <td>${hotel.city}</td>
                <td>${hotel.stars}</td>
                <td>${hotel.descriptions}</td>
                <td class="action-buttons">
                    <form action="AgentServlet" method="post">
                        <input type="hidden" name="id" value="${hotel.id}">
                        <input type="hidden" name="action" value="deleteHotel">
                        <input type="submit" value="Supprimer">
                    </form>
                    <form action="RoomServlet" method="get">
                        <input type="hidden" name="hotelId" value="${hotel.id}">
                        <input type="submit" value="Gérer les Chambres">
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>


    <h1>Gestion des Reservations</h1>

    <table class="hotels-table">
        <thead>
        <tr>
            <th>Id</th>
            <th>roomId</th>
            <th>hotelId</th>
            <th>userName</th>
            <th>userPhone</th>
            <th>startDate</th>
            <th>endDate</th>
        </tr>
        </thead>
        <tbody>
        <!-- Using JSTL to loop through the list of hotels -->
        <c:forEach var="reservation" items="${reservations}">
            <tr>

                <td>${reservation.id}</td>
                <td>${reservation.roomId}</td>
                <td>${reservation.hotelId}</td>
                <td>${reservation.userName}</td>
                <td>${reservation.userPhone}</td>
                <td>${reservation.startDate}</td>
                <td>${reservation.endDate}</td>

            </tr>
        </c:forEach>
        </tbody>
    </table>


    <!-- Formulaire pour Ajouter un Hôtel -->
    <h2>Ajouter un Hôtel</h2>
    <form action="AgentServlet" method="post" enctype="multipart/form-data" class="add-hotel-form">
        <input type="hidden" name="action" value="addHotel">

        <label>Nom :</label>
        <input type="text" name="name" required>

        <label>Ville :</label>
        <input type="text" name="city" required>

        <label>Étoiles :</label>
        <input type="number" name="stars" required min="1" max="5">

        <label>Description :</label>
        <textarea name="description" required></textarea>

        <label>Image :</label>
        <input type="file" name="image" accept="image/*">

        <input type="submit" value="Ajouter">
    </form>
</div>
</body>
</html>
