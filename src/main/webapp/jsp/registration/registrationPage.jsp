<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>MyPictures - Sign Up</title>
    <link rel="stylesheet" type="text/css" href="/jsp/registration/registrationPage.css">
    <link rel="icon" type="image/png" href="/images/icon.png">
</head>
<body>
<div class="wrapper">
    <canvas id="canvas"></canvas>
    <script src="/General JS/graphScript.js"></script>
    <nav class="header">
        <a href="${pageContext.request.contextPath}/home">MyPictures</a>
    </nav>
    <div class="register-container">
        <h1>Sign Up</h1>
        <%
            List<String> errors = (List<String>) request.getAttribute("errors");
        %>

        <%if (errors != null) { %>
        <ul class="errors">
            <% for (String error : errors) { %>
            <li><%= error %>
            </li>
            <% } %>
        </ul>
        <%}%>

        <form action="${pageContext.request.contextPath}/register" method="post">
            <input type="text" id="username" name="username" required placeholder="Enter Username">
            <input type="email" id="email" name="email" required placeholder="Enter Email">
            <input type="password" id="password" name="password" required placeholder="Enter Password">
            <input type="text" id="firstName" name="firstName" required placeholder="Enter Firstname">
            <input type="text" id="surname" name="surname" required placeholder="Enter Surname">
            <input type="text" id="phoneNumber" name="phoneNumber" required placeholder="Enter Phone Number"><br>

            <input type="submit" value="Register">
        </form>
        <form action="${pageContext.request.contextPath}/login" method="get">
            <button type="submit">Log In</button>
        </form>
    </div>
    <footer class="footer">
        <div class="footer-content">
            <p>&copy; 2023 MyPictures. All rights reserved.</p>
        </div>
        <p>Author: Beso Managadze</p>
    </footer>
</div>
</body>
</html>
