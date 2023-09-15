var photoId;

function viewImage(imageSrc, id) {
    photoId = id;
    document.body.style.overflowX = 'hidden';
    document.body.style.overflowY = 'hidden';
    var lightbox = document.getElementById("lightbox-container");
    lightbox.style.visibility = 'visible';
    var image = document.getElementById("viewImage");
    image.src = imageSrc;
}

function collapseImage() {
    document.body.style.overflowX = 'visible';
    document.body.style.overflowY = 'visible';
    var lightbox = document.getElementById("lightbox-container");
    lightbox.style.visibility = 'hidden';
}

const boxes = document.querySelectorAll('.box');

boxes.forEach(function (box) {
    box.addEventListener('mouseover', function () {
        var next = document.getElementById("next");
        var previous = document.getElementById("previous");
        next.style.visibility = 'visible';
        previous.style.visibility = 'visible';
    });

    box.addEventListener('mouseout', function () {
        var next = document.getElementById("next");
        var previous = document.getElementById("previous");
        next.style.visibility = 'hidden';
        previous.style.visibility = 'hidden';
    });
});

function nextImage(){
    console.log(photoId);
    var image = document.getElementById("viewImage");
    photoId = document.getElementById("next_"+photoId).textContent;
    console.log(photoId);
    var link = document.getElementById("photo_"+photoId);
    image.src = link.src;
}

function prevImage(){
    console.log(photoId);
    var image = document.getElementById("viewImage");
    photoId = document.getElementById("previous_"+photoId).textContent;
    console.log(photoId);
    var link = document.getElementById("photo_"+photoId);
    image.src = link.src;
}
