package simbia.app.crud.servlet.filtro;

import jakarta.servlet.annotation.WebServlet;
import simbia.app.crud.infra.servlet.abstractclasses.FiltroServlet;
import simbia.app.crud.model.dao.Plano;

/**
 * Classe de servlet de atualização dos registros em exibição.
 */
@WebServlet("/plano/filtro")
public class PlanoFiltroServlet extends FiltroServlet<Plano> {
    @Override
    public boolean entidadeCorrepondeAoFiltro(String sequenciaDeCaracteres, Plano entidade) {
        return String.valueOf(entidade.getIdPlano()).contains(sequenciaDeCaracteres) ||
                entidade.getNomePlano().toLowerCase().contains(sequenciaDeCaracteres);
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
        return "../plano/registros";
    }
}
