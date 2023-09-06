<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.mypictures.entity.User" %>
<%@ page import="org.springframework.beans.factory.annotation.Autowired" %>
<%@ page import="com.example.mypictures.entity.Album" %>
<%@ page import="com.example.mypictures.service.GoogleCloudService" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>MyPictures - Homepage</title>
    <link rel="stylesheet" type="text/css" href="/jsp/home/homePage.css">
    <link rel="stylesheet" type="text/css" href="/General CSS/ScrollBar.css">
    <link rel="icon" type="image/png" href="/images/icon.png">
    <% User user = (User) session.getAttribute("user"); %>
    <% List<Album> albums = (List<Album>) request.getAttribute("albums"); %>
</head>
<body>
<div class="wrapper">
    <% if (user == null) {%>
    <nav class="header">
        <a href="${pageContext.request.contextPath}/home">MyPictures</a>
        <form action="${pageContext.request.contextPath}/login" method="get">
            <button type="submit">Log In</button>
        </form>
        <form action="${pageContext.request.contextPath}/register" method="get">
            <button type="submit">Sign Up</button>
        </form>
    </nav>
    <h1 class="welcome">Welcome</h1>
    <h2 class="description-from-type-label">
        Preserve and share your precious memories with our intuitive and user-friendly photo album application. With our
        app, you can create personalized albums and effortlessly organize and showcase your favorite photos. Whether
        you're
        a photography enthusiast or simply want to cherish special moments, our app is designed to make your photo
        management experience enjoyable and hassle-free.
        Log In or Sign Up on our page to get it's all features.
    </h2>
    <% } else { %>
    <nav class="header">
        <a href="${pageContext.request.contextPath}/home">MyPictures</a>
        <form action="${pageContext.request.contextPath}/logout" method="get">
            <button type="submit">Log Out</button>
        </form>
    </nav>
    <h1 class="welcome">Welcome, <%=user.getFirstname() + " " + user.getSurname()%>.
    </h1>
    <h2 class="description-from-type-label">
        Preserve and share your precious memories with our intuitive and user-friendly photo album application. With our
        app, you can create personalized albums and effortlessly organize and showcase your favorite photos. Whether
        you're
        a photography enthusiast or simply want to cherish special moments, our app is designed to make your photo
        management experience enjoyable and hassle-free.
    </h2>

    <div class="all-albums">
        <form action="${pageContext.request.contextPath}/newalbum" method="get">
            <div class="album-container" onclick="this.closest('form').submit();">
                <div class="albumCover">
                    <img src="/images/addNewAlbum.jpg">
                </div>
                <div class="albumName">
                    <h4>Add New Album
                    </h4>
                </div>
            </div>
        </form>
        <% for (Album album : albums) { %>

        <form action="${pageContext.request.contextPath}/album" method="get">
            <input value="<%=album.getAlbumId()%>" name="albumId" style="display: none">
            <div class="album-container" onclick="this.closest('form').submit();">
                <div class="albumCover">
                    <img src="<%=GoogleCloudService.getPhotoURL(album.getSaveName())%>">
                </div>
                <div class="albumName">
                    <h4><%=album.getName()%>
                    </h4>
                </div>
            </div>
        </form>
        <% } %>
    </div>

    <% } %>

    <footer class="footer">
        <div class="footer-content">
            <p>&copy; 2023 MyPictures. All rights reserved.</p>
        </div>
        <p>Author: Beso Managadze</p>
    </footer>
</div>
</body>
</html>