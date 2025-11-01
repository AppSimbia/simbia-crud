package simbia.app.crud.servlet.registros;

import jakarta.servlet.annotation.WebServlet;
import simbia.app.crud.dao.CategoriaProdutoDao;
import simbia.app.crud.infra.dao.abstractclasses.DaoException;
import simbia.app.crud.infra.servlet.abstractclasses.RegistrosServlet;
import simbia.app.crud.model.dao.CategoriaProduto;

import java.util.List;

/**
 * Classe de servlet de registros para exibição.
 */
@WebServlet("/categoria-produto/registros")
public class CategoriaProdutoRegistrosServlet extends RegistrosServlet<CategoriaProduto> {
    @Override
    public List<CategoriaProduto> recuperarRegistrosDaTabela() throws DaoException {
        CategoriaProdutoDao dao = new CategoriaProdutoDao();

        return dao.recuperarTudo();
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
        return "/crud/assets/paginas-de-erro/erro-conexao.html";
    }
}
