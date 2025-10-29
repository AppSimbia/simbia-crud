package simbia.app.crud.servlet.registros;

import jakarta.servlet.annotation.WebServlet;
import simbia.app.crud.dao.VantagemDao;
import simbia.app.crud.infra.dao.abstractclasses.DaoException;
import simbia.app.crud.infra.servlet.abstractclasses.RegistrosServlet;
import simbia.app.crud.model.dao.Vantagem;

import java.util.List;

/**
 * Classe de servlet de registros para exibição.
 */
@WebServlet("/vantagem/registros")
public class VantagemRegistrosServlet extends RegistrosServlet<Vantagem> {
    @Override
    public List<Vantagem> recuperarRegistrosDaTabela() throws DaoException {
        VantagemDao dao = new VantagemDao();

        return dao.recuperarTudo();
    }

    @Override
    public String nomeDaTabela() {
        return "vantagem";
    }

    @Override
    public String enderecoDeDespache() {
        return "../vantagem.jsp";
    }

    @Override
    public String enderecoDeDespacheCasoErro() {
        return "erro.jsp";
    }
}
