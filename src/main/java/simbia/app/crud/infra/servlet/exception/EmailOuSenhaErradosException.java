package simbia.app.crud.infra.servlet.exception;

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
public class EmailOuSenhaErradosException extends RuntimeException {

    private String mensagem;

    /**
     * Constrói uma nova exceção de credenciais incorretas.
     * A mensagem é gerada automaticamente com o timestamp atual.
     */
    public EmailOuSenhaErradosException() {
        this.mensagem = Util.gerarMensagem();
    }

    /**
     * Retorna a representação em string desta exceção.
     * Formato: "EmailOuSenhaIncorretosException: [timestamp] - Erro de credenciais, email ou senha incorretos."
     */
    public String toString() {
        return "EmailOuSenhaIncorretosException: " + mensagem;
    }

    /**
     * Retorna a mensagem de erro com timestamp.
     */
    public String getMensagem() {
        return mensagem;
    }

    /**
     * Classe utilitária interna para geração de mensagens de erro.
     */
    private static class Util {
        /**
         * Gera a mensagem de erro formatada com timestamp atual.
         * Formato: "[dd/MM/yyyy HH:mm:ss] - Erro de credenciais, email ou senha incorretos."
         */
        private static String gerarMensagem() {
            String dataHora = "[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "]";
            return dataHora + " - Erro de credenciais, email ou senha incorretos.";
        }
    }
}