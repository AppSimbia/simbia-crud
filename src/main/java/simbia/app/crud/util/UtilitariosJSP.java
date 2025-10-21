package simbia.app.crud.util;

import simbia.app.crud.infra.servlet.exception.RequisicaoSemRegistrosException;
import simbia.app.crud.model.servlet.RequisicaoResposta;

import java.util.List;

public class UtilitariosJSP {

    public static <T> List<T> recuperarRegistrosDaRequisicao(RequisicaoResposta requisicaoResposta, String tabelaAlvo) throws RequisicaoSemRegistrosException{
        if (requisicaoResposta.existeAtributoNaRequisicao(tabelaAlvo + "Formatados")){
            return (List<T>) requisicaoResposta.recuperarAtributoDaRequisicao(tabelaAlvo + "Formatados");
        }else if (requisicaoResposta.existeSessaoDaRequisicao( tabelaAlvo + "Registros")){
            return (List<T>) requisicaoResposta.recuperarAtributoDaSessao(tabelaAlvo + "Registros");
        }else{
            throw new RequisicaoSemRegistrosException();
        }
    }
}
