package simbia.app.crud.servlet.registros;

import jakarta.servlet.annotation.WebServlet;
import simbia.app.crud.dao.PermissaoDao;
import simbia.app.crud.infra.dao.abstractclasses.DaoException;
import simbia.app.crud.infra.servlet.abstractclasses.RegistrosServlet;
import simbia.app.crud.model.dao.Permissao;

import java.util.List;

/**
 * Classe de servlet de registros para exibição.
 */
@WebServlet("/permissao/registros")
public class PermissaoRegistrosServlet extends RegistrosServlet<Permissao> {
    @Override
    public List<Permissao> recuperarRegistrosDaTabela() throws DaoException {
        PermissaoDao dao = new PermissaoDao();

        return dao.recuperarTudo();
    }

    @Override
    public String nomeDaTabela() {
        return "permissao";
    }

    @Override
    public String enderecoDeDespache() {
        return "../permissao.jsp";
    }

    @Override
    public String enderecoDeDespacheCasoErro() {
        return "erro.jsp";
    }
}
