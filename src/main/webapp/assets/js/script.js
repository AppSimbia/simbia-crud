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

function configPopUpAdicionar(enderecoPopUpAdicionar, enderecoServletRegistro){
    document.getElementById('btnAdicionar')
        .addEventListener('click', () => chamarPopUpAdicionar(enderecoPopUpAdicionar, enderecoServletRegistro))
}

async function chamarPopUpAdicionar(enderecoPopUpAdicionar, enderecoServletRegistro){
    try{
        const response = await fetch(enderecoPopUpAdicionar)
        const htmlRecuperado = await response.text()
        const containerPopUp = document.getElementById('container-geral-popup')

        containerPopUp.innerHTML = htmlRecuperado

        document.querySelector('#container-geral-popup form').action = enderecoServletRegistro

        document.querySelector('#container-geral-popup [name="btnFechar"]')
            .addEventListener('click', () => fecharModal(containerPopUp))

        document.querySelector('#container-geral-popup section')
            .addEventListener('click', (e) => {
                if (e.target === e.currentTarget) {
                    fecharModal(containerPopUp)
                }
            })

        containerPopUp.style.display = 'flex'
        document.querySelector('#container-geral-popup section').style.display = 'flex'

    } catch (error){
        console.log('Erro ao lançar pop-up de adicionar:', error)
    }
}

function fecharModal(containerErro){
    containerErro.style.display = 'none'
}