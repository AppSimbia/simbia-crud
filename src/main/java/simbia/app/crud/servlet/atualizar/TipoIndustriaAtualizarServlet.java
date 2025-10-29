package simbia.app.crud.servlet.atualizar;

import jakarta.servlet.annotation.WebServlet;
import simbia.app.crud.infra.servlet.abstractclasses.AtualizarServlet;

/**
 * Classe de servlet de atualização dos registros em exibição.
 */
@WebServlet("/tipo-industria/atualizar")
public class TipoIndustriaAtualizarServlet extends AtualizarServlet {
    @Override
    public String bancoDeDados() {
        return "tipoindustria";
    }

    @Override
    public String enderecoDeRedirecionamento() {
        return "/tipo-industria.jsp";
    }
}
