package simbia.app.crud.servlet.filtro;

import jakarta.servlet.annotation.WebServlet;
import simbia.app.crud.infra.servlet.abstractclasses.FiltroServlet;
import simbia.app.crud.model.dao.VantagemPlano;

/**
 * Classe de servlet de atualização dos registros em exibição.
 */
@WebServlet("/vantagem-plano/filtro")
public class VantagemPlanoFiltroServlet extends FiltroServlet<VantagemPlano> {
    @Override
    public boolean entidadeCorrepondeAoFiltro(String sequenciaDeCaracteres, VantagemPlano entidade) {
        return String.valueOf(entidade.getIdVantagemPlano()).contains(sequenciaDeCaracteres) ||
                String.valueOf(entidade.getIdVantagem()).contains(sequenciaDeCaracteres) ||
                String.valueOf(entidade.getIdPlano()).contains(sequenciaDeCaracteres);
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
