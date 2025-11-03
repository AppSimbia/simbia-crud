package simbia.app.crud.servlet.atualizar;

import jakarta.servlet.annotation.WebServlet;
import simbia.app.crud.infra.servlet.abstractclasses.AtualizarServlet;

/**
 * Classe de servlet de atualização dos registros em exibição.
 */
@WebServlet("/categoria-produto/atualizar")
public class CategoriaProdutoAtualizarServlet extends AtualizarServlet {
    @Override
    public String bancoDeDados() {
        return "categoriaproduto";
    }

    @Override
    public String enderecoDeRedirecionamento() {
        return "/categoria-produto.jsp";
    }
}
