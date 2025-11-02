package simbia.app.crud.servlet.editar;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import simbia.app.crud.dao.CategoriaProdutoDao;
import simbia.app.crud.infra.dao.abstractclasses.DaoException;
import simbia.app.crud.infra.dao.exception.errosDoBancoDeDados.ViolacaoDeUnicidadeException;
import simbia.app.crud.infra.servlet.abstractclasses.EditarServlet;
import simbia.app.crud.infra.servlet.abstractclasses.OperacoesException;
import simbia.app.crud.model.dao.CategoriaProduto;
import simbia.app.crud.model.servlet.RequisicaoResposta;
import simbia.app.crud.util.ValidacoesDeDados;

import java.io.IOException;

@WebServlet("/categoria-produto/alterar")
public class CategoriaProdutoEditarServlet extends EditarServlet<CategoriaProduto> {

    /**
     * Processa a requisição POST para inserir uma categoria de produto.
     * Valida os dados, insere no banco e trata possíveis erros.
     */
    @Override
    protected void doPost(HttpServletRequest requisicao, HttpServletResponse resposta)
            throws ServletException, IOException {
        RequisicaoResposta requisicaoResposta = new RequisicaoResposta(requisicao, resposta);

        try {
            // Recupera dados do formulário
            String nome = requisicaoResposta.recuperarParametroDaRequisicao("nome");
            String descricao = requisicaoResposta.recuperarParametroDaRequisicao("descricao");

            // VALIDAÇÃO UNIFICADA - Nome + Descrição
            ValidacoesDeDados.ResultadoValidacao resultado =
                    ValidacoesDeDados.validarNomeDescricao(nome, descricao, "CategoriaProduto");

            // Se houver erros, retorna para o popup
            if (resultado.temErros()) {
                requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("erros", resultado.toJSON());
                requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("dados", nome + ";" + descricao);
                requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("popupAberto", "true");
                requisicaoResposta.redirecionarPara("/categoria-produto.jsp");
                return;
            }

            // Se passou nas validações, insere no banco
            CategoriaProduto categoria = new CategoriaProduto(nome, descricao);
            CategoriaProdutoDao dao = new CategoriaProdutoDao();
            dao.inserir(categoria);

            requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("status", true);
            requisicaoResposta.redirecionarPara("/categoria-produto/atualizar");

        } catch (ViolacaoDeUnicidadeException causa) {
            // Trata erro de nome duplicado
            String errosJSON = "{\"nome\":\"Esta categoria já está cadastrada\"}";
            requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("erros", errosJSON);
            requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("popupAberto", "true");
            requisicaoResposta.redirecionarPara("/categoria-produto.jsp");

        } catch (DaoException causa) {
            causa.printStackTrace();
            requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("status", false);
            requisicaoResposta.redirecionarPara("/categoria-produto.jsp");
        }
    }

    /**
     * Insere a categoria no banco de dados usando o DAO.
     */
    @Override
    public void editarRegistroNoBanco(CategoriaProduto entidade) throws DaoException, OperacoesException {
        CategoriaProdutoDao dao = new CategoriaProdutoDao();

        dao.atualizar(entidade);
    }

    /**
     * Recupera os dados do formulário de cadastro.
     * Retorna um objeto CategoriaProduto pronto para ser inserido.
     */
    @Override
    public CategoriaProduto recuperarRegistroEmEdicaoNaRequisicao(RequisicaoResposta requisicaoResposta) {
        String nome = requisicaoResposta.recuperarParametroDaRequisicao("nome");
        String descricao = requisicaoResposta.recuperarParametroDaRequisicao("descricao");

        return new CategoriaProduto(nome, descricao);
    }

    /**
     * Retorna o endereço para onde redirecionar após inserção bem-sucedida.
     */
    @Override
    public String enderecoDeRedirecionamento() {
        return "/categoria-produto/atualizar";
    }

    /**
     * Retorna o endereço para onde despachar em caso de erro.
     */
    @Override
    public String enderecoDeRedirecionamentoCasoErro() {
        return "/categoria-produto.jsp";
    }
}
