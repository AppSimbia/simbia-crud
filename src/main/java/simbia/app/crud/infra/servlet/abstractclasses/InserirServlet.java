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
 * Servlet abstrato para INSERIR um novo registro de uma entidade <T> no banco.
 *
 * Fluxo: (POST) recupera parâmetros do form -> constrói a entidade T
 * -> chama o DAO.inserir(T)
 * -> redireciona para a servlet de registros.
 */
public abstract class InserirServlet<T> extends HttpServlet {

    /**
     * Processa a requisição POST: constrói a entidade, executa a inserção e redireciona.
     */
    @Override
    protected void doPost(HttpServletRequest requisicao, HttpServletResponse resposta)
            throws ServletException, IOException {

        RequisicaoResposta requisicaoResposta = new RequisicaoResposta(requisicao, resposta);

        try {
            // 1. A subclasse irá implementar este método para pegar os parâmetros
            // (ex: "nome", "email", "senha") e criar a entidade.
            T entidade = construirEntidadeDaRequisicao(requisicaoResposta);

            // 2. A subclasse irá implementar a chamada ao DAO.inserir()
            chamarDaoParaInserir(entidade);

            // 3. Sucesso: Redireciona para a servlet de registros
            requisicaoResposta.redirecionarPara(enderecoDeDespache());

        } catch (NaoHouveAlteracaoNoBancoDeDadosException causa) {
            // Erro: DAO não inseriu
            requisicaoResposta.adicionarAtributoNaRequisicao("erro", ErrosDeDevolucaoParaClient.NAO_HOUVE_ALTERACAO_NO_BANCO);

        } catch (DaoException causa) {
            // Erro: Falha de banco
            causa.printStackTrace();
            requisicaoResposta.adicionarAtributoNaRequisicao("erro", ErrosDeDevolucaoParaClient.ERRO_DE_COMUNICACAO_COM_O_BANCO_DE_DADOS);

        }
    }

    /**
     * Contrato para a subclasse.
     * Deve recuperar os parâmetros da requisição (ex: "nome", "email", "senha")
     * e retornar um objeto T preenchido.
     *
     * @param requisicaoResposta
     * @return A entidade T pronta para ser inserida.
     * @throws IllegalArgumentException Se os dados da requisição forem inválidos (ex: campos vazios).
     */
    public abstract T construirEntidadeDaRequisicao(RequisicaoResposta requisicaoResposta) throws IllegalArgumentException;

    /**
     * Contrato para a subclasse.
     * Deve chamar o método inserir(entidade) do DAO específico.
     * Ex: new AdministradorDao().inserir(entidade);
     *
     * @param entidade A entidade preenchida vinda de 'construirEntidadeDaRequisicao'.
     * @throws DaoException Se ocorrer um erro de banco de dados.
     * @throws NaoHouveAlteracaoNoBancoDeDadosException Se o DAO falhar em inserir.
     */
    public abstract void chamarDaoParaInserir(T entidade) throws NaoHouveAlteracaoNoBancoDeDadosException, DaoException;

    /**
     * Retorna a URL da servlet de *registros* (ex: "/admin/registros")
     * para onde o usuário será redirecionado após a inserção bem-sucedida.
     */
    public abstract String enderecoDeDespache();

}