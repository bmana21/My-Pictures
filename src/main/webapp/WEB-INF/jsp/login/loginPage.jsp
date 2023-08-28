<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>MyPictures - Log In</title>
</head>
<body>
<h1>Log In</h1>
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

<form action="${pageContext.request.contextPath}/login" method="post">
    <label for="usernameOrEmail">Username Or Email:</label>
    <input type="text" id="usernameOrEmail" name="usernameOrEmail" required><br>

    <label for="password">Password:</label>
    <input type="password" id="password" name="password" required><br>


    <input type="submit" value="Log In">
</form>
</body>
</html>
