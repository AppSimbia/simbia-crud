package simbia.app.crud.servlet.deletar;

import jakarta.servlet.annotation.WebServlet;
import simbia.app.crud.dao.PlanoDao;
import simbia.app.crud.infra.dao.abstractclasses.DaoException;
import simbia.app.crud.infra.dao.exception.errosDeOperacao.NaoHouveAlteracaoNoBancoDeDadosException;
import simbia.app.crud.infra.servlet.abstractclasses.DeletarServlet;

/**
 * Servlet para deletar um plano.
 * Mapeada para a URL /plano/deletar e responde a requisições POST.
 */
@WebServlet("/plano/deletar")
public class PlanoDeletarServlet extends DeletarServlet {

    // Instancia o DAO específico para esta servlet
    private final PlanoDao planoDao = new PlanoDao();

    /**
     * Implementação que chama o DAO do Plano.
     * que sabe como tratá-las.
     */
    @Override
    public void chamarDaoParaDeletar(long id) throws NaoHouveAlteracaoNoBancoDeDadosException, DaoException {
        // Chama o método deletar do DAO específico
        this.planoDao.deletar(id);
    }

    @Override
    public String enderecoDeDespache() {
        return "/plano/atualizar";
    }

    @Override
    public String enderecoDeDespacheCasoErro() {
        return "../plano/registros";
    }
}


