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
    <style>
        :root {
            --primary-color: #2575fc;
            --secondary-color: #6a11cb;
            --white: #fff;
            --text-light: rgba(255, 255, 255, 0.9);
            --background-blur: rgba(255, 255, 255, 0.1);
            --shadow-light: rgba(0, 0, 0, 0.2);
            --shadow-medium: rgba(0, 0, 0, 0.3);
        }

        label{
            color: black;
        }

        body {
            font-family: 'Arial', sans-serif;
            background: linear-gradient(to right, var(--secondary-color), var(--primary-color));
            color: var(--white);
            margin: 0;
        }

        .container {
            width: 90%;
            max-width: 1200px;
            margin: auto;
            padding: 20px;
        }

        /* Popup Styles */
        #reservationModal {
            display: none;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, 0.5);
            z-index: 1000;
        }

        .modal-content {
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            background: var(--white);
            padding: 25px;
            border-radius: 15px;
            width: 90%;
            max-width: 500px;
            box-shadow: 0 8px 15px var(--shadow-light);
            text-align: center;
        }

        .modal-content h2 {
            margin-bottom: 20px;
            color: var(--primary-color);
        }

        .modal-content label {
            display: block;
            text-align: left;
            margin: 10px 0 5px;
            font-weight: bold;
        }

        .modal-content input {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid var(--shadow-light);
            border-radius: 8px;
            outline: none;
            font-size: 1rem;
        }

        .close {
            position: absolute;
            top: 10px;
            right: 15px;
            font-size: 24px;
            color: var(--secondary-color);
            cursor: pointer;
            background: none;
            border: none;
            font-weight: bold;
        }

        .modal-content button {
            background: var(--primary-color);
            color: var(--white);
            border: none;
            padding: 10px 20px;
            border-radius: 8px;
            font-size: 1rem;
            cursor: pointer;
            transition: background 0.3s ease;
        }

        .modal-content button:hover {
            background: var(--secondary-color);
        }
    </style>
</head>
<body>
<div class="container">
    <!-- Header Section -->
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
                    <td>
                        <button type="button" class="action-btn" onclick="openModal('${room.id}', ${hotel.getId()}, ${room.price}, ${room.capacity})">
                            Réserver
                        </button>                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </section>
</div>


<!-- Reservation Modal -->
<div id="reservationModal">
    <div class="modal-content">
        <button class="close" onclick="closeModal()">&times;</button>
        <h2>Réserver une Chambre</h2>
        <form id="reservationForm">
            <label for="roomId">Chambre:</label>
            <input type="text" id="roomId" name="roomId" readonly>

            <label for="hotelId">Hôtel:</label>
            <input type="text" id="hotelId" name="hotelId" readonly>

            <label for="userName">Nom:</label>
            <input type="text" id="userName" name="userName" placeholder="Enter Your name"  required>

            <label for="userPhone">Téléphone:</label>
            <input type="text" id="userPhone" name="userPhone" placeholder="Enter Your Phone"  required>

            <label for="startDate">Date de début:</label>
            <input type="date" id="startDate" name="startDate" required>

            <label for="endDate">Date de fin:</label>
            <input type="date" id="endDate" name="endDate" required>

            <button type="button" onclick="reserver()">Réserver</button>
        </form>
    </div>
</div>

<script>
    function openModal(roomLabel, hotelId, price, capacity) {
        document.getElementById('reservationModal').style.display = 'block';
        document.getElementById('roomId').value = roomLabel;
        document.getElementById('hotelId').value = hotelId;
    }

    function closeModal() {
        document.getElementById('reservationModal').style.display = 'none';
    }

    function reserver() {
        const form = document.getElementById('reservationForm');

        // Get values directly from form inputs
        const roomId = document.getElementById('roomId').value;
        const hotelId = document.getElementById('hotelId').value;
        const userName = document.getElementById('userName').value;
        const userPhone = document.getElementById('userPhone').value;
        const startDate = document.getElementById('startDate').value;
        const endDate = document.getElementById('endDate').value;

        // Validate that all fields are filled
        if (!roomId || !hotelId || !userName || !userPhone || !startDate || !endDate) {
            alert('Tous les champs sont obligatoires.');
            return;
        }

        // Prepare form data manually
        const formData = new URLSearchParams();
        formData.append('roomId', roomId);
        formData.append('hotelId', hotelId);
        formData.append('userName', userName);
        formData.append('userPhone', userPhone);
        formData.append('startDate', startDate);
        formData.append('endDate', endDate);

        fetch('ReservationServlet', {
            method: 'POST',
            body: formData,
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        })
            .then(response => response.json())
            .then(data => {
                alert(data.message);
                if (data.success) {
                    closeModal();
                    location.reload();
                }
            })
            .catch(error => {
                alert('Erreur lors de la réservation: ' + error);
            });
    }
</script>


</body>
</html>
