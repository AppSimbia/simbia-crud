package simbia.app.crud.servlet.popup;

import jakarta.servlet.annotation.WebServlet;
import simbia.app.crud.infra.servlet.abstractclasses.PopUpAdicionarServlet;

/**
 * Servlet de Pop-Up de formulario de novo registros
 */
@WebServlet("/permissao/popup/adicionar")
public class PermissaoPopUpAdicionarServlet extends PopUpAdicionarServlet {
    @Override
    public String nomeDaTabela() {
        return "permissao";
    }

    @Override
    public String enderecoDeDespache() {
        return "../../permissao.jsp";
    }
}
