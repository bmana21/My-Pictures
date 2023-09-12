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