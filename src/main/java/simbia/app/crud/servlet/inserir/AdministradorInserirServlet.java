package simbia.app.crud.servlet.inserir;

import jakarta.servlet.annotation.WebServlet;
import simbia.app.crud.dao.AdministradorDao;
import simbia.app.crud.infra.dao.abstractclasses.DaoException;
import simbia.app.crud.infra.dao.exception.errosDeOperacao.NaoHouveAlteracaoNoBancoDeDadosException;
import simbia.app.crud.infra.servlet.abstractclasses.InserirServlet;
import simbia.app.crud.model.dao.Administrador;
import simbia.app.crud.model.servlet.RequisicaoResposta;

/**
 * Servlet concreta para INSERIR um novo Administrador.
 * Mapeada para a URL /administrador/inserir e responde a requisições POST.
 */
@WebServlet(name = "AdministradorInserirServlet", urlPatterns = {"/administrador/inserir"})
public class AdministradorInserirServlet extends InserirServlet<Administrador> {

    // Instancia o DAO específico para esta servlet
    private final AdministradorDao administradorDao = new AdministradorDao();

    /**
     * Pega os parâmetros "nome", "email" e "senha" da requisição
     * e constrói um objeto CategoriaProduto.
     */
    @Override
    public Administrador construirEntidadeDaRequisicao(RequisicaoResposta requisicaoResposta) throws IllegalArgumentException {
        String nome = requisicaoResposta.recuperarParametroDaRequisicao("usuario");
        String usuario = requisicaoResposta.recuperarParametroDaRequisicao("usuario");
        String senha = requisicaoResposta.recuperarParametroDaRequisicao("senha");

        if (nome == null || nome.trim().isEmpty() || usuario == null || usuario.trim().isEmpty() || senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome, email e senha são obrigatórios.");
        }
        return new Administrador(usuario, senha);
    }

    /**
     * Implementação que chama o DAO do Administrador para inserir.
     */
    @Override
    public void chamarDaoParaInserir(Administrador administrador) throws NaoHouveAlteracaoNoBancoDeDadosException, DaoException {
        // Chama o método inserir do DAO específico
        this.administradorDao.inserir(administrador);
    }

    /**
     * Após inserir com sucesso, redireciona de volta para a servlet de registros.
     */
    @Override
    public String enderecoDeRedirecionamentoPosInsercao() {
        return "/administrador/registros";
    }

    /**
     * Página JSP para onde o usuário é despachado
     * caso ocorra qualquer erro no processo.
     */
    @Override
    public String enderecoDeDespacheCasoErro() {
        return "/erro.jsp";
    }
}