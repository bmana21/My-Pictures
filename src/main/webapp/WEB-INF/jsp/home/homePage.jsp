<%@ page import="java.util.List" %>
<%@ page import="com.example.mypictures.entity.User" %>
<%@ page import="org.springframework.beans.factory.annotation.Autowired" %>
<%@ page import="com.example.mypictures.entity.Album" %>
<%@ page import="com.example.mypictures.service.GoogleCloudService" %>
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
<% List<Album> albums = (List<Album>) request.getAttribute("albums"); %>
<%
    GoogleCloudService googleCloudService = new GoogleCloudService();
%>
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
<form action="${pageContext.request.contextPath}/newalbum" method="get">
    <button type="submit">Create New Album</button>
</form>
<% for (Album album : albums) { %>
<form action="${pageContext.request.contextPath}/album" method="get">
    <input value="<%=album.getAlbumId()%>" name="albumId" style="display: none">
    <div class="album-container" onclick="this.closest('form').submit();">
        <div class="image">
            <img src="<%=googleCloudService.getPhotoURL(album.getSaveName())%>">
        </div>
        <div class="text">
            <div class="name">
                <h4><%=album.getName()%>
                </h4>
            </div>
        </div>
    </div>
</form>
<% } %>
<% } %>
</body>
</html>