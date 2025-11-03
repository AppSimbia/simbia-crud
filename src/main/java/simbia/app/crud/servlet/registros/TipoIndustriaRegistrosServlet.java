package simbia.app.crud.servlet.registros;

import jakarta.servlet.annotation.WebServlet;
import simbia.app.crud.dao.TipoIndustriaDao;
import simbia.app.crud.infra.dao.abstractclasses.DaoException;
import simbia.app.crud.infra.servlet.abstractclasses.RegistrosServlet;
import simbia.app.crud.model.dao.TipoIndustria;

import java.util.List;

/**
 * Classe de servlet de registros para exibição.
 */
@WebServlet("/tipo-industria/registros")
public class TipoIndustriaRegistrosServlet extends RegistrosServlet<TipoIndustria> {
    @Override
    public List<TipoIndustria> recuperarRegistrosDaTabela() throws DaoException {
        TipoIndustriaDao dao = new TipoIndustriaDao();

        return dao.recuperarTudo();
    }

    @Override
    public String nomeDaTabela() {
        return "tipoindustria";
    }

    @Override
    public String enderecoDeDespache() {
        return "../tipo-industria.jsp";
    }

    @Override
    public String enderecoDeDespacheCasoErro() {
        return "/crud/assets/paginas-de-erro/erro-conexao.html";
    }
}
