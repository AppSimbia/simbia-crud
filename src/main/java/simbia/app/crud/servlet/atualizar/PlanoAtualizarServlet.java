package simbia.app.crud.servlet.atualizar;

import jakarta.servlet.annotation.WebServlet;
import simbia.app.crud.infra.servlet.abstractclasses.AtualizarServlet;

@WebServlet("/plano/atualizar")
public class PlanoAtualizarServlet extends AtualizarServlet {
    @Override
    public String bancoDeDados() {
        return "plano";
    }

    @Override
    public String enderecoDeRedirecionamento() {
        return "/plano.jsp";
    }
}
