<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Models.Hotel" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Page Visiteur - Liste des Hôtels</title>
    <link rel="stylesheet" href="styles/visitor_list_styles.css">
</head>
<body>
<div class="container">
    <header>
        <h1>Découvrez nos Hôtels</h1>
    </header>

    <div class="filter-section">
        <form action="VisiteurServlet" method="get" class="filter-form">
            <div class="form-row">
                <div class="form-group">
                    <label for="city">Ville :</label>
                    <input type="text" name="city" id="city" placeholder="Entrez une ville">
                </div>
                <div class="form-group">
                    <label for="minStars">Étoiles (min) :</label>
                    <input type="number" name="minStars" id="minStars" min="1" max="5" placeholder="1-5">
                </div>
                <div class="form-group">
                    <label for="maxPrice">Prix maximum :</label>
                    <input type="number" name="maxPrice" id="maxPrice" step="0.01" placeholder="Prix max">
                </div>
                <button type="submit" class="filter-btn">Filtrer</button>
            </div>
        </form>
    </div>

    <div class="hotels-list">
        <%
            List<Hotel> hotels = (List<Hotel>) request.getAttribute("hotels");
            if (hotels != null && !hotels.isEmpty()) {
                for (Hotel hotel : hotels) {
        %>
        <div class="hotel-card">
            <div class="hotel-image">
                <% if (hotel.getImage() != null && !hotel.getImage().isEmpty()) { %>
                <img src="<%= request.getContextPath() %>/<%= hotel.getImage() %>"
                     alt="<%= hotel.getName() %> Hotel"
                     onerror="this.src='path/to/default-hotel-image.png';">
                <% } else { %>
                <img src="path/to/default-hotel-image.png" alt="Default Hotel Image">
                <% } %>
            </div>
            <div class="hotel-header">
                <h3><%= hotel.getName() %></h3>
                <span class="hotel-stars">
                    <% for(int i = 0; i < hotel.getStars(); i++) { %>
                        ★
                    <% } %>
                </span>
            </div>
            <div class="hotel-details">
                <p><strong>Ville :</strong> <%= hotel.getCity() %></p>
                <p class="hotel-description"><%= hotel.getDescriptions() %></p>
                <form action="RoomHotelServlet" method="get" class="hotel-action">
                    <input type="hidden" name="hotelId" value="<%= hotel.getId() %>">
                    <button type="submit" class="details-btn">Voir Détails</button>
                </form>
            </div>
        </div>
        <% }
        } else {
        %>
        <div class="no-hotels">
            <p>Aucun hôtel disponible pour le moment.</p>
        </div>
        <% } %>
    </div>
</div>
</body>
</html>