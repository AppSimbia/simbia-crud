package simbia.app.crud.servlet.filtro;

import jakarta.servlet.annotation.WebServlet;
import simbia.app.crud.infra.servlet.abstractclasses.FiltroServlet;
import simbia.app.crud.model.dao.CategoriaProduto;

@WebServlet("/categoria-produto/filtro")
public class CategoriaProdutoFiltroServlet extends FiltroServlet<CategoriaProduto> {
    @Override
    public boolean entidadeCorrepondeAoFiltro(String regexFiltro, CategoriaProduto entidade) {
        return String.valueOf(entidade.getIdCategoriaProduto()).matches(regexFiltro) ||
                entidade.getNomeCategoria().toLowerCase().matches(regexFiltro);
    }

    @Override
    public String nomeDaTabela() {
        return "categoriaproduto";
    }

    @Override
    public String enderecoDeDespache() {
        return "../categoria-produto.jsp";
    }

    @Override
    public String enderecoDeDespacheCasoErro() {
        return "../categoria-produto/registros";
    }
}
