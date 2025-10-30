function abrirPopUp(enderecoDoPopUp){

    fetch(enderecoDoPopUp)
        .then(response => response.text())
        .then(data=> {
        document.getElementById('div-popup').innerHTML = data;
    })
}

function fecharPopUp(){
    document.getElementById('popup-container').style.display = 'none';
}