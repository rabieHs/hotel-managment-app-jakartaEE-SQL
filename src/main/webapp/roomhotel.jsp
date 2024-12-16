<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="Models.Room" %>
<%@ page import="Models.Hotel" %>

<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestion des Chambres - ${hotel.getName()}</title>
    <link rel="stylesheet" href="styles/room_hotel_style.css">
</head>
<body>
<div class="container">
    <header>
        <div class="hotel-image">
            <img src="${hotel.getImage()}" alt="${hotel.getName()}" />
        </div>
        <div class="hotelDescription">
            <h1 class="hotelname">${hotel.getName()}</h1>
            <p><strong>Description:</strong> ${hotel.getDescriptions()}</p>
            <div>
                <c:forEach begin="1" end="5" var="i">
                    <span class="star ${i <= hotel.getStars() ? 'filled' : 'notfilled'}">&#9733;</span>
                </c:forEach>
            </div>
        </div>
    </header>

    <!-- Filter Section -->
    <section class="filter-section">
        <form action="RoomHotelServlet" method="get" class="filter-form">
            <div class="form-row">
                <div class="form-group">
                    <label for="maxPrice">Prix maximum :</label>
                    <input type="number" name="maxPrice" id="maxPrice" class="form-input" placeholder="Entrez le prix max" />
                </div>
                <input type="hidden" name="hotelId" value="${hotel.getId()}" />
                <button type="submit" class="filter-btn">Filtrer</button>
            </div>
        </form>
    </section>

    <!-- Rooms List Section -->
    <section class="rooms-list">
        <h2>Liste des Chambres</h2>
        <table class="hotels-table">
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
                    <td>${room.price} €</td>
                    <td><a  class="action-btn">Reserver</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </section>
</div>
</body>
</html>
