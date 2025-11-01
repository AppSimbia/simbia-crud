package simbia.app.crud.infra.servlet.exception.operacao;

import simbia.app.crud.infra.servlet.abstractclasses.OperacoesException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Exceção lançada quando as credenciais de autenticação estão incorretas.
 *
 * Esta exceção é lançada durante o processo de login quando o email ou a senha
 * fornecidos não correspondem a nenhum registro válido no sistema.
 *
 * A mensagem de erro é gerada automaticamente com timestamp no formato
 * "[dd/MM/yyyy HH:mm:ss] - Erro de credenciais, email ou senha incorretos."
 */
public class EmailOuSenhaErradosException extends OperacoesException {
    @Override
    public String gerarMensagem() {
        return "Email ou senha incorretos";
    }
}