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
                `[name="erro-${campo}-repetida"]`,
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