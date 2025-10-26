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