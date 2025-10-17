package simbia.app.crud.infra.servlet.abstractclasses;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import simbia.app.crud.infra.servlet.exception.RequisicaoSemRegistrosException;
import simbia.app.crud.model.servlet.RequisicaoResposta;
import simbia.app.crud.util.ValidacoesDeDados;

import java.io.IOException;

public abstract class ordenacaoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest requisicao, HttpServletResponse resposta) throws ServletException, IOException {
        RequisicaoResposta requisicaoResposta = new RequisicaoResposta(requisicao, resposta);
        try {
            String tipoDeOrdenacao = recuperarTipoDeOrdenacao(requisicaoResposta);
            requisicaoResposta.adicionarAtributoNaRequisicao(nomeDaTabela() + "Ordenado", tipoDeOrdenacao);

        } catch (RequisicaoSemRegistrosException causa){
            causa.printStackTrace();
        }
    }

    private static void ordenarRegistros(RequisicaoResposta requisicaoResposta, String tipoOrdenacao){

    }

    private static String recuperarTipoDeOrdenacao(RequisicaoResposta requisicaoResposta){
        String tipoOrdenacao = requisicaoResposta.recuperarParametroDaRequisicao("tipoOrdenacao");

        ValidacoesDeDados.validarTipoDeOrdenacao(tipoOrdenacao);

        return tipoOrdenacao;
    }

    public abstract String nomeDaTabela();
}
