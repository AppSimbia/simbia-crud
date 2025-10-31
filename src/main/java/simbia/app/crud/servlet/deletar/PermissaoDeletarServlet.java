package simbia.app.crud.servlet.deletar;

import jakarta.servlet.annotation.WebServlet;
import simbia.app.crud.dao.PermissaoDao;
import simbia.app.crud.infra.dao.abstractclasses.DaoException;
import simbia.app.crud.infra.dao.exception.errosDeOperacao.NaoHouveAlteracaoNoBancoDeDadosException;
import simbia.app.crud.infra.servlet.abstractclasses.DeletarServlet;

/**
 * Servlet para deletar uma permissão.
 * Mapeada para a URL /permissao/deletar e responde a requisições POST.
 */
@WebServlet(name = "TipoIndustriaDeletarServlet", urlPatterns = {"/permissao/deletar"})
public class PermissaoDeletarServlet extends DeletarServlet {

    // Instancia o DAO específico para esta servlet
    private final PermissaoDao permissaoDao = new PermissaoDao();

    /**
     * Implementação que chama o DAO do Permissao.
     * que sabe como tratá-las.
     */
    @Override
    public void chamarDaoParaDeletar(long id) throws NaoHouveAlteracaoNoBancoDeDadosException, DaoException {
        // Chama o método deletar do DAO específico
        this.permissaoDao.deletar(id);
    }

    @Override
    public String enderecoDeDespache() {
        return "../permissao.jsp";
    }

    @Override
    public String enderecoDeDespacheCasoErro() {
        return "../permissao/registros";
    }
}


