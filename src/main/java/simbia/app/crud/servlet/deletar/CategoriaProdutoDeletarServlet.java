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
@WebServlet(name = "CategoriaProdutoDeletarServlet", urlPatterns = {"/categoria-produto/deletar"})
public class CategoriaProdutoDeletarServlet extends DeletarServlet {

    // Instancia o DAO específico para esta servlet
    private final CategoriaProdutoDao categoriaProdutoDao = new CategoriaProdutoDao();

    /**
     * Implementação que chama o DAO do CategoriaProduto.
     */
    @Override
    public void chamarDaoParaDeletar(long id) throws NaoHouveAlteracaoNoBancoDeDadosException, DaoException {
        // Chama o método deletar do DAO específico
        this.categoriaProdutoDao.deletar(id);
    }

    /**
     * Após deletar com sucesso, redireciona de volta para a servlet de registros.
     * Isso força o recarregamento da lista de administradores.
     */
    @Override
    public String enderecoDeRedirecionamentoPosDelecao() {
        return "/categoria-produto/registros";
    }

    /**
     * Página JSP para onde o usuário é despachado
     * caso ocorra qualquer erro no processo.
     */
    @Override
    public String enderecoDeDespacheCasoErro() {
        return "/erro.jsp";
    }
}
