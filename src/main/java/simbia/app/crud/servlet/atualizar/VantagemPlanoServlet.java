package simbia.app.crud.servlet.atualizar;

import jakarta.servlet.annotation.WebServlet;
import simbia.app.crud.infra.servlet.abstractclasses.AtualizarServlet;

/**
 * Classe de servlet de atualização dos registros em exibição.
 */
@WebServlet("/vantagem-plano/atualizar")
public class VantagemPlanoServlet extends AtualizarServlet {
    @Override
    public String bancoDeDados() {
        return "vantagemplano";
    }

    @Override
    public String enderecoDeRedirecionamento() {
        return "/vantagem-plano.jsp";
    }
}