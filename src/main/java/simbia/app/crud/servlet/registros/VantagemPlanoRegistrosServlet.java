package simbia.app.crud.servlet.registros;

import jakarta.servlet.annotation.WebServlet;
import simbia.app.crud.dao.VantagemPlanoDao;
import simbia.app.crud.infra.dao.abstractclasses.DaoException;
import simbia.app.crud.infra.servlet.abstractclasses.RegistrosServlet;
import simbia.app.crud.model.dao.VantagemPlano;

import java.util.List;

/**
 * Classe de servlet de registros para exibição.
 */
@WebServlet("/vantagem-plano/registros")
public class VantagemPlanoRegistrosServlet extends RegistrosServlet<VantagemPlano> {
    @Override
    public List recuperarRegistrosDaTabela() throws DaoException {
        VantagemPlanoDao dao = new VantagemPlanoDao();

        return dao.recuperarTudo();
    }

    @Override
    public String nomeDaTabela() {
        return "vantagemplano";
    }

    @Override
    public String enderecoDeDespache() {
        return "../vantagem-plano.jsp";
    }

    @Override
    public String enderecoDeDespacheCasoErro() {
        return "/crud/assets/paginas-de-erro/erro-conexao.html";
    }
}
