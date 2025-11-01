package simbia.app.crud.servlet.inserir;

import jakarta.servlet.annotation.WebServlet;
import simbia.app.crud.dao.CategoriaProdutoDao;
import simbia.app.crud.dao.PermissaoDao;
import simbia.app.crud.dao.TipoIndustriaDao;
import simbia.app.crud.infra.dao.abstractclasses.DaoException;
import simbia.app.crud.infra.dao.exception.errosDeOperacao.NaoHouveAlteracaoNoBancoDeDadosException;
import simbia.app.crud.infra.servlet.abstractclasses.InserirServlet;
import simbia.app.crud.model.dao.CategoriaProduto;
import simbia.app.crud.model.dao.Permissao;
import simbia.app.crud.model.dao.TipoIndustria;
import simbia.app.crud.model.servlet.RequisicaoResposta;

/**
 * Servlet concreta para INSERIR uma novo categoria de produto.
 * Mapeada para a URL /tipo-industria/inserir e responde a requisições POST.
 */
@WebServlet(name = "TipoIndustriaInserirServlet", urlPatterns = {"/tipo-industria/inserir"})
public class TipoIndustriaInserirServlet extends InserirServlet<TipoIndustria> {

    private final TipoIndustriaDao tipoIndustriaDao = new TipoIndustriaDao();

    /**
     * Pega os parâmetros "nomeTipoIndustrua" e "descricao" da requisição
     * e constrói um objeto TipoIndustria.
     */
    @Override
    public TipoIndustria construirEntidadeDaRequisicao(RequisicaoResposta requisicaoResposta) throws IllegalArgumentException {
        String nomeTipoIndustria = requisicaoResposta.recuperarParametroDaRequisicao("nomeTipoIndustria");
        String descricao = requisicaoResposta.recuperarParametroDaRequisicao("descricao");

        if (nomeTipoIndustria == null || nomeTipoIndustria.trim().isEmpty() || descricao == null || descricao.trim().isEmpty()) {
            throw new IllegalArgumentException("Preencha todas as informações.");
        }
        return new TipoIndustria(nomeTipoIndustria, descricao);
    }

    /**
     * Implementação que chama o DAO do TipoIndustria para inserir.
     */
    @Override
    public void chamarDaoParaInserir(TipoIndustria tipoIndustria) throws NaoHouveAlteracaoNoBancoDeDadosException, DaoException {
        // Chama o método inserir do DAO específico
        this.tipoIndustriaDao.inserir(tipoIndustria);
    }

    /**
     * Após inserir com sucesso, redireciona de volta para a servlet de registros.
     */
    @Override
    public String enderecoDeDespache() {
        return "/tipo-industria/registros";
    }

}