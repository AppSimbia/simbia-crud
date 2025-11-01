async function chamarErro(mensagem){
    try{
        const response = await fetch('/crud/assets/modals/erroPopUp.html')
        const htmlRecuperado = await response.text()

        const containerErro = document.getElementById('container-erro')
        containerErro.innerHTML = htmlRecuperado
        containerErro.style.display = 'block'

        document.querySelector('#container-erro h1').innerText = mensagem.split(';')[0]
        document.querySelector('#container-erro h2').innerText = mensagem.split(';')[1]

        document.querySelector('#container-erro [name="btnFechar"]')
            .addEventListener('click', () => fecharModal(containerErro))
    } catch (error){
        console.log('Erro ao lançar pop-up de erro')
    }
}

function fecharModal(containerErro){
    containerErro.style.display = 'none'
}