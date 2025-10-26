package simbia.app.crud.servlet.atualizar;

import jakarta.servlet.annotation.WebServlet;
import simbia.app.crud.infra.servlet.abstractclasses.AtualizarServlet;

@WebServlet("/permissao/atualizar")
public class PermissaoAtualizarServlet extends AtualizarServlet {
    @Override
    public String bancoDeDados() {
        return "permissao";
    }

    @Override
    public String enderecoDeRedirecionamento() {
        return "../permissao.jsp";
    }
}
