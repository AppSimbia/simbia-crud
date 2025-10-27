package simbia.app.crud.servlet.atualizar;

import jakarta.servlet.annotation.WebServlet;
import simbia.app.crud.infra.servlet.abstractclasses.AtualizarServlet;

@WebServlet("/vantagem/atualizar")
public class VantagemAtualizarServlet extends AtualizarServlet {
    @Override
    public String bancoDeDados() {
        return "vantagem";
    }

    @Override
    public String enderecoDeRedirecionamento() {
        return "/vantagem.jsp";
    }
}
