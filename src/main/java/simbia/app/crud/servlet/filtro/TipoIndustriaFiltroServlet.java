package simbia.app.crud.servlet.filtro;

import jakarta.servlet.annotation.WebServlet;
import simbia.app.crud.infra.servlet.abstractclasses.FiltroServlet;
import simbia.app.crud.model.dao.TipoIndustria;

@WebServlet("/tipo-industria/filtro")
public class TipoIndustriaFiltroServlet extends FiltroServlet<TipoIndustria> {
    @Override
    public boolean entidadeCorrepondeAoFiltro(String regexFiltro, TipoIndustria entidade) {
        return String.valueOf(entidade.getIdTipoIndustria()).matches(regexFiltro) ||
                entidade.getNomeTipoIndustria().toLowerCase().matches(regexFiltro);
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
        return "../tipo-industria/registros";
    }
}
