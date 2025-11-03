package simbia.app.crud.infra.servlet.exception.validacaoDeDados;

import simbia.app.crud.infra.servlet.abstractclasses.ValidacaoDeDadosException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Exceção lançada quando o email fornecido não está no padrão de formatação esperado.
 *
 * Esta exceção é lançada durante a validação de entrada quando o email não corresponde
 * ao formato válido (ex: ausência de @, domínio inválido, etc).
 *
 * A mensagem de erro é gerada automaticamente com timestamp no formato
 * "[dd/MM/yyyy HH:mm:ss] - Erro de credenciais, email fora do padrao de formatacao"
 */
public class PadraoEmailErradoException extends ValidacaoDeDadosException {

    @Override
    public String gerarMensagem() {
        return "Padrão email inválido, por favor digite um email válido";
    }
}