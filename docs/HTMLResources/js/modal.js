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