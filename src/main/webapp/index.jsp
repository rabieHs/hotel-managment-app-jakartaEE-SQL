<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hotel Management App</title>
    <link rel="stylesheet" href="styles/index_styles.css">

</head>
<body onload="showWelcome()">
<header>
    <h1>Welcome to Hotel Management App</h1>
    <p>Streamline your hotel operations with ease. Manage bookings, rooms, and guests all in one place.</p>
</header>
<section class="features">
    <div class="feature-box">
        <img src="https://www.mews.com/hs-fs/hubfs/what-is-a-hotel-key-card-nfc.webp?width=624&height=555&name=what-is-a-hotel-key-card-nfc.webp" alt="Feature Icon">
        <h3>Room Management</h3>
        <p>Easily manage room availability, pricing, and categories.</p>
    </div>
    <div class="feature-box">
        <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTdf4aeD0y2ouA28FgpgcBVutBHqBWPMLLmjg&s" alt="Feature Icon">
        <h3>Booking System</h3>
        <p>Handle bookings seamlessly with our user-friendly interface.</p>
    </div>
    <div class="feature-box">
        <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS9-DVU3n8wkeYz5sXSc5fVTuJWzu9bMsgVmw&s" alt="Feature Icon">
        <h3>Customer Insights</h3>
        <p>Access detailed analytics to understand guest preferences.</p>
    </div>
</section>
<section class="cta">
    <h2>Get Started Today!</h2>
    <a href="login.jsp">Login</a>
    <form action="VisiteurServlet" method="get" style="display: inline;">
        <button type="submit">Continue as Visitor</button>
    </form>
</section>
<footer>
    &copy; 2024 Hotel Management App. All rights reserved.
</footer>
</body>
</html>
