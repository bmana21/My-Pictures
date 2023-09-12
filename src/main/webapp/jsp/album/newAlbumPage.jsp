<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Create New Album</title>
    <link rel="stylesheet" type="text/css" href="/jsp/album/newAlbumPage.css">
    <link rel="stylesheet" type="text/css" href="/General CSS/ScrollBar.css">
    <link rel="icon" type="image/png" href="/images/icon.png">
</head>
<body>
<div class="wrapper">
    <canvas id="canvas"></canvas>
    <script src="/General JS/graphScript.js"></script>
    <nav class="header">
        <a href="${pageContext.request.contextPath}/home">MyPictures</a>
    </nav>
    <div class="main-content">
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
                    <input type="text" id="name" name="name" required placeholder="Enter Album Name"><br>
                </div>
                <div>
                    <label>Album Cover: </label>
                    <input type="file" name="albumCover">
                </div>
                <div>
                    <label>Album Photos: </label>
                    <input type="file" name="photos" id="photos" accept="image/*" multiple onchange="previewFiles()">
                </div>
                <div id="previewPhotosContainer">

                </div>
                <button type="submit">Upload Photos</button>
            </form>
        </div>
    </div>
    <footer class="footer">
        <div class="footer-content">
            <p>&copy; 2023 MyPictures. All rights reserved.</p>
        </div>
        <p>Author: Beso Managadze</p>
    </footer>
</div>
<script src="/jsp/album/script/newAlbumPageScript.js"></script>

</body>
</html>