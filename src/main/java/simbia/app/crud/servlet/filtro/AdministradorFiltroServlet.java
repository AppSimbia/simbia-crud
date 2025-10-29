    package simbia.app.crud.servlet.filtro;

    import jakarta.servlet.annotation.WebServlet;
    import simbia.app.crud.infra.servlet.abstractclasses.FiltroServlet;
    import simbia.app.crud.model.dao.Administrador;

    /**
     * Classe de servlet de atualização dos registros em exibição.
     */
    @WebServlet("/administrador/filtro")
    public class AdministradorFiltroServlet extends FiltroServlet<Administrador> {

        @Override
        public boolean entidadeCorrepondeAoFiltro(String regexFiltro, Administrador entidade) {
            return String.valueOf(entidade.getIdAdministrador()).matches(regexFiltro) ||
                    entidade.getNome().toLowerCase().matches(regexFiltro) ||
                    entidade.getEmail().toLowerCase().matches(regexFiltro);
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
            return "../administrador/registros";
        }
    }
