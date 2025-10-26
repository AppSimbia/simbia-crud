package simbia.app.crud.servlet.atualizar;

import simbia.app.crud.infra.servlet.abstractclasses.AtualizarServlet;

public class VantagemPlanoServlet extends AtualizarServlet {
    @Override
    public String bancoDeDados() {
        return "vantagemplano";
    }

    @Override
    public String enderecoDeRedirecionamento() {
        return "/vatagem-plano.jsp";
    }
}
