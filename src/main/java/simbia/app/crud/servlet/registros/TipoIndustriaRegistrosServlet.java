package simbia.app.crud.servlet;

import jakarta.servlet.annotation.WebServlet;
import simbia.app.crud.dao.TipoIndustriaDao;
import simbia.app.crud.infra.servlet.abstractclasses.RegistrosServlet;
import simbia.app.crud.model.dao.TipoIndustria;

import java.util.List;

@WebServlet("/tipo-industria/registros")
public class TipoIndustriaRegistrosServlet extends RegistrosServlet<TipoIndustria> {
    @Override
    public List<TipoIndustria> recuperarRegistrosDaTabela() {
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
        return "erro.jsp";
    }
}
