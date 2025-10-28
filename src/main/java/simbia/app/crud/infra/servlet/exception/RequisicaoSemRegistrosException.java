package simbia.app.crud.infra.servlet.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Exceção lançada quando a requisição não possui registros para serem exibidos.
 *
 * Esta exceção é lançada quando se tenta processar ou exibir uma lista de registros
 * que está vazia ou nula, indicando que não há dados disponíveis para apresentar
 * ao usuário.
 *
 * A mensagem de erro é gerada automaticamente com timestamp no formato
 * "[dd/MM/yyyy HH:mm:ss] - Erro de obrigatoriedade, requisicao não possui registros a serem mostrados."
 */
public class RequisicaoSemRegistrosException extends RuntimeException {

    private String mensagem;

    /**
     * Constrói uma nova exceção de requisição sem registros.
     * A mensagem é gerada automaticamente com o timestamp atual.
     */
    public RequisicaoSemRegistrosException() {
        this.mensagem = Util.gerarMensagem();
    }

    /**
     * Retorna a representação em string desta exceção.
     * Formato: "RequisicaoSemRegistrosException: [timestamp] - Erro de obrigatoriedade, requisicao não possui registros a serem mostrados."
     */
    public String toString() {
        return "RequisicaoSemRegistrosException: " + mensagem;
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
         * Formato: "[dd/MM/yyyy HH:mm:ss] - Erro de obrigatoriedade, requisicao não possui registros a serem mostrados."
         */
        private static String gerarMensagem() {
            String dataHora = "[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "]";
            return dataHora + " - Erro de obrigatoriedade, requisicao nao possui registros a serem mostrados.";
        }
    }
}