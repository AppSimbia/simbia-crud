package simbia.app.crud.servlet.filtro;

import jakarta.servlet.annotation.WebServlet;
import simbia.app.crud.infra.servlet.abstractclasses.FiltroServlet;
import simbia.app.crud.model.dao.VantagemPlano;

@WebServlet("/vantagem-plano/filtro")
public class VantagemPlanoFiltroServlet extends FiltroServlet<VantagemPlano> {
    @Override
    public boolean entidadeCorrepondeAoFiltro(String regexFiltro, VantagemPlano entidade) {
        return String.valueOf(entidade.getIdVantagemPlano()).matches(regexFiltro) ||
                String.valueOf(entidade.getIdVantagem()).matches(regexFiltro) ||
                String.valueOf(entidade.getIdPlano()).matches(regexFiltro);
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
        return "../vantagem-plano/registros";
    }
}
