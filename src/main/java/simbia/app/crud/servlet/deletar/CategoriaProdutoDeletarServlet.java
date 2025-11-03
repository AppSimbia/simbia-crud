package simbia.app.crud.servlet.deletar;

import jakarta.servlet.annotation.WebServlet;
import simbia.app.crud.dao.CategoriaProdutoDao;
import simbia.app.crud.infra.dao.abstractclasses.DaoException;
import simbia.app.crud.infra.dao.exception.errosDeOperacao.NaoHouveAlteracaoNoBancoDeDadosException;
import simbia.app.crud.infra.servlet.abstractclasses.DeletarServlet;

/**
 * Servlet para deletar uma categoria de produto.
 * Mapeada para a URL /categoria-produto/deletar e responde a requisições POST.
 */
@WebServlet("/categoria-produto/deletar")
public class CategoriaProdutoDeletarServlet extends DeletarServlet {

    // Instancia o DAO específico para esta servlet
    private final CategoriaProdutoDao categoriaProdutoDao = new CategoriaProdutoDao();

    /**
     * Implementação que chama o DAO do CategoriaProduto.
     */
    @Override
    public void chamarDaoParaDeletar(long id) throws NaoHouveAlteracaoNoBancoDeDadosException, DaoException {
        this.categoriaProdutoDao.deletar(id);
    }

    @Override
    public String enderecoDeDespache() {
        return "/categoria-produto/atualizar";
    }

    @Override
    public String enderecoDeDespacheCasoErro() {
        return "../categoria-produto/registros";
    }
}
