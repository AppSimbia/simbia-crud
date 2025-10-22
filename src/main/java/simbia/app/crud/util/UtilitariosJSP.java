package simbia.app.crud.util;

import simbia.app.crud.infra.servlet.exception.RequisicaoSemRegistrosException;
import simbia.app.crud.model.servlet.RequisicaoResposta;

import java.util.List;

/**
 * Classe utilitária para manipulação de dados em páginas JSP.
 *
 * <p>Fornece métodos auxiliares para recuperação de registros armazenados
 * em diferentes escopos (requisição e sessão).
 */
public class UtilitariosJSP {

    /**
     * Recupera registros de uma tabela específica, priorizando dados filtrados.
     *
     * <p>Este método implementa uma estratégia de busca em cascata:
     * <ol>
     *   <li><b>Primeira tentativa:</b> Busca por dados filtrados no atributo da requisição
     *       com a chave {@code tabelaAlvo + "Formatados"}</li>
     *   <li><b>Segunda tentativa:</b> Se não encontrado, busca dados originais no atributo
     *       da sessão com a chave {@code tabelaAlvo + "Registros"}</li>
     *   <li><b>Falha:</b> Se nenhum registro for encontrado em ambas as fontes,
     *       lança exceção</li>
     * </ol>
     *
     * @param <T> Tipo genérico dos registros na lista
     * @param requisicaoResposta Objeto que encapsula a requisição e resposta HTTP
     * @param tabelaAlvo Nome base da tabela (ex: "usuario", "produto", "cliente").
     *                   Será concatenado com sufixos para formar as chaves de busca
     * @return Lista tipada contendo os registros encontrados
     * @throws RequisicaoSemRegistrosException Se nenhum registro for encontrado
     *
     */
    public static <T> List<T> recuperarRegistrosDaRequisicao(
            RequisicaoResposta requisicaoResposta,
            String tabelaAlvo) throws RequisicaoSemRegistrosException {

        // Prioridade 1: Verifica se existem dados filtrados ou ordenados na requisição atual
        if (requisicaoResposta.existeAtributoNaRequisicao(tabelaAlvo + "Formatados")) {
            return (List<T>) requisicaoResposta.recuperarAtributoDaRequisicao(tabelaAlvo + "Formatados");
        }
        // Prioridade 2: Caso contrário, busca dados originais na sessão
        else if (requisicaoResposta.existeSessaoDaRequisicao(tabelaAlvo + "Registros")) {
            return (List<T>) requisicaoResposta.recuperarAtributoDaSessao(tabelaAlvo + "Registros");
        }
        // Se nenhum registro foi encontrado, lança exceção
        else {
            throw new RequisicaoSemRegistrosException();
        }
    }
}