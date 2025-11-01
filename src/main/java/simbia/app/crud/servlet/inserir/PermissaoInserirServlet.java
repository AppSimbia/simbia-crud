package simbia.app.crud.servlet.inserir;

import jakarta.servlet.annotation.WebServlet;
import simbia.app.crud.dao.CategoriaProdutoDao;
import simbia.app.crud.dao.PermissaoDao;
import simbia.app.crud.infra.dao.abstractclasses.DaoException;
import simbia.app.crud.infra.dao.exception.errosDeOperacao.NaoHouveAlteracaoNoBancoDeDadosException;
import simbia.app.crud.infra.servlet.abstractclasses.InserirServlet;
import simbia.app.crud.model.dao.Administrador;
import simbia.app.crud.model.dao.CategoriaProduto;
import simbia.app.crud.model.dao.Permissao;
import simbia.app.crud.model.servlet.RequisicaoResposta;

/**
 * Servlet concreta para INSERIR uma nova permissão.
 * Mapeada para a URL /permissao/inserir e responde a requisições POST.
 */
@WebServlet(name = "PermissaoServlet", urlPatterns = {"/permissao/inserir"})
public class PermissaoInserirServlet extends InserirServlet<Permissao> {

    private final PermissaoDao permissaoDao = new PermissaoDao();

    /**
     * Pega os parâmetros "nomePermissao" e "descricao" da requisição
     * e constrói um objeto Permissao.
     */
    @Override
    public Permissao construirEntidadeDaRequisicao(RequisicaoResposta requisicaoResposta) throws IllegalArgumentException {
        String nomePermissao = requisicaoResposta.recuperarParametroDaRequisicao("nomePermissao");
        String descricao = requisicaoResposta.recuperarParametroDaRequisicao("descricao");

        if (nomePermissao == null || nomePermissao.trim().isEmpty() || descricao == null || descricao.trim().isEmpty()) {
            throw new IllegalArgumentException("Preencha todas as informações.");
        }
        return new Permissao(nomePermissao, descricao);
    }

    /**
     * Implementação que chama o DAO do Permissao para inserir.
     */
    @Override
    public void chamarDaoParaInserir(Permissao permissao) throws NaoHouveAlteracaoNoBancoDeDadosException, DaoException {
        // Chama o método inserir do DAO específico
        this.permissaoDao.inserir(permissao);
    }

    /**
     * Após inserir com sucesso, redireciona de volta para a servlet de registros.
     */
    @Override
    public String enderecoDeDespache() {
        return "/permissao/registros";
    }

}