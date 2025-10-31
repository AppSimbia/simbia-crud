function adicionarListenerPopUps(enderecoPopUpAdicionar, enderecoPopUpAtualizar, tabela) {
    const btnAdicionar = document.getElementById('btnAdicionar');
    const btnsAtualizar = document.getElementsByName('editar');

    if (btnAdicionar) {
        btnAdicionar.addEventListener('click', () => abrirPopUp(enderecoPopUpAdicionar));
    }
    if (tabela == 'administrador'){
        if (btnsAtualizar.length > 0) {
            btnsAtualizar.forEach(btn => {
                btn.addEventListener('click', () => abrirPopUpAtualizarAdmin(enderecoPopUpAtualizar, btn.value));
            });
        }
    }else if(tabela == 'plano'){
        if (btnsAtualizar.length > 0) {
            btnsAtualizar.forEach(btn => {
                btn.addEventListener('click', () => abrirPopUpAtualizarPlano(enderecoPopUpAtualizar, btn.value));
            });
        }
    }else{
        if (btnsAtualizar.length > 0) {
            btnsAtualizar.forEach(btn => {
                btn.addEventListener('click', () => abrirPopUpAtualizarDescricao(enderecoPopUpAtualizar, btn.value));
            });
        }
    }

}

function abrirPopUp(enderecoPopUpAdicionar){
    fetch(enderecoPopUpAdicionar)
        .then(response => {
            if (!response.ok) throw new Error('Erro ao carregar pop-up');
            return response.text();
        })
        .then(data => {
            const container = document.getElementById('popup-container');
            container.innerHTML = data;
            container.style.display = 'block';
            configurarFechamentoPopUp()
})
}

function abrirPopUpAtualizarAdmin(enderecoDoPopUp, info) {
    fetch(enderecoDoPopUp)
        .then(response => {
            if (!response.ok) throw new Error('Erro ao carregar pop-up');
            return response.text();
        })
        .then(data => {
            const container = document.getElementById('popup-container');
            container.innerHTML = data;
            container.style.display = 'block';

            const arrayInfo = info.split(';');
            const id = arrayInfo[0];
            const email = arrayInfo[1];
            const nome = arrayInfo[2];

            const inputNome = document.getElementsByName('nome')[0];
            const inputEmail = document.getElementsByName('email')[0];

            if (inputNome) inputNome.value = nome;
            if (inputEmail) inputEmail.value = email;

            const titulo = document.querySelector('#display-fechar-add-admin h2');
            const subtitulo = document.querySelector('#display-fechar-add-admin p');

            if (titulo) titulo.innerHTML = "Alterar " + nome;
            if (subtitulo) subtitulo.innerHTML = "ID: " + id;

            configurarFechamentoPopUp();
        })
        .catch(error => {
            console.error('Erro:', error);
            alert('Erro ao carregar o pop-up de atualização');
        });
}

function abrirPopUpAtualizarPlano(enderecoDoPopUp, info) {
    fetch(enderecoDoPopUp)
        .then(response => {
            if (!response.ok) throw new Error('Erro ao carregar pop-up');
            return response.text();
        })
        .then(data => {
            const container = document.getElementById('popup-container');
            container.innerHTML = data;
            container.style.display = 'block';

            const arrayInfo = info.split(';');
            const id = arrayInfo[0];
            const nome = arrayInfo[1];
            const valor = arrayInfo[2];
            const ativo = arrayInfo[3]

            const inputNome = document.getElementsByName('nome')[0];
            const inputValor = document.getElementsByName('valor')[0];
            const radioAtivo = document.querySelector('[value="ativo"]')
            const radioInativo = document.querySelector('[value="inativo"]')

            if (inputNome) inputNome.value = nome;
            if (inputValor) inputValor.value = valor;
            if (radioAtivo && ativo == 'true') radioAtivo.checked = true
            if (radioInativo && ativo == 'false') radioInativo.checked = true


            const titulo = document.querySelector('#display-fechar-add-admin h2');
            const subtitulo = document.querySelector('#display-fechar-add-admin p');

            if (titulo) titulo.innerHTML = "Alterar " + nome;
            if (subtitulo) subtitulo.innerHTML = "ID: " + id;

            configurarFechamentoPopUp();
        })
        .catch(error => {
            console.error('Erro:', error);
            alert('Erro ao carregar o pop-up de atualização');
        });
}

function abrirPopUpAtualizarDescricao(enderecoDoPopUp, info) {
    fetch(enderecoDoPopUp)
        .then(response => {
            if (!response.ok) throw new Error('Erro ao carregar pop-up');
            return response.text();
        })
        .then(data => {
            const container = document.getElementById('popup-container');
            container.innerHTML = data;
            container.style.display = 'block';

            const arrayInfo = info.split(';');
            const id = arrayInfo[0];
            const nome = arrayInfo[1];
            const descricao = arrayInfo[2];

            const inputNome = document.getElementsByName('nome')[0];
            const textAreaDescricao = document.getElementsByName('descricao')[0];

            if (inputNome) inputNome.value = nome;
            if (textAreaDescricao) textAreaDescricao.value = descricao

            const titulo = document.querySelector('#display-fechar-add-admin h2');
            const subtitulo = document.querySelector('#display-fechar-add-admin p');

            if (titulo) titulo.innerHTML = "Alterar " + nome;
            if (subtitulo) subtitulo.innerHTML = "ID: " + id;

            configurarFechamentoPopUp();
        })
        .catch(error => {
            console.error('Erro:', error);
            alert('Erro ao carregar o pop-up de atualização');
        });
}

function configurarFechamentoPopUp() {
    const btnFechar = document.querySelector('[name="btnFechar"]');
    const containerGeral = document.getElementById("container-geral-popup");

    if (btnFechar) {
        btnFechar.addEventListener('click', fecharPopUp);
    }

    if (containerGeral) {
        containerGeral.addEventListener('click', (e) => {
            if (e.target === containerGeral) {
                fecharPopUp();
            }
        });
    }
}

function fecharPopUp() {
    const container = document.getElementById('popup-container');
    container.style.display = 'none';
    container.innerHTML = '';
}