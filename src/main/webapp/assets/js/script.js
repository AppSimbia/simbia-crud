async function chamarErro(mensagem){
    try{
        const response = await fetch('./assets/modals/erroPopUp.html')
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

function configPopUpEditar(enderecoPopUpAdicionar, enderecoServletRegistro, tabela){
    document.getElementsByName('editar')
        .forEach( btn =>{
            btn.addEventListener('click', () => chamarPopUpEditar(enderecoPopUpAdicionar, enderecoServletRegistro, btn.value, tabela))
        })
}

function configPopUpDeletar(enderecoServletDeletar){
    document.getElementsByName('apagar')
        .forEach( btn =>{
            btn.addEventListener('click', () => chamarPopUpDeletar(enderecoServletDeletar, btn.value))
        })
}

/**
 * Exibe erros de validação nos campos do popup
 */
function exibirErrosValidacao(errosJSON) {
    if (!errosJSON) return;

    try {
        const erros = JSON.parse(errosJSON);

        // Limpa todos os erros anteriores
        document.querySelectorAll('.pErro').forEach(p => {
            p.textContent = '';
            p.style.display = 'none';
        });

        // Exibe novos erros
        for (const [campo, mensagem] of Object.entries(erros)) {
            // Tenta encontrar o elemento de erro
            const seletores = [
                `[name="erro-${campo}-padrao"]`,
                `[name="erro-${campo}"]`,
                `[data-erro="${campo}"]`
            ];

            let elemento = null;
            for (const seletor of seletores) {
                elemento = document.querySelector(seletor);
                if (elemento) break;
            }

            if (elemento) {
                elemento.textContent = mensagem;
                elemento.style.display = 'block';
                elemento.style.color = 'red';

                // Destaca o input com erro
                const input = document.querySelector(`[name="${campo}"]`);
                if (input) {
                    input.style.border = '2px solid red';

                    // Remove destaque ao corrigir
                    input.addEventListener('input', function() {
                        this.style.border = '';
                        if (elemento) {
                            elemento.textContent = '';
                            elemento.style.display = 'none';
                        }
                    }, { once: true });
                }
            }
        }
    } catch (e) {
        console.error('Erro ao processar validações:', e);
    }
}

/**
 * Preenche campos do formulário com dados anteriores
 */
function preencherCamposFormulario(dados, separador = ';') {
    if (!dados || dados === 'null') return;

    const valores = dados.split(separador);
    const inputs = document.querySelectorAll('#container-geral-popup input:not([type="radio"]), #container-geral-popup textarea');

    inputs.forEach((input, index) => {
        if (valores[index] && valores[index] !== 'null' && valores[index].trim() !== '') {
            input.value = valores[index];
        }
    });
}

/**
 * Limpa todos os erros do formulário
 */
function limparErrosFormulario() {
    document.querySelectorAll('.pErro').forEach(p => {
        p.textContent = '';
        p.style.display = 'none';
    });

    document.querySelectorAll('input, textarea').forEach(input => {
        input.style.border = '';
    });
}
async function chamarPopUpEditar(enderecoPopUpEditar, enderecoServletEditar, info, tabela){
    try{
        const response = await fetch(enderecoPopUpEditar)
        const htmlRecuperado = await response.text()
        const containerPopUp = document.getElementById('container-geral-popup')

        containerPopUp.innerHTML = htmlRecuperado

        document.querySelector('#container-geral-popup form').action = enderecoServletEditar

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

        const btnEnviar = document.getElementsByName('btnSalvar')[0]
        const form = document.querySelector('#container-geral-popup form')

        btnEnviar.type = 'button'

        let confirmado = false

        btnEnviar.addEventListener('click', (e) => {
            e.preventDefault()

            if (!confirmado) {
                confirmado = true
                btnEnviar.innerText = 'Tem certeza?'
                btnEnviar.style.background = '#FFCF40'
                btnEnviar.style.boxShadow = '0 0 70px rgba(255,207,64,0.27)'
            } else {
                form.submit()
            }
        })

        if (tabela == 'administrador') {
            const informacoes = info.split(';')

            document.querySelector('#container-geral-popup h2').innerText = 'Alterar ' + informacoes[2]
            document.querySelector('#display-fechar-add-admin p').innerText = "ID:" + informacoes[0]
            document.getElementsByName("nome")[0].value = informacoes[2]
            document.getElementsByName("email")[0].value = informacoes[1]
            document.getElementsByName("id")[0].value = informacoes[0]

        } else if (tabela == 'plano'){
            const informacoes = info.split(';')

            document.querySelector('#container-geral-popup h2').innerText = 'Alterar ' + informacoes[1]
            document.querySelector('#display-fechar-add-admin p').innerText = "ID:" + informacoes[0]
            document.getElementsByName("nome")[0].value = informacoes[1]
            document.getElementsByName("valor")[0].value = informacoes[2]
            document.getElementsByName("id")[0].value = informacoes[0]

            if (informacoes[3] == 'true'){
                document.querySelector('[value="ativo"]').checked = true
            }else{
                document.querySelector('[value="inativo"]').checked = true
            }
        } else if (tabela == 'vantagemplano'){
            const informacoes = info.split(';')

            console.log(informacoes)

            document.querySelector('#display-fechar-add-admin p').innerText = "ID:" + informacoes[0]
            document.querySelector('#id-plano').value = informacoes[2]
            document.querySelector("#id-vantagem").value = informacoes[1]
            document.getElementsByName("id")[0].value = informacoes[0]

            console.log(document.querySelector('#id-plano'))
            console.log(document.querySelector('#id-vantagem'))

        } else {
            const informacoes = info.split(';')

            document.querySelector('#display-fechar-add-admin p').innerText = "ID:" + informacoes[0]
            document.getElementsByName("nome")[0].value = informacoes[1]
            document.getElementsByTagName('textarea')[0].value = informacoes[2]
            document.getElementsByName("id")[0].value = informacoes[0]
        }

    } catch (error){
        console.log('Erro ao lançar pop-up de adicionar:', error)
    }
}

async function chamarPopUpDeletar(enderecoServletDeletar, info){
    try{
        const response = await fetch('./assets/modals/popup-confirmacao-deletar.html')
        const htmlRecuperado = await response.text()
        const containerPopUp = document.getElementById('container-geral-popup')

        containerPopUp.innerHTML = htmlRecuperado
        document.querySelector('#container-geral-popup form').action = enderecoServletDeletar

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

        document.querySelector('#container-geral-popup section form button').value = info
        document.querySelector('span').innerText = info

    } catch (error){
        console.log('Erro ao lançar pop-up de deletar:', error)
    }
}

function fecharModal(containerErro){
    containerErro.style.display = 'none'
}

async function mostrarStatus(tipo) {
    try {
        const arquivo = tipo === 'sucesso' ? 'status-sucesso.html' : 'status-erro.html';
        const response = await fetch(`./assets/modals/${arquivo}`);
        const htmlRecuperado = await response.text();

        const containerStatus = document.getElementById('container-status');
        containerStatus.innerHTML = htmlRecuperado;
        containerStatus.style.display = 'flex';

        document.querySelector('#container-status [name="btnFechar"]')
            .addEventListener('click', () => fecharModal(containerStatus));

        setTimeout(() => {
            fecharModal(containerStatus);
        }, 5000);

    } catch (error) {
        console.log('Erro ao mostrar status:', error);
    }
}

async function chamarPopUpAdicionar(enderecoPopUpAdicionar, enderecoServletRegistro){
    try{
        const response = await fetch(enderecoPopUpAdicionar)
        const htmlRecuperado = await response.text()
        const containerPopUp = document.getElementById('container-geral-popup')

        containerPopUp.innerHTML = htmlRecuperado
        document.querySelector('#container-geral-popup form').action = enderecoServletRegistro

        document.querySelector('#container-geral-popup [name="btnFechar"]')
            .addEventListener('click', () => {
                limparErrosFormulario(); // Limpa erros ao fechar
                fecharModal(containerPopUp);
            })

        document.querySelector('#container-geral-popup section')
            .addEventListener('click', (e) => {
                if (e.target === e.currentTarget) {
                    limparErrosFormulario();
                    fecharModal(containerPopUp)
                }
            })

        containerPopUp.style.display = 'flex'
        document.querySelector('#container-geral-popup section').style.display = 'flex'

        return Promise.resolve(); // Retorna promise para aguardar

    } catch (error){
        console.log('Erro ao lançar pop-up de adicionar:', error)
        return Promise.reject(error);
    }
}