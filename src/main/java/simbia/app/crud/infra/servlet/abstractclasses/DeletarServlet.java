package simbia.app.crud.infra.servlet.abstractclasses;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import simbia.app.crud.infra.dao.abstractclasses.DaoException;
import simbia.app.crud.infra.dao.exception.errosDeOperacao.NaoHouveAlteracaoNoBancoDeDadosException;
import simbia.app.crud.infra.servlet.exception.ErrosDeDevolucaoParaClient;
import simbia.app.crud.model.servlet.RequisicaoResposta;
import java.io.IOException;

/**
 * Servlet abstrato para deletar um registro de uma tabela com base em um ID.
 *
 * Esta servlet responde APENAS a requisições POST para garantir que a deleção
 * seja uma ação intencional e não acionada por links ou crawlers.
 *
 * Fluxo: (POST) recupera "id" → chama DAO → redireciona para servlet de registros
 */
public abstract class DeletarServlet extends HttpServlet {

    /**
     * Processa a requisição POST: extrai o ID, executa a deleção e redireciona.
     */
    @Override
    protected void doPost(HttpServletRequest requisicao, HttpServletResponse resposta)
            throws ServletException, IOException {

        RequisicaoResposta requisicaoResposta = new RequisicaoResposta(requisicao, resposta);
        String paginaDeErro = enderecoDeDespacheCasoErro();

        try {
            // 1. Recuperar e validar o ID da requisição
            String idParametro = requisicaoResposta.recuperarParametroDaRequisicao("id");

            long id = Long.parseLong(idParametro);

            // 2. Chamar a implementação do DAO para deletar
            chamarDaoParaDeletar(id);

            // 3. Sucesso: Redireciona para a servlet de registros
            requisicaoResposta.redirecionarPara(enderecoDeDespache());

        } catch (NaoHouveAlteracaoNoBancoDeDadosException causa) {
            requisicaoResposta.adicionarAtributoNaRequisicao("erro", ErrosDeDevolucaoParaClient.NAO_HOUVE_ALTERACAO_NO_BANCO);
            requisicaoResposta.despacharPara(paginaDeErro);

        } catch (DaoException causa){
            causa.printStackTrace();
            requisicaoResposta.adicionarAtributoNaRequisicao("erro", ErrosDeDevolucaoParaClient.ERRO_DE_COMUNICACAO_COM_O_BANCO_DE_DADOS);
            requisicaoResposta.despacharPara(paginaDeErro);

        }
    }

    /**
     * Contrato para a subclasse. Deve chamar o método deletar(id) do DAO específico.
     */
    public abstract void chamarDaoParaDeletar(long id) throws NaoHouveAlteracaoNoBancoDeDadosException, DaoException;

    /**
     * Retorna a URL da servlet de registros
     * para onde o usuário será redirecionado após a deleção bem-sucedida.
     */
    public abstract String enderecoDeDespache();

    /**
     * Retorna o caminho da página de erro
     */
    public abstract String enderecoDeDespacheCasoErro();
}