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
        List<Album> albums = (List<Album>) request.getAttribute("albums");
    %>
    <title>Album - <%=album.getName()%>
    </title>
    <link rel="stylesheet" type="text/css" href="/jsp/album/albumPage.css">
</head>
<body>
<div class="wrapper">

    <nav class="header">
        <a href="${pageContext.request.contextPath}/home">MyPictures</a>
    </nav>
    <div class="main-content">
        <div class="photo-gallery">
            <% for (Photo photo : photos) { %>
            <div class="photo-container">
                <div class="photo">
                    <img src="<%=GoogleCloudService.getPhotoURL(photo.getSaveName())%>">
                </div>
                <div class="overlay"></div>
                <div class="delete">
                    <% String link = "/deletephoto?albumId="+album.getAlbumId()+"&photoId="+photo.getPhotoId(); %>
                    <a href="<%=link%>">Delete</a>
                </div>
                <div class="download"><a href="<%=GoogleCloudService.getPhotoURL(photo.getSaveName())%>"> Download </a>
                </div>
            </div>
            <% } %>

        </div>
        <div class="upload-photo">
            <form action="${pageContext.request.contextPath}/addphoto" method="post" enctype="multipart/form-data">
                <div>
                    <label>add More Photos: </label>
                    <input type="file" name="photos" multiple>
                </div>
                <input type="hidden" name="albumId" value="<%= album.getAlbumId()%>">
                <button type="submit">Upload Photos</button>
            </form>
        </div>
    </div>
    <div class="sidebar-wrapper">
        <div class="sidebar">
            <div class="all-albums">
                <form action="${pageContext.request.contextPath}/album" method="get">
                    <input value="<%=album.getAlbumId()%>" name="albumId" style="display: none">
                    <div class="active-album-container" onclick="this.closest('form').submit();">
                        <div class="albumCover">
                            <img src="<%=GoogleCloudService.getPhotoURL(album.getSaveName())%>">
                        </div>
                        <div class="albumName">
                            <h4><%=album.getName()%>
                            </h4>
                        </div>
                    </div>
                </form>
                <% for (Album album1 : albums) { %>
                <% if (album1 == album) continue; %>
                <form action="${pageContext.request.contextPath}/album" method="get">
                    <input value="<%=album1.getAlbumId()%>" name="albumId" style="display: none">
                    <div class="album-container" onclick="this.closest('form').submit();">
                        <div class="albumCover">
                            <img src="<%=GoogleCloudService.getPhotoURL(album1.getSaveName())%>">
                        </div>
                        <div class="albumName">
                            <h4><%=album1.getName()%>
                            </h4>
                        </div>
                    </div>
                </form>
                <% } %>
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
            </div>
        </div>
    </div>
    <footer class="footer">
        <div class="footer-content">
            <p>&copy; 2023 MyPictures. All rights reserved.</p>
        </div>
        <p>Author: Beso Managadze</p>
    </footer>
</div>
<script>
    window.addEventListener('scroll', function () {
        var sidebar = document.querySelector('.sidebar');
        var headerHeight = document.querySelector('.header').offsetHeight;
        var scrollTop = window.pageYOffset || document.documentElement.scrollTop;
        if (scrollTop >= headerHeight) {
            sidebar.style.marginTop = '0';
        } else {
            sidebar.style.marginTop = headerHeight - scrollTop + 'px';
        }
    });
</script>
</body>
</html>