<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>MyPictures - Log In</title>
    <link rel="stylesheet" type="text/css" href="/jsp/login/loginPage.css">
    <link rel="stylesheet" type="text/css" href="/General CSS/ScrollBar.css">
    <link rel="icon" type="image/png" href="/images/icon.png">
</head>
<body>
<div class="wrapper">
    <nav class="header">
        <a href="${pageContext.request.contextPath}/home">MyPictures</a>
    </nav>
    <canvas id="canvas"></canvas>
    <script src="/General JS/graphScript.js"></script>
    <div class="login-container">
        <h1>Log In</h1>
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

        <form action="${pageContext.request.contextPath}/login" method="post">
            <label for="usernameOrEmail">Username Or Email:</label>
            <input type="text" id="usernameOrEmail" name="usernameOrEmail" required placeholder="Username Or Email">

            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required placeholder="Password"><br>
            <input type="submit" value="Log In">
            <div class="remember-me">
                <input type="checkbox" id="remember-me" name="rememberMe">
                <label for="remember-me">Remember Me For A Month</label>
            </div>
        </form>
        <form action="${pageContext.request.contextPath}/register" method="get">
            <button type="submit">Sign Up</button>
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
