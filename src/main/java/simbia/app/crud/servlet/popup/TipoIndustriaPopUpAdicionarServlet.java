package simbia.app.crud.servlet.popup;

import jakarta.servlet.annotation.WebServlet;
import simbia.app.crud.infra.servlet.abstractclasses.PopUpAdicionarServlet;

/**
 * Servlet de Pop-Up de formulario de novo registros
 */
@WebServlet("/tipo-industria/popup/adicionar")
public class TipoIndustriaPopUpAdicionarServlet extends PopUpAdicionarServlet {
    @Override
    public String nomeDaTabela() {
        return "tipoindustria";
    }

    @Override
    public String enderecoDeDespache() {
        return "../../tipo-industria.jsp";
    }
}
