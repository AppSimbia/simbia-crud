package simbia.app.crud.servlet.atualizar;

import simbia.app.crud.infra.servlet.abstractclasses.AtualizarServlet;

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
