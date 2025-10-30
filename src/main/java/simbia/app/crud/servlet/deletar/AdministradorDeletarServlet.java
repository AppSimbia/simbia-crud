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
@WebServlet(name = "AdministradorDeletarServlet", urlPatterns = {"/administrador/deletar"})
public class AdministradorDeletarServlet extends DeletarServlet {

    // Instancia o DAO específico para esta servlet
    private final AdministradorDao administradorDao = new AdministradorDao();

    /**
     * Implementação que chama o DAO do Administrador.
     */
    @Override
    public void chamarDaoParaDeletar(long id) throws NaoHouveAlteracaoNoBancoDeDadosException, DaoException {
        // Chama o método deletar do DAO específico
        this.administradorDao.deletar(id);
    }

    /**
     * Após deletar com sucesso, redireciona de volta para a servlet de registros.
     * Isso força o recarregamento da lista de administradores.
     */
    @Override
    public String enderecoDeRedirecionamentoPosDelecao() {
        return "/administrador/registros";
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