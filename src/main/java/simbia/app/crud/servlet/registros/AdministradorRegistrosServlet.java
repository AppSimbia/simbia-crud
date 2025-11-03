package simbia.app.crud.servlet.registros;

import jakarta.servlet.annotation.WebServlet;
import simbia.app.crud.dao.AdministradorDao;
import simbia.app.crud.infra.dao.abstractclasses.DaoException;
import simbia.app.crud.infra.servlet.abstractclasses.RegistrosServlet;
import simbia.app.crud.model.dao.Administrador;

import java.util.List;

/**
 * Classe de servlet de registros para exibição.
 */
@WebServlet("/administrador/registros")
public class AdministradorRegistrosServlet extends RegistrosServlet<Administrador> {
    @Override
    public List<Administrador> recuperarRegistrosDaTabela() throws DaoException {
        AdministradorDao dao = new AdministradorDao();

        return dao.recuperarTudo();
    }

    @Override
    public String nomeDaTabela() {
        return "administrador";
    }

    @Override
    public String enderecoDeDespache() {
        return "../administrador.jsp";
    }

    @Override
    public String enderecoDeDespacheCasoErro() {
        return "/crud/assets/paginas-de-erro/erro-conexao.html";
    }
}
