package simbia.app.crud.servlet.inserir;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import simbia.app.crud.dao.CategoriaProdutoDao;
import simbia.app.crud.infra.dao.abstractclasses.DaoException;
import simbia.app.crud.infra.dao.exception.errosDeOperacao.NaoHouveAlteracaoNoBancoDeDadosException;
import simbia.app.crud.infra.dao.exception.errosDoBancoDeDados.*;
import simbia.app.crud.infra.servlet.abstractclasses.InserirServlet;
import simbia.app.crud.infra.servlet.abstractclasses.OperacoesException;
import simbia.app.crud.model.dao.CategoriaProduto;
import simbia.app.crud.model.servlet.RequisicaoResposta;

import java.io.IOException;

/**
 * Servlet para inserir novas categorias de produto no sistema.
 */
@WebServlet("/categoria-produto/inserir")
public class CategoriaProdutoInserirServlet extends InserirServlet<CategoriaProduto> {

    /**
     * Processa a requisição POST para inserir uma categoria de produto.
     * Valida os dados, insere no banco e trata possíveis erros.
     */
    @Override
    protected void doPost(HttpServletRequest requisicao, HttpServletResponse resposta) throws ServletException, IOException {
        RequisicaoResposta requisicaoResposta = new RequisicaoResposta(requisicao, resposta);

        try{
            CategoriaProduto registro = recuperarNovoRegistroNaRequisicao(requisicaoResposta);
            inserirRegistroNoBanco(registro);

            requisicaoResposta.despacharPara(enderecoDeRedirecionamento());

        } catch (NaoHouveAlteracaoNoBancoDeDadosException causa) {
        requisicaoResposta.redirecionarPara(enderecoDeRedirecionamentoCasoErro());
        } catch (ViolacaoDeObrigatoriedadeException causa) {
        requisicaoResposta.redirecionarPara(enderecoDeRedirecionamentoCasoErro());
        } catch (ViolacaoDeUnicidadeException causa) {
        requisicaoResposta.redirecionarPara(enderecoDeRedirecionamentoCasoErro());
        } catch (FalhaDeConexaoDriverInadequadoException | FalhaDeConexaoGeralException | FalhaDeConexaoBancoDeDadosInexistenteException
                 | FalhaDeConexaoQuedaRepentina | FalhaDeConexaoSenhaIncorretaException causa) {
            requisicaoResposta.redirecionarPara(enderecoDeRedirecionamentoCasoErro());
    }
    }

    /**
     * Insere a categoria no banco de dados usando o DAO.
     */
    @Override
    public void inserirRegistroNoBanco(CategoriaProduto entidade) throws DaoException, OperacoesException {
        CategoriaProdutoDao dao = new CategoriaProdutoDao();
        dao.inserir(entidade);
    }

    /**
     * Recupera os dados do formulário de cadastro.
     * Retorna um objeto CategoriaProduto pronto para ser inserido.
     */
    @Override
    public CategoriaProduto recuperarNovoRegistroNaRequisicao(RequisicaoResposta requisicaoResposta) {
        String nome = requisicaoResposta.recuperarParametroDaRequisicao("nome");
        String descricao = requisicaoResposta.recuperarParametroDaRequisicao("descricao");

        return new CategoriaProduto(nome, descricao);
    }

    /**
     * Retorna o endereço para onde redirecionar após inserção bem-sucedida.
     */
    @Override
    public String enderecoDeRedirecionamento() {
        return "/categoria-produto/atualizar";
    }

    /**
     * Retorna o endereço para onde despachar em caso de erro.
     */
    @Override
    public String enderecoDeRedirecionamentoCasoErro() {
        return "/categoria-produto.jsp";
    }
}