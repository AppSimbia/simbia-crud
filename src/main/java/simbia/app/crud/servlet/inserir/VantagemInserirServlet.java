package simbia.app.crud.servlet.inserir;

import jakarta.servlet.annotation.WebServlet;
import simbia.app.crud.dao.VantagemDao;
import simbia.app.crud.infra.dao.abstractclasses.DaoException;
import simbia.app.crud.infra.dao.exception.errosDeOperacao.NaoHouveAlteracaoNoBancoDeDadosException;
import simbia.app.crud.infra.servlet.abstractclasses.InserirServlet;
import simbia.app.crud.model.dao.Vantagem;
import simbia.app.crud.model.servlet.RequisicaoResposta;

/**
 * Servlet concreta para INSERIR uma novo categoria de produto.
 * Mapeada para a URL /vantagem/inserir e responde a requisições POST.
 */
@WebServlet(name = "VantagemServlet", urlPatterns = {"/vantagem/inserir"})
public class VantagemInserirServlet extends InserirServlet<Vantagem> {

    private final VantagemDao vantagemDao = new VantagemDao();

    /**
     * Pega os parâmetros "nomeVantagem" e "descricao" da requisição
     * e constrói um objeto Vantagem.
     */
    @Override
    public Vantagem construirEntidadeDaRequisicao(RequisicaoResposta requisicaoResposta) throws IllegalArgumentException {
        String nomeVantagem = requisicaoResposta.recuperarParametroDaRequisicao("nomeTipoIndustria");
        String descricao = requisicaoResposta.recuperarParametroDaRequisicao("descricao");

        if (nomeVantagem == null || nomeVantagem.trim().isEmpty() || descricao == null || descricao.trim().isEmpty()) {
            throw new IllegalArgumentException("Preencha todas as informações.");
        }
        return new Vantagem(nomeVantagem, descricao);
    }

    /**
     * Implementação que chama o DAO do Vantagem para inserir.
     */
    @Override
    public void chamarDaoParaInserir(Vantagem vantagem) throws NaoHouveAlteracaoNoBancoDeDadosException, DaoException {
        // Chama o método inserir do DAO específico
        this.vantagemDao.inserir(vantagem);
    }


    /**
     * Após inserir com sucesso, redireciona de volta para a servlet de registros.
     */
    @Override
    public String enderecoDeDespache() {
        return "/vantagem/registros";
    }

}