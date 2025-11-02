package simbia.app.crud.servlet.inserir;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import simbia.app.crud.dao.TipoIndustriaDao;
import simbia.app.crud.dao.VantagemDao;
import simbia.app.crud.infra.dao.abstractclasses.DaoException;
import simbia.app.crud.infra.dao.exception.errosDeOperacao.NaoHouveAlteracaoNoBancoDeDadosException;
import simbia.app.crud.infra.dao.exception.errosDoBancoDeDados.*;
import simbia.app.crud.infra.servlet.abstractclasses.InserirServlet;
import simbia.app.crud.infra.servlet.abstractclasses.OperacoesException;
import simbia.app.crud.infra.servlet.exception.validacaoDeDados.PadraoDescricaoErradoException;
import simbia.app.crud.infra.servlet.exception.validacaoDeDados.PadraoEmailErradoException;
import simbia.app.crud.infra.servlet.exception.validacaoDeDados.PadraoNomeErradoException;
import simbia.app.crud.model.dao.TipoIndustria;
import simbia.app.crud.model.dao.Vantagem;
import simbia.app.crud.model.servlet.RequisicaoResposta;
import simbia.app.crud.util.ValidacoesDeDados;

import java.io.IOException;

/**
 * Servlet para inserir novas vantagens no sistema.
 */
@WebServlet("/vantagem/inserir")
public class VantagemInserirServlet extends InserirServlet<Vantagem> {

    @Override
    protected void doPost(HttpServletRequest requisicao, HttpServletResponse resposta) throws ServletException, IOException {
        RequisicaoResposta requisicaoResposta = new RequisicaoResposta(requisicao, resposta);

        try{
            Vantagem registro = recuperarNovoRegistroNaRequisicao(requisicaoResposta);
            inserirRegistroNoBanco(registro);
            requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("status", true);

            requisicaoResposta.despacharPara(enderecoDeRedirecionamento());

        } catch (NaoHouveAlteracaoNoBancoDeDadosException causa) {
            requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("status", false);
            requisicaoResposta.redirecionarPara(enderecoDeRedirecionamentoCasoErro());

        } catch (PadraoDescricaoErradoException causa){
            requisicaoResposta.redirecionarPara(enderecoDeRedirecionamentoCasoErro());

        } catch (PadraoNomeErradoException causa){
            requisicaoResposta.redirecionarPara(enderecoDeRedirecionamentoCasoErro());

        } catch(ViolacaoDeUnicidadeException causa){
            requisicaoResposta.redirecionarPara(enderecoDeRedirecionamentoCasoErro());

        } catch(FalhaDeConexaoDriverInadequadoException | FalhaDeConexaoGeralException |
                 FalhaDeConexaoBancoDeDadosInexistenteException | FalhaDeConexaoQuedaRepentina |
                 FalhaDeConexaoSenhaIncorretaException causa){
            requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("status", false);
            requisicaoResposta.redirecionarPara(enderecoDeRedirecionamentoCasoErro());

        }
    }

    @Override
    public void inserirRegistroNoBanco(Vantagem entidade) throws DaoException, OperacoesException {
        VantagemDao dao = new VantagemDao();

        dao.inserir(entidade);
    }

    @Override
    public Vantagem recuperarNovoRegistroNaRequisicao(RequisicaoResposta requisicaoResposta)
            throws PadraoNomeErradoException, PadraoEmailErradoException {
        String nome = requisicaoResposta.recuperarParametroDaRequisicao("nome");
        String descricao = requisicaoResposta.recuperarParametroDaRequisicao("descricao");

        ValidacoesDeDados.validarDescricao("descricao");
        ValidacoesDeDados.validarNome("nome");

        return new Vantagem(nome, descricao);
    }

    @Override
    public String enderecoDeRedirecionamento() {
        return "/vantagem/atualizar";
    }

    @Override
    public String enderecoDeRedirecionamentoCasoErro() {
        return "/vantagem.jsp";
    }
}