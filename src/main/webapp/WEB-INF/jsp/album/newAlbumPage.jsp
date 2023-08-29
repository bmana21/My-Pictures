<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Create New Album</title>
</head>
<body>
<a href="${pageContext.request.contextPath}/home" >MyPictures</a>
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
<form action="${pageContext.request.contextPath}/newalbum" method="post" enctype="multipart/form-data">
    <div>
        <label for="name">Album Name:</label>
        <input type="text" id="name" name="name" required><br>
    </div>
    <div>
        <label>Album Cover: </label>
        <input type="file" name="albumCover">
    </div>
    <div>
        <label>Album Photos: </label>
        <input type="file" name="photos" multiple>
    </div>
    <button type="submit">Upload Photos</button>
</form>
</body>
</html>