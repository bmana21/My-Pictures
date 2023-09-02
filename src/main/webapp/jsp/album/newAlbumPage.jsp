<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Create New Album</title>
    <link rel="stylesheet" type="text/css" href="/jsp/album/newAlbumPage.css">
    <link rel="stylesheet" type="text/css" href="/General CSS/ScrollBar.css">
</head>
<body>
<div class="wrapper">
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
<script>
    function previewFiles() {
        var fileInput = document.getElementById('photos');
        var imagePreview = document.getElementById('previewPhotosContainer');

        imagePreview.innerHTML = '';

        if (fileInput.files && fileInput.files.length > 0) {
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
                        deleteA.onclick = function() {
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