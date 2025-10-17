package simbia.app.crud.util;

import simbia.app.crud.infra.servlet.exception.RequisicaoSemRegistrosException;
import simbia.app.crud.model.servlet.RequisicaoResposta;

import java.util.List;
import java.util.Map;

public class UtilitariosJSP {

    public static <T> List<T> recuperarRegistrosDaRequisicao(RequisicaoResposta requisicaoResposta, String tabelaAlvo) throws RequisicaoSemRegistrosException{
        if (requisicaoResposta.existeAtributoNaRequisicao("registrosFormatados")){
            Map<String, List<T>> dicionarioDeRegistrosFormatados = (Map<String, List<T>>) requisicaoResposta.recuperarAtributoDaRequisicao("registrosFormatados");
            return dicionarioDeRegistrosFormatados.get(tabelaAlvo);
        }else if (requisicaoResposta.existeSessaoDaRequisicao("registros")){
            Map<String, List<T>> dicionarioDeRegistrosFormatados = (Map<String, List<T>>) requisicaoResposta.recuperarAtributoDaSessao("registros");
            return dicionarioDeRegistrosFormatados.get(tabelaAlvo);
        }else{
            throw new RequisicaoSemRegistrosException();
        }
    }
}
