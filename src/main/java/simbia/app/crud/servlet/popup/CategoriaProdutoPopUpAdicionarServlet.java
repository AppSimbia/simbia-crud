package simbia.app.crud.servlet.popup;

import jakarta.servlet.annotation.WebServlet;
import simbia.app.crud.infra.servlet.abstractclasses.PopUpAdicionarServlet;

/**
 * Servlet de Pop-Up de formulario de novo registros
 */
@WebServlet("/categoria-produto/popup/adicionar")
public class CategoriaProdutoPopUpAdicionarServlet extends PopUpAdicionarServlet {
    @Override
    public String nomeDaTabela() {
        return "categoriaproduto";
    }

    @Override
    public String enderecoDeDespache() {
        return "../../categoria-produto.jsp";
    }
}
