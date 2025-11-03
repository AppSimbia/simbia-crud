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
import simbia.app.crud.infra.servlet.abstractclasses.ValidacaoDeDadosException;
import simbia.app.crud.model.dao.CategoriaProduto;
import simbia.app.crud.model.servlet.RequisicaoResposta;
import simbia.app.crud.util.ValidacoesDeDados;

import java.io.IOException;

@WebServlet("/categoria-produto/alterar")
public class CategoriaProdutoEditarServlet extends EditarServlet<CategoriaProduto> {

    @Override
    protected void doPost(HttpServletRequest requisicao, HttpServletResponse resposta)
            throws ServletException, IOException {
        RequisicaoResposta requisicaoResposta = new RequisicaoResposta(requisicao, resposta);

        try {
            String nome = requisicaoResposta.recuperarParametroDaRequisicao("nome");
            String descricao = requisicaoResposta.recuperarParametroDaRequisicao("descricao");

            ValidacoesDeDados.ResultadoValidacao resultado =
                    ValidacoesDeDados.validarNomeDescricao(nome, descricao, "CategoriaProduto");

            if (resultado.temErros()) {
                String id = requisicaoResposta.recuperarParametroDaRequisicao("id");

                requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("erros", resultado.toJSON());

                requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("dados", nome + ";" + descricao + ";" + id);

                requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("popupAberto", "true");
                requisicaoResposta.redirecionarPara("/categoria-produto.jsp");
                return;
            }

            CategoriaProduto categoria = recuperarRegistroEmEdicaoNaRequisicao(requisicaoResposta);

            editarRegistroNoBanco(categoria);

            requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("status", true);
            requisicaoResposta.redirecionarPara("/categoria-produto/atualizar");

        } catch (ViolacaoDeUnicidadeException causa) {
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

    @Override
    public void editarRegistroNoBanco(CategoriaProduto entidade) throws DaoException, OperacoesException {
        CategoriaProdutoDao dao = new CategoriaProdutoDao();
        dao.atualizar(entidade);
    }

    @Override
    public CategoriaProduto recuperarRegistroEmEdicaoNaRequisicao(RequisicaoResposta requisicaoResposta)
            throws DaoException, OperacoesException, ValidacaoDeDadosException {

        String idStr = requisicaoResposta.recuperarParametroDaRequisicao("id");
        String nome = requisicaoResposta.recuperarParametroDaRequisicao("nome");
        String descricao = requisicaoResposta.recuperarParametroDaRequisicao("descricao");

        long id = Long.parseLong(idStr);

        return new CategoriaProduto(id, nome, descricao);
    }

    @Override
    public String enderecoDeRedirecionamento() {
        return "/categoria-produto/atualizar";
    }

    @Override
    public String enderecoDeRedirecionamentoCasoErro() {
        return "/categoria-produto.jsp";
    }
}