package simbia.app.crud.servlet.popup;

import jakarta.servlet.annotation.WebServlet;
import simbia.app.crud.infra.servlet.abstractclasses.PopUpAdicionarServlet;

/**
 * Servlet de Pop-Up de formulario de novo registros
 */
@WebServlet("/administrador/popup/adicionar")
public class AdministradorPopUpAdicionarServlet extends PopUpAdicionarServlet {
    @Override
    public String nomeDaTabela() {
        return "administrador";
    }

    @Override
    public String enderecoDeDespache() {
        return "../../administrador.jsp";
    }
}
