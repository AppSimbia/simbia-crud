package simbia.app.crud.servlet.inserir;

import jakarta.servlet.annotation.WebServlet;
import simbia.app.crud.dao.CategoriaProdutoDao;
import simbia.app.crud.dao.PermissaoDao;
import simbia.app.crud.dao.PlanoDao;
import simbia.app.crud.infra.dao.abstractclasses.DaoException;
import simbia.app.crud.infra.dao.exception.errosDeOperacao.NaoHouveAlteracaoNoBancoDeDadosException;
import simbia.app.crud.infra.servlet.abstractclasses.InserirServlet;
import simbia.app.crud.model.dao.CategoriaProduto;
import simbia.app.crud.model.dao.Permissao;
import simbia.app.crud.model.dao.Plano;
import simbia.app.crud.model.servlet.RequisicaoResposta;

import java.math.BigDecimal;

/**
 * Servlet concreta para INSERIR uma novo plano.
 * Mapeada para a URL /plano/inserir e responde a requisições POST.
 */
@WebServlet(name = "PlanoInserirServlet", urlPatterns = {"/plano/inserir"})
public class PlanoInserirServlet extends InserirServlet<Plano> {

    private final PlanoDao planoDao = new PlanoDao();

    /**
     * Pega os parâmetros "valor", "ativo" e "nomePlano" da requisição
     * e constrói um objeto Plano.
     */
    @Override
    public Plano construirEntidadeDaRequisicao(RequisicaoResposta requisicaoResposta) throws IllegalArgumentException {

        String valorStr = requisicaoResposta.recuperarParametroDaRequisicao("valor");
        String ativoStr = requisicaoResposta.recuperarParametroDaRequisicao("ativo");
        String nomePlano = requisicaoResposta.recuperarParametroDaRequisicao("nomePlano");

        if (nomePlano == null || nomePlano.trim().isEmpty() ||
                valorStr == null || valorStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do plano e Valor são obrigatórios.");
        }

        boolean bAtivo = Boolean.parseBoolean(ativoStr);

        BigDecimal nValor;
        try {
            String valorCorrigido = valorStr.replace(",", ".");
            nValor = new BigDecimal(valorCorrigido);

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("O Valor informado (" + valorStr + ") não é um número válido.");
        }

        return new Plano(nValor, bAtivo, nomePlano);
    }

    /**
     * Implementação que chama o DAO do Plano para inserir.
     */
    @Override
    public void chamarDaoParaInserir(Plano plano) throws NaoHouveAlteracaoNoBancoDeDadosException, DaoException {
        // Chama o método inserir do DAO específico
        this.planoDao.inserir(plano);
    }

    /**
     * Após inserir com sucesso, redireciona de volta para a servlet de registros.
     */
    @Override
    public String enderecoDeDespache() {
        return "/permissao/registros";
    }

}