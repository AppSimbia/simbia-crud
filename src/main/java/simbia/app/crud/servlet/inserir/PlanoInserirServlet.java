package simbia.app.crud.servlet.inserir;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import simbia.app.crud.dao.PlanoDao;
import simbia.app.crud.infra.dao.abstractclasses.DaoException;
import simbia.app.crud.infra.dao.exception.errosDeOperacao.NaoHouveAlteracaoNoBancoDeDadosException;
import simbia.app.crud.infra.dao.exception.errosDoBancoDeDados.*;
import simbia.app.crud.infra.servlet.abstractclasses.InserirServlet;
import simbia.app.crud.infra.servlet.abstractclasses.OperacoesException;
import simbia.app.crud.model.dao.Plano;
import simbia.app.crud.model.servlet.RequisicaoResposta;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * Servlet para inserir novos planos no sistema.
 */
@WebServlet("/plano/inserir")
public class PlanoInserirServlet extends InserirServlet<Plano> {

    /**
     * Processa a requisição POST para inserir um plano.
     * Valida os dados, insere no banco e trata possíveis erros.
     */
    @Override
    protected void doPost(HttpServletRequest requisicao, HttpServletResponse resposta) throws ServletException, IOException {
        RequisicaoResposta requisicaoResposta = new RequisicaoResposta(requisicao, resposta);

        try{
            Plano registro = recuperarNovoRegistroNaRequisicao(requisicaoResposta);

            inserirRegistroNoBanco(registro);

            requisicaoResposta.redirecionarPara(enderecoDeRedirecionamento());

        } catch (NumberFormatException causa) {
            requisicaoResposta.redirecionarPara(enderecoDeRedirecionamentoCasoErro());
        } catch (NaoHouveAlteracaoNoBancoDeDadosException causa) {
            requisicaoResposta.redirecionarPara(enderecoDeRedirecionamentoCasoErro());
        } catch (ViolacaoDeObrigatoriedadeException causa) {
            requisicaoResposta.redirecionarPara(enderecoDeRedirecionamentoCasoErro());
        } catch (ViolacaoDeUnicidadeException causa) {
            requisicaoResposta.redirecionarPara(enderecoDeRedirecionamentoCasoErro());
        } catch (FalhaDeConexaoDriverInadequadoException | FalhaDeConexaoGeralException |
                 FalhaDeConexaoBancoDeDadosInexistenteException | FalhaDeConexaoQuedaRepentina |
                 FalhaDeConexaoSenhaIncorretaException causa) {
            requisicaoResposta.redirecionarPara(enderecoDeRedirecionamentoCasoErro());
        }
    }

    /**
     * Insere o plano no banco de dados usando o DAO.
     */
    @Override
    public void inserirRegistroNoBanco(Plano entidade) throws DaoException, OperacoesException {
        PlanoDao dao = new PlanoDao();
        dao.inserir(entidade);
    }

    /**
     * Recupera os dados do formulário de cadastro.
     * Retorna um objeto Plano pronto para ser inserido.
     */
    @Override
    public Plano recuperarNovoRegistroNaRequisicao(RequisicaoResposta requisicaoResposta)
            throws NumberFormatException {

        String nome = requisicaoResposta.recuperarParametroDaRequisicao("nome");
        String valorStr = requisicaoResposta.recuperarParametroDaRequisicao("valor");
        String statusStr = requisicaoResposta.recuperarParametroDaRequisicao("status");


        BigDecimal valor = null;

        if (valorStr != null && !valorStr.trim().isEmpty()) {
            valor = new BigDecimal(valorStr.trim().replace(",", "."));
        }

        boolean ativo = "ativo".equals(statusStr);

        return new Plano(valor, ativo, nome);
    }

    /**
     * Retorna o endereço para onde redirecionar após inserção bem-sucedida.
     */
    @Override
    public String enderecoDeRedirecionamento() {
        return "/plano/atualizar";
    }

    /**
     * Retorna o endereço para onde despachar em caso de erro.
     */
    @Override
    public String enderecoDeRedirecionamentoCasoErro() {
        return "/plano.jsp";
    }
}