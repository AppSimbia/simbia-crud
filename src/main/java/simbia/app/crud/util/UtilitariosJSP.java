package simbia.app.crud.util;

import simbia.app.crud.infra.servlet.exception.RequisicaoSemRegistrosException;
import simbia.app.crud.model.servlet.RequisicaoResposta;

import java.util.List;

/**
 * Classe utilitária para manipulação de dados em páginas JSP.
 *
 * Fornece métodos auxiliares para recuperação de registros armazenados
 * em diferentes escopos (requisição e sessão), priorizando dados filtrados/formatados
 * sobre dados brutos.
 */
public class UtilitariosJSP {

    /**
     * Recupera registros de uma tabela da requisição ou sessão.
     *
     * Busca os registros seguindo a ordem de prioridade:
     * 1. Primeiro tenta recuperar "tabelaAlvo + Formatados" da requisição (dados filtrados/ordenados)
     * 2. Se não encontrar, tenta recuperar "tabelaAlvo + Registros" da sessão (dados brutos)
     *
     * Valida se os registros existem antes de retornar.
     *
     * @param requisicaoResposta objeto contendo requisição e resposta HTTP
     * @param tabelaAlvo nome da tabela cujos registros devem ser recuperados (ex: "usuario", "produto")
     * @param <T> tipo genérico dos registros na lista
     * @return lista de registros encontrados
     * @throws RequisicaoSemRegistrosException se não encontrar registros ou se a lista estiver vazia
     */
    public static <T> List<T> recuperarRegistrosDaRequisicao(
            RequisicaoResposta requisicaoResposta,
            String tabelaAlvo) throws RequisicaoSemRegistrosException {
        List<T> registrosASeremExibidos = null;

        if (requisicaoResposta.existeAtributoNaRequisicao(tabelaAlvo + "Formatados")) {
            registrosASeremExibidos = (List<T>) requisicaoResposta.recuperarAtributoDaRequisicao(tabelaAlvo + "Formatados");
        }
        else if (requisicaoResposta.existeSessaoDaRequisicao(tabelaAlvo + "Registros")) {
            registrosASeremExibidos = (List<T>) requisicaoResposta.recuperarAtributoDaSessao(tabelaAlvo + "Registros");
        }

        ValidacoesDeDados.validarRegistros(registrosASeremExibidos);

        return registrosASeremExibidos;
    }
}