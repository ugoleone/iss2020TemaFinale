/*
 * modalID = id del div del modal
 * imgID = id dell'immagine da usare
 * modalImgID = id dell'immagine che sta nel modal
 * closerID = id dell'icona da premere per chiudere il modal
 */

function setModal(modalID, imgID, modalImgID, closerID) {
  var modal = document.getElementById(modalID);
  var img = document.getElementById(imgID);
  var modalImg = document.getElementById(modalImgID);

  img.onclick = function(){
    modal.style.display = "block";
    modalImg.src = this.src;
    //captionText.innerHTML = this.alt; //qualora si volesse una didascalia
  }

  //Elemento <span> da premere per chiudere il modale
  var span = document.getElementById(closerID);
  
  span.onclick = function() { 
    modal.style.display = "none";
  }
}

function openModal() {
  document.getElementById('myModal').style.display = "block";
}

function closeModal() {
  document.getElementById('myModal').style.display = "none";
}

var slideIndex = 1;
showDivs(slideIndex);

function plusDivs(n) {
  showDivs(slideIndex += n);
}

function currentDiv(n) {
  showDivs(slideIndex = n);
}

function showDivs(n) {
  var i;
  var x = document.getElementsByClassName("mySlides");
  var dots = document.getElementsByClassName("demo");
  
  if (n > x.length) {slideIndex = 1}
  if (n < 1) {slideIndex = x.length}
  for (i = 0; i < x.length; i++) {
    x[i].style.display = "none";
  }
  for (i = 0; i < dots.length; i++) {
    dots[i].className = dots[i].className.replace(" w3-opacity-off", "");
  }
  x[slideIndex-1].style.display = "block";
  dots[slideIndex-1].className += " w3-opacity-off";
}