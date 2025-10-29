package simbia.app.crud.servlet.deletar;

import jakarta.servlet.annotation.WebServlet;
import simbia.app.crud.dao.TipoIndustriaDao;
import simbia.app.crud.infra.dao.abstractclasses.DaoException;
import simbia.app.crud.infra.dao.exception.errosDeOperacao.NaoHouveAlteracaoNoBancoDeDadosException;
import simbia.app.crud.infra.servlet.abstractclasses.DeletarServlet;

/**
 * Servlet para deletar um tipo de industria.
 * Mapeada para a URL /tipo-industria/deletar e responde a requisições POST.
 */
@WebServlet(name = "TipoIndustriaDeletarServlet", urlPatterns = {"/tipo-industria/deletar"})
public class TipoIndustriaDeletarServlet extends DeletarServlet {

    // Instancia o DAO específico para esta servlet
    private final TipoIndustriaDao tipoIndustriaDao = new TipoIndustriaDao();

    /**
     * Implementação que chama o DAO do TipoIndustria.
     * que sabe como tratá-las.
     */
    @Override
    public void chamarDaoParaDeletar(long id) throws NaoHouveAlteracaoNoBancoDeDadosException, DaoException {
        // Chama o método deletar do DAO específico
        this.tipoIndustriaDao.deletar(id);
    }

    /**
     * Após deletar com sucesso, redireciona de volta para a servlet de registros.
     * Isso força o recarregamento da lista de administradores.
     */
    @Override
    public String enderecoDeRedirecionamentoPosDelecao() {
        return "/tipo-industria/registros";
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

