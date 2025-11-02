package simbia.app.crud.servlet.inserir;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import simbia.app.crud.dao.VantagemDao;
import simbia.app.crud.infra.dao.abstractclasses.DaoException;
import simbia.app.crud.infra.dao.exception.errosDeOperacao.NaoHouveAlteracaoNoBancoDeDadosException;
import simbia.app.crud.infra.dao.exception.errosDoBancoDeDados.*;
import simbia.app.crud.infra.servlet.abstractclasses.InserirServlet;
import simbia.app.crud.infra.servlet.abstractclasses.OperacoesException;
import simbia.app.crud.model.dao.Vantagem;
import simbia.app.crud.model.servlet.RequisicaoResposta;

import java.io.IOException;

/**
 * Servlet para inserir novas vantagens no sistema.
 */
@WebServlet("/vantagem/inserir")
public class VantagemInserirServlet extends InserirServlet<Vantagem> {

    /**
     * Processa a requisição POST para inserir uma vantagem.
     * Valida os dados, insere no banco e trata possíveis erros.
     */
    @Override
    protected void doPost(HttpServletRequest requisicao, HttpServletResponse resposta) throws ServletException, IOException {
        RequisicaoResposta requisicaoResposta = new RequisicaoResposta(requisicao, resposta);

        try{
            Vantagem registro = recuperarNovoRegistroNaRequisicao(requisicaoResposta);
            inserirRegistroNoBanco(registro);

            requisicaoResposta.redirecionarPara(enderecoDeRedirecionamento());

        } catch (NaoHouveAlteracaoNoBancoDeDadosException causa) {
            requisicaoResposta.adicionarAtributoNaRequisicao("mensagem", "Operação falhou! Tente novamente.");
            requisicaoResposta.adicionarAtributoNaRequisicao("popupAdicionarAberto", false);

            requisicaoResposta.redirecionarPara(enderecoDeRedirecionamentoCasoErro());

        } catch (ViolacaoDeObrigatoriedadeException causa) {
            requisicaoResposta.adicionarAtributoNaRequisicao("mensagem", "Campo obrigatório.");
            requisicaoResposta.adicionarAtributoNaRequisicao("popupAdicionarAberto", true);
            requisicaoResposta.adicionarAtributoNaRequisicao("dados", dados(requisicaoResposta));

            requisicaoResposta.redirecionarPara(enderecoDeRedirecionamentoCasoErro());

        } catch (ViolacaoDeUnicidadeException causa) {
            requisicaoResposta.adicionarAtributoNaRequisicao("mensagem", "Campo já possui registro com esse valor.");
            requisicaoResposta.adicionarAtributoNaRequisicao("popupAdicionarAberto", true);
            requisicaoResposta.adicionarAtributoNaRequisicao("dados", dados(requisicaoResposta));

            requisicaoResposta.redirecionarPara(enderecoDeRedirecionamentoCasoErro());

        } catch (FalhaDeConexaoDriverInadequadoException | FalhaDeConexaoGeralException |
                 FalhaDeConexaoBancoDeDadosInexistenteException | FalhaDeConexaoQuedaRepentina |
                 FalhaDeConexaoSenhaIncorretaException causa) {
            requisicaoResposta.adicionarAtributoNaRequisicao("mensagem", "Erro de conexão! Tente novamente.");
            requisicaoResposta.adicionarAtributoNaRequisicao("popupAdicionarAberto", false);

            requisicaoResposta.redirecionarPara(enderecoDeRedirecionamentoCasoErro());
        }
    }

    /**
     * Insere a vantagem no banco de dados usando o DAO.
     */
    @Override
    public void inserirRegistroNoBanco(Vantagem entidade) throws DaoException, OperacoesException {
        VantagemDao dao = new VantagemDao();
        dao.inserir(entidade);
    }

    /**
     * Recupera os dados do formulário de cadastro.
     * Retorna um objeto Vantagem pronto para ser inserido.
     */
    @Override
    public Vantagem recuperarNovoRegistroNaRequisicao(RequisicaoResposta requisicaoResposta) {
        String nomeVantagem = requisicaoResposta.recuperarParametroDaRequisicao("nomeVantagem");
        String descricao = requisicaoResposta.recuperarParametroDaRequisicao("descricao");

        return new Vantagem(nomeVantagem, descricao);
    }

    /**
     * Retorna o endereço para onde redirecionar após inserção bem-sucedida.
     */
    @Override
    public String enderecoDeRedirecionamento() {
        return "/vantagem/atualizar";
    }

    /**
     * Retorna o endereço para onde despachar em caso de erro.
     */
    @Override
    public String enderecoDeRedirecionamentoCasoErro() {
        return "/vantagem.jsp";
    }

    /**
     * Recupera os dados do formulário e retorna como string formatada.
     * Formato: "nomeVantagem;descricao"
     */
    private static String dados(RequisicaoResposta requisicaoResposta){
        String nomeVantagem = requisicaoResposta.recuperarParametroDaRequisicao("nomeVantagem");
        String descricao = requisicaoResposta.recuperarParametroDaRequisicao("descricao");

        StringBuilder dados = new StringBuilder();

        dados.append(nomeVantagem != null ? nomeVantagem : "null").append(";");
        dados.append(descricao != null ? descricao : "null");

        return dados.toString();
    }
}