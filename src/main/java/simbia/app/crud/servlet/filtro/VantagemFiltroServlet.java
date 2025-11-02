package simbia.app.crud.servlet.filtro;

import jakarta.servlet.annotation.WebServlet;
import simbia.app.crud.infra.servlet.abstractclasses.FiltroServlet;
import simbia.app.crud.model.dao.Vantagem;

/**
 * Classe de servlet de atualização dos registros em exibição.
 */
@WebServlet("/vantagem/filtro")
public class VantagemFiltroServlet extends FiltroServlet<Vantagem> {
    @Override
    public boolean entidadeCorrepondeAoFiltro(String sequenciaDeCaracteres, Vantagem entidade) {
        return String.valueOf(entidade.getIdVantagem()).contains(sequenciaDeCaracteres) ||
                entidade.getNomeVantagem().toLowerCase().contains(sequenciaDeCaracteres);
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
        return "../vantagem/registros";
    }
}
