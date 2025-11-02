package simbia.app.crud.servlet.deletar;

import jakarta.servlet.annotation.WebServlet;
import simbia.app.crud.dao.VantagemPlanoDao;
import simbia.app.crud.infra.dao.abstractclasses.DaoException;
import simbia.app.crud.infra.dao.exception.errosDeOperacao.NaoHouveAlteracaoNoBancoDeDadosException;
import simbia.app.crud.infra.servlet.abstractclasses.DeletarServlet;

/**
 * Servlet para deletar uma vantagem do plano.
 * Mapeada para a URL /vantagem-plano/deletar e responde a requisições POST.
 */
@WebServlet("/vantagem-plano/deletar")
public class VantagemPlanoDeletarServlet extends DeletarServlet {

    // Instancia o DAO específico para esta servlet
    private final VantagemPlanoDao vantagemPlanoDao = new VantagemPlanoDao();

    /**
     * Implementação que chama o DAO do VantagemPlano.
     * que sabe como tratá-las.
     */
    @Override
    public void chamarDaoParaDeletar(long id) throws NaoHouveAlteracaoNoBancoDeDadosException, DaoException {
        // Chama o método deletar do DAO específico
        this.vantagemPlanoDao.deletar(id);
    }

    @Override
    public String enderecoDeDespache() {
        return "/vantagem-plano/atualizar";
    }

    @Override
    public String enderecoDeDespacheCasoErro() {
        return "../vantagem-plano/registros";
    }
}



