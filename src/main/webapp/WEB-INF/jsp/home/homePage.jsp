<%@ page import="java.util.List" %>
<%@ page import="com.example.mypictures.entity.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>MyPictures - Homepage</title>
</head>
<body>
<h1>Welcome</h1>
<% User user = (User) session.getAttribute("user"); %>
<% if (user == null) {%>
<form action="${pageContext.request.contextPath}/login" method="get">
    <button type="submit">Log In</button>
</form>
<form action="${pageContext.request.contextPath}/register" method="get">
    <button type="submit">Sign Up</button>
</form>
<% } else { %>
<form action="${pageContext.request.contextPath}/logout" method="get">
    <button type="submit">Log Out</button>
</form>
<p><%= user.getFirstname()%> </p>
<p><%= user.getSurname()%> </p>
<% } %>
</body>
</html>
