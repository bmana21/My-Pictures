<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Create New Album</title>
    <link rel="stylesheet" type="text/css" href="/jsp/album/newAlbumPage.css">
</head>
<body>
<div class="wrapper">
    <nav class="header">
        <a href="${pageContext.request.contextPath}/home">MyPictures</a>
    </nav>
    <div class="new-album-container">
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
        <form action="${pageContext.request.contextPath}/newalbum" method="post" enctype="multipart/form-data">
            <div>
                <label for="name">Album Name:</label>
                <input type="text" id="name" name="name" required placeholder="Enter Album Name"><br>
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