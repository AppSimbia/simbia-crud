package simbia.app.crud.infra.servlet.abstractclasses;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import simbia.app.crud.infra.servlet.exception.RequisicaoSemRegistrosException;
import simbia.app.crud.model.servlet.RequisicaoResposta;
import simbia.app.crud.util.ValidacoesDeDados;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class FiltroServlet<T> extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest requisicao, HttpServletResponse resposta) throws ServletException, IOException {
        RequisicaoResposta requisicaoResposta = new RequisicaoResposta(requisicao, resposta);

        try{
            filtrarRegitrosDaRequisicao(requisicaoResposta);
            requisicaoResposta.despacharPara(enderecoDeDespache());

        } catch (RequisicaoSemRegistrosException causa){
            requisicaoResposta.redirecionarPara(enderecoDeDespacheCasoErro());
        }
    }

    private void filtrarRegitrosDaRequisicao(RequisicaoResposta requisicaoResposta) throws RequisicaoSemRegistrosException{
        List<T> registros = (List<T>) requisicaoResposta.recuperarAtributoDaSessao(nomeDaTabela() + "Registros");
        List<T> registrosFiltrados = new ArrayList<>();

        ValidacoesDeDados.validarRegistros(registros);

        String regexFiltro = gerarRegexDeFiltroDaRequisicao(requisicaoResposta);

        for (T entidade : registros){
            if (entidadeCorrepondeAoFiltro(regexFiltro, entidade)){
                registrosFiltrados.add(entidade);
            }
        }

        requisicaoResposta.adicionarAtributoNaRequisicao(nomeDaTabela() + "Formatados", registrosFiltrados);
    }

    private String gerarRegexDeFiltroDaRequisicao(RequisicaoResposta requisicaoResposta){
        String filtro = requisicaoResposta.recuperarParametroDaRequisicao("filtro");
        String regexDeFiltro = ".*";

        if ((filtro != null && !filtro.trim().isEmpty())) {
            regexDeFiltro = ".*" + filtro.toLowerCase() + ".*";
        }

        return regexDeFiltro;
    }

    public abstract boolean entidadeCorrepondeAoFiltro(String regexFiltro, T entidade);

    public abstract String nomeDaTabela();

    public abstract String enderecoDeDespache();

    public abstract String enderecoDeDespacheCasoErro();

}
