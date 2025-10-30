package simbia.app.crud.servlet.popup;

import jakarta.servlet.annotation.WebServlet;
import simbia.app.crud.infra.servlet.abstractclasses.PopUpAdicionarServlet;

/**
 * Servlet de Pop-Up de formulario de novo registros
 */
@WebServlet("/plano/popup/adicionar")
public class PlanoPopUpAdicionarServlet extends PopUpAdicionarServlet {
    @Override
    public String nomeDaTabela() {
        return "plano";
    }

    @Override
    public String enderecoDeDespache() {
        return "../../plano.jsp";
    }
}
