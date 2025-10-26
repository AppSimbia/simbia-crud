package simbia.app.crud.servlet.filtro;

import jakarta.servlet.annotation.WebServlet;
import simbia.app.crud.infra.servlet.abstractclasses.FiltroServlet;
import simbia.app.crud.model.dao.Permissao;

@WebServlet("/permissao/filtro")
public class PermissaoFiltroServlet extends FiltroServlet<Permissao> {
    @Override
    public boolean entidadeCorrepondeAoFiltro(String regexFiltro, Permissao entidade) {
        return String.valueOf(entidade.getIdPermissao()).matches(regexFiltro) ||
                entidade.getNomePermissao().toLowerCase().matches(regexFiltro);
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
