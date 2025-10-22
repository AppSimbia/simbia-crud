package simbia.app.crud.servlet.registros;

import jakarta.servlet.annotation.WebServlet;
import simbia.app.crud.dao.VantagemPlanoDao;
import simbia.app.crud.infra.servlet.abstractclasses.RegistrosServlet;

import java.util.List;

@WebServlet("/vantagem-plano/registros")
public class VantagemPlanoRegistrosServlet extends RegistrosServlet {
    @Override
    public List recuperarRegistrosDaTabela() {
        VantagemPlanoDao dao = new VantagemPlanoDao();

        return dao.recuperarTudo();
    }

    @Override
    public String nomeDaTabela() {
        return "vantagemplano";
    }

    @Override
    public String enderecoDeDespache() {
        return "../vantagem-plano.jsp";
    }

    @Override
    public String enderecoDeDespacheCasoErro() {
        return "erro.jsp";
    }
}
