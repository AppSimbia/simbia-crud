package simbia.app.crud.infra.servlet.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Exceção lançada quando a requisição não possui o atributo 'tipoOrdenacao'.
 *
 * Esta exceção é lançada quando se tenta ordenar registros mas o parâmetro ou
 * atributo 'tipoOrdenacao' está ausente na requisição, impossibilitando determinar
 * o critério de ordenação desejado.
 *
 * A mensagem de erro é gerada automaticamente com timestamp no formato
 * "[dd/MM/yyyy HH:mm:ss] - Erro de obrigatoriedade, requisicao não possui atributo 'tipoOrdenacao'."
 */
public class RequisicaoSemTipoOrdenacaoException extends RuntimeException {

    private String mensagem;

    /**
     * Constrói uma nova exceção de requisição sem tipo de ordenação.
     * A mensagem é gerada automaticamente com o timestamp atual.
     */
    public RequisicaoSemTipoOrdenacaoException() {
        this.mensagem = Util.gerarMensagem();
    }

    /**
     * Retorna a representação em string desta exceção.
     * Formato: "RequisicaoSeTipoOrdenacaoException: [timestamp] - Erro de obrigatoriedade, requisicao não possui atributo 'tipoOrdenacao'."
     */
    public String toString() {
        return "RequisicaoSeTipoOrdenacaoException: " + mensagem;
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
         * Formato: "[dd/MM/yyyy HH:mm:ss] - Erro de obrigatoriedade, requisicao não possui atributo 'tipoOrdenacao'."
         */
        private static String gerarMensagem() {
            String dataHora = "[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "]";
            return dataHora + " - Erro de obrigatoriedade, requisicao nao possui atributo 'tipoOrdenacao'.";
        }
    }
}