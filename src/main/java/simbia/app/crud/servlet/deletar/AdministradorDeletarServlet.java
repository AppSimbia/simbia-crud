package simbia.app.crud.servlet.deletar;

import jakarta.servlet.annotation.WebServlet;
import simbia.app.crud.dao.AdministradorDao;
import simbia.app.crud.infra.dao.abstractclasses.DaoException;
import simbia.app.crud.infra.dao.exception.errosDeOperacao.NaoHouveAlteracaoNoBancoDeDadosException;
import simbia.app.crud.infra.servlet.abstractclasses.DeletarServlet;

/**
 * Servlet para deletar um Administrador.
 * Mapeada para a URL /administrador/deletar e responde a requisições POST.
 */
@WebServlet("/administrador/deletar")
public class AdministradorDeletarServlet extends DeletarServlet {

    // Instancia o DAO específico para esta servlet
    private final AdministradorDao administradorDao = new AdministradorDao();

    /**
     * Implementação que chama o DAO do Administrador.
     */
    @Override
    public void chamarDaoParaDeletar(long id) throws NaoHouveAlteracaoNoBancoDeDadosException, DaoException {
        this.administradorDao.deletar(id);
    }


    @Override
    public String enderecoDeDespache() {
        return "/administrador/atualizar";
    }

    @Override
    public String enderecoDeDespacheCasoErro() {
        return "../administrador/registros";
    }
}