<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>MyPictures - Sign Up</title>
</head>
<body>
<h1>Sign Up</h1>
<%
    List<String> errors = (List<String>) request.getAttribute("errors");
%>

<%if (errors != null) { %>
<ul>
    <% for (String error : errors) { %>
    <li><%= error %>
    </li>
    <% } %>
</ul>
<%}%>

<form action="${pageContext.request.contextPath}/register" method="post">
    <label for="username">Username:</label>
    <input type="text" id="username" name="username" required><br>

    <label for="email">Email:</label>
    <input type="email" id="email" name="email" required><br>

    <label for="password">Password:</label>
    <input type="password" id="password" name="password" required><br>

    <label for="firstName">First Name:</label>
    <input type="text" id="firstName" name="firstName" required><br>

    <label for="surname">Last Name:</label>
    <input type="text" id="surname" name="surname" required><br>

    <label for="phoneNumber">Phone Number:</label>
    <input type="text" id="phoneNumber" name="phoneNumber"><br>

    <input type="submit" value="Register">
</form>
<form action="${pageContext.request.contextPath}/login" method="get">
    <button type="submit">Log In</button>
</form>
</body>
</html>
