package simbia.app.crud.servlet.filtro;

import jakarta.servlet.annotation.WebServlet;
import simbia.app.crud.infra.servlet.abstractclasses.FiltroServlet;
import simbia.app.crud.model.dao.Plano;

@WebServlet("/plano/filtro")
public class PlanoFiltroServlet extends FiltroServlet<Plano> {
    @Override
    public boolean entidadeCorrepondeAoFiltro(String regexFiltro, Plano entidade) {
        return String.valueOf(entidade.getIdPlano()).matches(regexFiltro) ||
                entidade.getNomePlano().toLowerCase().matches(regexFiltro);
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
