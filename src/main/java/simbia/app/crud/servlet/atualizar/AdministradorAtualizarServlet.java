package simbia.app.crud.servlet.atualizar;

import jakarta.servlet.annotation.WebServlet;
import simbia.app.crud.infra.servlet.abstractclasses.AtualizarServlet;

/**
 * Classe de servlet de atualização dos registros em exibição.
 */
@WebServlet("/administrador/atualizar")
public class AdministradorAtualizarServlet extends AtualizarServlet {
    @Override
    public String bancoDeDados() {
        return "administrador";
    }

    @Override
    public String enderecoDeRedirecionamento() {
        return "/administrador.jsp";
    }
}
