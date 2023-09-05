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
    <link rel="stylesheet" type="text/css" href="/General CSS/ScrollBar.css">
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
                    <% String link = "/deletephoto?albumId=" + album.getAlbumId() + "&photoId=" + photo.getPhotoId(); %>
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
        <div id="deletePopUp" class="delete-popUp">
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
            <script>
                function openPopup() {
                    var modal = document.getElementById("deletePopUp");
                    modal.style.display = "block";
                }

                function closePopup() {
                    var modal = document.getElementById("deletePopUp");
                    modal.style.display = "none";
                }
            </script>

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
    var previews = document.getElementById("just-previews");
    previews.style.display = 'none';
    function previewFiles() {
        var fileInput = document.getElementById('photos');
        var imagePreview = document.getElementById('previewPhotosContainer');


        imagePreview.innerHTML = '';
        if (fileInput.files && fileInput.files.length > 0) {
            previews.style.display = 'block';
            for (var i = 0; i < fileInput.files.length; i++) {
                var reader = new FileReader();
                reader.onload = (function (index) {
                    return function (e) {
                        var container = document.createElement('div');
                        container.className = 'photoContainer';
                        var photo = document.createElement('img');
                        photo.src = e.target.result;
                        photo.className = 'preview-photo';
                        var overlay = document.createElement('div');
                        overlay.className = 'overlay';
                        var deleteButton = document.createElement('div');
                        deleteButton.className = 'delete';
                        var deleteA = document.createElement('a');
                        deleteA.textContent = 'Delete';
                        deleteA.href = 'javascript:void(0)';
                        deleteA.onclick = function () {
                            removePhoto(index);
                        };
                        container.appendChild(photo);
                        container.appendChild(overlay);
                        deleteButton.appendChild(deleteA);
                        container.appendChild(deleteButton);


                        imagePreview.appendChild(container);
                    };
                })(i);
                reader.readAsDataURL(fileInput.files[i]);
            }
        }
        else previews.style.display = 'none';
    }

    function removePhoto(index) {
        const dt = new DataTransfer();
        const input = document.getElementById('photos');
        const {files} = input;

        for (let i = 0; i < files.length; i++) {
            const file = files[i];
            if (index !== i) dt.items.add(file);
        }

        input.files = dt.files;
        previewFiles();
    }
</script>
</body>
</html>