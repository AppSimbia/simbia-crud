package simbia.app.crud.infra.servlet.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Exceção lançada quando um usuário tenta acessar recursos sem estar autenticado.
 *
 * Esta exceção é lançada quando se tenta acessar páginas ou funcionalidades restritas
 * sem possuir uma sessão válida de autenticação, indicando uma tentativa de acesso
 * não autorizado aos recursos do sistema.
 *
 * A mensagem de erro é gerada automaticamente com timestamp no formato
 * "[dd/MM/yyyy HH:mm:ss] - Tentativa de acessar as tabelas sem devida autenticacao."
 */
public class UsuarioNaoAutenticadoException extends RuntimeException {

    private String mensagem;

    /**
     * Constrói uma nova exceção de usuário não autenticado.
     * A mensagem é gerada automaticamente com o timestamp atual.
     */
    public UsuarioNaoAutenticadoException() {
        this.mensagem = Util.gerarMensagem();
    }

    /**
     * Retorna a representação em string desta exceção.
     * Formato: "UsuarioNaoAutenticadoException: [timestamp] - Tentativa de acessar as tabelas sem devida autenticacao."
     */
    public String toString() {
        return "UsuarioNaoAutenticadoException: " + mensagem;
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
         * Formato: "[dd/MM/yyyy HH:mm:ss] - Tentativa de acessar as tabelas sem devida autenticacao."
         */
        private static String gerarMensagem() {
            String dataHora = "[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "]";
            return dataHora + " - Tentativa de acessar as tabelas sem devida autenticacao.";
        }
    }
}