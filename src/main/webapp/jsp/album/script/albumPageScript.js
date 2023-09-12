
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