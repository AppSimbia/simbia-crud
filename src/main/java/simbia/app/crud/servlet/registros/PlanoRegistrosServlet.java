package simbia.app.crud.servlet.registros;

import jakarta.servlet.annotation.WebServlet;
import simbia.app.crud.dao.PlanoDao;
import simbia.app.crud.infra.dao.abstractclasses.DaoException;
import simbia.app.crud.infra.servlet.abstractclasses.RegistrosServlet;
import simbia.app.crud.model.dao.Plano;

import java.util.List;

/**
 * Classe de servlet de registros para exibição.
 */
@WebServlet("/plano/registros")
public class PlanoRegistrosServlet extends RegistrosServlet<Plano> {
    @Override
    public List<Plano> recuperarRegistrosDaTabela() throws DaoException {
        PlanoDao dao = new PlanoDao();

        return dao.recuperarTudo();
    }

    @Override
    public String nomeDaTabela() {
        return "plano";
    }

    @Override
    public String enderecoDeDespache() {
        return "../plano.jsp";
    }

    @Override
    public String enderecoDeDespacheCasoErro() {
        return "erro.jsp";
    }
}
