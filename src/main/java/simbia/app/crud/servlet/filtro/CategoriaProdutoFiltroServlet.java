package simbia.app.crud.servlet.filtro;

import jakarta.servlet.annotation.WebServlet;
import simbia.app.crud.infra.servlet.abstractclasses.FiltroServlet;
import simbia.app.crud.model.dao.CategoriaProduto;

/**
 * Classe de servlet de atualização dos registros em exibição.
 */
@WebServlet("/categoria-produto/filtro")
public class CategoriaProdutoFiltroServlet extends FiltroServlet<CategoriaProduto> {
    @Override
    public boolean entidadeCorrepondeAoFiltro(String sequenciaDeCaracteres, CategoriaProduto entidade) {
        return String.valueOf(entidade.getIdCategoriaProduto()).contains(sequenciaDeCaracteres) ||
                entidade.getNomeCategoria().toLowerCase().contains(sequenciaDeCaracteres);
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
