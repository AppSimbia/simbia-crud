package simbia.app.crud.servlet;

import jakarta.servlet.annotation.WebServlet;
import simbia.app.crud.dao.AdministradorDao;
import simbia.app.crud.infra.servlet.abstractclasses.RegistrosServlet;
import simbia.app.crud.model.dao.Administrador;

import java.util.List;

@WebServlet("/administrador/registros")
public class AdministradorRegistrosServlet extends RegistrosServlet<Administrador> {
    @Override
    public List<Administrador> recuperarRegistrosDaTabela(){
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
        return "erroEmRegistros.jsp";
    }
}
