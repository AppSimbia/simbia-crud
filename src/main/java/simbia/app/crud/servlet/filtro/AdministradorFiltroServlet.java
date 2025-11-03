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
        public boolean entidadeCorrepondeAoFiltro(String sequenciaDeCaracteres, Administrador entidade) {
            return String.valueOf(entidade.getIdAdministrador()).contains(sequenciaDeCaracteres) ||
                    entidade.getNome().toLowerCase().contains(sequenciaDeCaracteres) ||
                    entidade.getEmail().toLowerCase().contains(sequenciaDeCaracteres);
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
