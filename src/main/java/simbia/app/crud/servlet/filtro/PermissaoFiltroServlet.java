package simbia.app.crud.servlet.filtro;

import jakarta.servlet.annotation.WebServlet;
import simbia.app.crud.infra.servlet.abstractclasses.FiltroServlet;
import simbia.app.crud.model.dao.Permissao;

/**
 * Classe de servlet de atualização dos registros em exibição.
 */
@WebServlet("/permissao/filtro")
public class PermissaoFiltroServlet extends FiltroServlet<Permissao> {
    @Override
    public boolean entidadeCorrepondeAoFiltro(String sequenciaDeCaracteres, Permissao entidade) {
        return String.valueOf(entidade.getIdPermissao()).contains(sequenciaDeCaracteres) ||
                entidade.getNomePermissao().toLowerCase().contains(sequenciaDeCaracteres);
    }

    @Override
    public String nomeDaTabela() {
        return "permissao";
    }

    @Override
    public String enderecoDeDespache() {
        return "../permissao.jsp";
    }

    @Override
    public String enderecoDeDespacheCasoErro() {
        return "../permissao/registros";
    }
}
