package simbia.app.crud.servlet.filtro;

import jakarta.servlet.annotation.WebServlet;
import simbia.app.crud.infra.servlet.abstractclasses.FiltroServlet;
import simbia.app.crud.model.dao.TipoIndustria;

/**
 * Classe de servlet de atualização dos registros em exibição.
 */
@WebServlet("/tipo-industria/filtro")
public class TipoIndustriaFiltroServlet extends FiltroServlet<TipoIndustria> {
    @Override
    public boolean entidadeCorrepondeAoFiltro(String sequenciaDeCaracteres, TipoIndustria entidade) {
        return String.valueOf(entidade.getIdTipoIndustria()).contains(sequenciaDeCaracteres) ||
                entidade.getNomeTipoIndustria().toLowerCase().contains(sequenciaDeCaracteres);
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
