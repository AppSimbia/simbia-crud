package simbia.app.crud.servlet.atualizar;

import jakarta.servlet.annotation.WebServlet;
import simbia.app.crud.infra.servlet.abstractclasses.AtualizarServlet;

@WebServlet("/categoria-produto/atualizar")
public class CategoriaProdutoAtualizarServlet extends AtualizarServlet {
    @Override
    public String bancoDeDados() {
        return "categoriaproduto";
    }

    @Override
    public String enderecoDeRedirecionamento() {
        return "../categoria-produto.jsp";
    }
}
