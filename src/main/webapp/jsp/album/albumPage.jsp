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
    <link rel="icon" type="image/png" href="/images/icon.png">
    <link rel="stylesheet" type="text/css" href="/jsp/album/albumPage.css">
    <link rel="stylesheet" type="text/css" href="/jsp/album/viewImage.css">
    <link rel="stylesheet" type="text/css" href="/General CSS/ScrollBar.css">
</head>
<body>
<div class="wrapper">

    <nav class="header">
        <a href="${pageContext.request.contextPath}/home">MyPictures</a>
    </nav>
    <div class="main-content">
        <div class="photo-gallery">
            <% int index = 0;
                int nPhotos = photos.size();
            %>
            <% for (Photo photo : photos) { %>
            <div class="photo-container">
                <div class="photo">
                    <img id="photo_<%=photo.getPhotoId()%>"
                         src="<%=GoogleCloudService.getPhotoURL(photo.getSaveName())%>">
                    <div id="next_<%=photo.getPhotoId()%>"  style="display: none;"><%=photos.get((index+1)%nPhotos).getPhotoId()%></div>
                    <div id="previous_<%=photo.getPhotoId()%>" style="display: none;"><%=photos.get((index-1+nPhotos)%nPhotos).getPhotoId()%></div>
                </div>
                <div class="overlay"
                     onclick="viewImage('<%=GoogleCloudService.getPhotoURL(photo.getSaveName())%>', <%=photo.getPhotoId()%>)"></div>
                <div class="delete">
                    <% String link = "/deletephoto?albumId=" + album.getAlbumId() + "&photoId=" + photo.getPhotoId(); %>
                    <a href="<%=link%>">Delete</a>
                </div>
                <div class="download"><a href="<%=GoogleCloudService.getPhotoURL(photo.getSaveName())%>">
                    Download </a>
                </div>
            </div>

            <%
                    index++;
                }
            %>

        </div>
        <div class="upload-photo">
            <form action="${pageContext.request.contextPath}/addphoto" method="post" enctype="multipart/form-data">
                <div>
                    <label>add More Photos: </label>
                    <input type="file" name="photos" id="photos" multiple onchange="previewFiles()">
                </div>
                <input type="hidden" name="albumId" value="<%= album.getAlbumId()%>">
                <div id="just-previews">
                    <p>Preview of your upload photos: </p>
                    <div id="previewPhotosContainer">
                    </div>
                </div>
                <button type="submit">Upload Photos</button>
            </form>

        </div>
        <button onclick="openPopup()" class="delete-album-button"> Delete Album</button>
        <div id="deletePopUp" class="delete-popUp" onclick="closePopup()">
            <div class="delete-popUp-content">
                <form action="${pageContext.request.contextPath}/deletealbum" method="get">
                    <p>Are you sure you want to delete this album? Note that all the photos from this album will be
                        deleted, too.</p>
                    <input type="hidden" name="albumId" value="<%= album.getAlbumId()%>">
                    <div class="delete-popUp-buttons">
                        <button type="button" onclick="closePopup()">Cancel</button>
                        <button type="submit">Delete</button>
                    </div>
                </form>
            </div>
            <script src="/jsp/album/script/albumPagePopupScript.js"></script>

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
    <div id="lightbox-container">
        <div id="lightbox" class="show" onclick="collapseImage()"></div>
        <div class="viewImage-container">
            <a href="#" id="next" class="previous box" onclick="prevImage()">&#8249;</a>
            <a href="#" id="previous" class="next box" onclick="nextImage()">&#8250;</a>
            <img id="viewImage" class="box" src="/images/defaultImage.png">
        </div>
    </div>
    <footer class="footer">
        <div class="footer-content">
            <p>&copy; 2023 MyPictures. All rights reserved.</p>
        </div>
        <p>Author: Beso Managadze</p>
    </footer>
</div>
<script src="/jsp/album/script/albumPageScript.js"></script>
<script src="/jsp/album/script/albumPageImageScript.js"></script>
</body>
</html>