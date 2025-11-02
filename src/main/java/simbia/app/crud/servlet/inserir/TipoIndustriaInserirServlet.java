package simbia.app.crud.servlet.inserir;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import simbia.app.crud.dao.TipoIndustriaDao;
import simbia.app.crud.infra.dao.abstractclasses.DaoException;
import simbia.app.crud.infra.dao.exception.errosDeOperacao.NaoHouveAlteracaoNoBancoDeDadosException;
import simbia.app.crud.infra.servlet.abstractclasses.InserirServlet;
import simbia.app.crud.infra.servlet.abstractclasses.OperacoesException;
import simbia.app.crud.model.dao.TipoIndustria;
import simbia.app.crud.model.servlet.RequisicaoResposta;

import java.io.IOException;

public class TipoIndustriaInserirServlet extends InserirServlet<TipoIndustria> {

    @Override
    protected void doPost(HttpServletRequest requisicao, HttpServletResponse resposta) throws ServletException, IOException {
        RequisicaoResposta requisicaoResposta = new RequisicaoResposta(requisicao, resposta);

        try{
            TipoIndustria registro = recuperarNovoRegistroNaRequisicao(requisicaoResposta);
            inserirRegistroNoBanco(registro);

            requisicaoResposta.despacharPara(enderecoDeRedirecionamento());

        } catch (NaoHouveAlteracaoNoBancoDeDadosException causa) {
            requisicaoResposta.adicionarAtributoNaRequisicao("mensagem", "Operação falhou! Tente novamente.");
            requisicaoResposta.adicionarAtributoNaRequisicao("popupAdicionarAberto", false);

            requisicaoResposta.redirecionarPara(enderecoDeRedirecionamentoCasoErro());

        }
    }

    @Override
    public void inserirRegistroNoBanco(TipoIndustria entidade) throws DaoException, OperacoesException {
        TipoIndustriaDao dao = new TipoIndustriaDao();

        dao.inserir(entidade);
    }

    @Override
    public TipoIndustria recuperarNovoRegistroNaRequisicao(RequisicaoResposta requisicaoResposta) {
        String nome = requisicaoResposta.recuperarParametroDaRequisicao("nome");
        String descricao = requisicaoResposta.recuperarParametroDaRequisicao("descricao");

        return new TipoIndustria(nome, descricao);
    }

    @Override
    public String enderecoDeRedirecionamento() {
        return "/tipo-industria/atualizar";
    }

    @Override
    public String enderecoDeRedirecionamentoCasoErro() {
        return "/tipoIndustria.jsp";
    }
}
