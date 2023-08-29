<%@ page import="java.util.List" %>
<%@ page import="com.example.mypictures.entity.User" %>
<%@ page import="org.springframework.beans.factory.annotation.Autowired" %>
<%@ page import="com.example.mypictures.entity.Album" %>
<%@ page import="com.example.mypictures.service.GoogleCloudService" %>
<%@ page import="com.example.mypictures.entity.Photo" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <% User user = (User) session.getAttribute("user");
        Album album = (Album) request.getAttribute("album");
        List<Photo> photos = (List<Photo>) request.getAttribute("photos");
        GoogleCloudService googleCloudService = new GoogleCloudService();
    %>
    <title>Album - <%=album.getName()%>
    </title>
</head>
<body>
<a href="${pageContext.request.contextPath}/home" >MyPictures</a>
<h2><%=album.getName()%>
</h2>
<%--<form action="${pageContext.request.contextPath}/newalbum" method="get">--%>
<%--    <button type="submit">Create New Album</button>--%>
<%--</form>--%>
<% for (Photo photo : photos) { %>
<div class="photo-container">
    <div class="image">
        <img src="<%=googleCloudService.getPhotoURL(photo.getSaveName())%>">
    </div>
    <div class="text">
        <div class="name">
            <h4><%=photo.getName()%></h4>
        </div>
    </div>
</div>
<% } %>

</body>
</html>