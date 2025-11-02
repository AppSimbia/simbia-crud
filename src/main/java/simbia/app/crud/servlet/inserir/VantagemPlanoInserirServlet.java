package simbia.app.crud.servlet.inserir;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import simbia.app.crud.dao.VantagemPlanoDao;
import simbia.app.crud.infra.dao.abstractclasses.DaoException;
import simbia.app.crud.infra.dao.exception.errosDeOperacao.NaoHouveAlteracaoNoBancoDeDadosException;
import simbia.app.crud.infra.dao.exception.errosDoBancoDeDados.*;
import simbia.app.crud.infra.servlet.abstractclasses.InserirServlet;
import simbia.app.crud.infra.servlet.abstractclasses.OperacoesException;
import simbia.app.crud.model.dao.VantagemPlano;
import simbia.app.crud.model.servlet.RequisicaoResposta;
import simbia.app.crud.util.ValidacoesDeDados;

import java.io.IOException;

@WebServlet("/vantagem-plano/adicionar")
public class VantagemPlanoInserirServlet extends InserirServlet<VantagemPlano> {
    @Override
    protected void doPost(HttpServletRequest requisicao, HttpServletResponse resposta) throws ServletException, IOException {
        RequisicaoResposta requisicaoResposta = new RequisicaoResposta(requisicao, resposta);

        try{
            VantagemPlano registro = recuperarNovoRegistroNaRequisicao(requisicaoResposta);
            inserirRegistroNoBanco(registro);

            requisicaoResposta.redirecionarPara(enderecoDeRedirecionamento());

        } catch (NumberFormatException causa){
            requisicaoResposta.redirecionarPara(enderecoDeRedirecionamentoCasoErro());

        } catch (ViolacaoDeRegistroDeChaveEstrangeiraException causa){
            requisicaoResposta.redirecionarPara(enderecoDeRedirecionamentoCasoErro());

        } catch (NaoHouveAlteracaoNoBancoDeDadosException causa) {
            requisicaoResposta.redirecionarPara(enderecoDeRedirecionamentoCasoErro());

        } catch (FalhaDeConexaoDriverInadequadoException | FalhaDeConexaoGeralException |
                 FalhaDeConexaoQuedaRepentina | FalhaDeConexaoBancoDeDadosInexistenteException |
                 FalhaDeConexaoSenhaIncorretaException causa){
            requisicaoResposta.redirecionarPara(enderecoDeRedirecionamentoCasoErro());

        }
    }

    @Override
    public void inserirRegistroNoBanco(VantagemPlano entidade) throws DaoException, OperacoesException {
        VantagemPlanoDao dao = new VantagemPlanoDao();

        dao.inserir(entidade);
    }

    @Override
    public VantagemPlano recuperarNovoRegistroNaRequisicao(RequisicaoResposta requisicaoResposta) throws NumberFormatException{
        String idPlano = requisicaoResposta.recuperarParametroDaRequisicao("id-plano");
        String idVantagem = requisicaoResposta.recuperarParametroDaRequisicao("id-vantagem");

        ValidacoesDeDados.validarId(idPlano);
        ValidacoesDeDados.validarId(idVantagem);

        return new VantagemPlano(Long.parseLong(idPlano), Long.parseLong(idVantagem));
    }

    @Override
    public String enderecoDeRedirecionamento() {
        return "/vantagem-plano/atualizar";
    }

    @Override
    public String enderecoDeRedirecionamentoCasoErro() {
        return "/vantagem-plano.jsp";
    }
}
