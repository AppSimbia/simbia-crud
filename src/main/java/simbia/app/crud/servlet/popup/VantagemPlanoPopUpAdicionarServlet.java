package simbia.app.crud.servlet.popup;

import jakarta.servlet.annotation.WebServlet;
import simbia.app.crud.infra.servlet.abstractclasses.PopUpAdicionarServlet;

/**
 * Servlet de Pop-Up de formulario de novo registros
 */
@WebServlet("/vantagem-plano/popup/adicionar")
public class VantagemPlanoPopUpAdicionarServlet extends PopUpAdicionarServlet {
    @Override
    public String nomeDaTabela() {
        return "vantagemplano";
    }

    @Override
    public String enderecoDeDespache() {
        return "../../vantagem-plano.jsp";
    }
}
