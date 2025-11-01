package simbia.app.crud.servlet.inserir;

import jakarta.servlet.annotation.WebServlet;
import simbia.app.crud.dao.CategoriaProdutoDao;
import simbia.app.crud.infra.dao.abstractclasses.DaoException;
import simbia.app.crud.infra.dao.exception.errosDeOperacao.NaoHouveAlteracaoNoBancoDeDadosException;
import simbia.app.crud.infra.servlet.abstractclasses.InserirServlet;
import simbia.app.crud.model.dao.CategoriaProduto;
import simbia.app.crud.model.servlet.RequisicaoResposta;

/**
 * Servlet concreta para INSERIR uma nova categoria de produto.
 * Mapeada para a URL /categoria-produto/inserir e responde a requisições POST.
 */
@WebServlet(name = "CategoriaProdutoInserirServlet", urlPatterns = {"/categoria-produto/inserir"})
public class CategoriaProdutoInserirServlet extends InserirServlet<CategoriaProduto> {

    private final CategoriaProdutoDao categoriaProdutoDao = new CategoriaProdutoDao();

    /**
     * Pega os parâmetros "nomeCategoria", e "descricao" da requisição
     * e constrói um objeto CategoriaProduto.
     */
    @Override
    public CategoriaProduto construirEntidadeDaRequisicao(RequisicaoResposta requisicaoResposta) throws IllegalArgumentException {
        String nomeCategoria = requisicaoResposta.recuperarParametroDaRequisicao("nomeCategoria");
        String descricao = requisicaoResposta.recuperarParametroDaRequisicao("descricao");

        if (nomeCategoria == null || nomeCategoria.trim().isEmpty() || descricao == null || descricao.trim().isEmpty()) {
            throw new IllegalArgumentException("Preencha todas as informações.");
        }
        return new CategoriaProduto(nomeCategoria, descricao);
    }

    /**
     * Implementação que chama o DAO do CategoriaProduto para inserir.
     */
    @Override
    public void chamarDaoParaInserir(CategoriaProduto categoriaProduto) throws NaoHouveAlteracaoNoBancoDeDadosException, DaoException {
        // Chama o método inserir do DAO específico
        this.categoriaProdutoDao.inserir(categoriaProduto);
    }

    /**
     * Após inserir com sucesso, redireciona de volta para a servlet de registros.
     */
    @Override
    public String enderecoDeDespache() {
        return "/categoria-produto/registros";
    }

}