function viewImage(imageSrc){
    document.body.style.overflowX = 'hidden';
    document.body.style.overflowY = 'hidden';
    var lightbox = document.getElementById("lightbox-container");
    lightbox.style.visibility = 'visible';
    var image = document.getElementById("viewImage");
    image.src = imageSrc;
}

function collapseImage(){
    document.body.style.overflowX = 'visible';
    document.body.style.overflowY = 'visible';
    var lightbox = document.getElementById("lightbox-container");
    lightbox.style.visibility = 'hidden';
}