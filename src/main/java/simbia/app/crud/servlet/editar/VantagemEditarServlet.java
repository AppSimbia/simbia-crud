package simbia.app.crud.servlet.editar;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import simbia.app.crud.dao.VantagemDao;
import simbia.app.crud.infra.dao.abstractclasses.DaoException;
import simbia.app.crud.infra.dao.exception.errosDoBancoDeDados.ViolacaoDeUnicidadeException;
import simbia.app.crud.infra.servlet.abstractclasses.EditarServlet;
import simbia.app.crud.infra.servlet.abstractclasses.OperacoesException;
import simbia.app.crud.infra.servlet.abstractclasses.ValidacaoDeDadosException;
import simbia.app.crud.model.dao.Vantagem;
import simbia.app.crud.model.servlet.RequisicaoResposta;
import simbia.app.crud.util.ValidacoesDeDados;

import java.io.IOException;

@WebServlet("/vantagem/alterar")
public class VantagemEditarServlet extends EditarServlet<Vantagem> {

    @Override
    protected void doPost(HttpServletRequest requisicao, HttpServletResponse resposta)
            throws ServletException, IOException {
        RequisicaoResposta requisicaoResposta = new RequisicaoResposta(requisicao, resposta);

        try {
            String nome = requisicaoResposta.recuperarParametroDaRequisicao("nome");
            String descricao = requisicaoResposta.recuperarParametroDaRequisicao("descricao");

            ValidacoesDeDados.ResultadoValidacao resultado =
                    ValidacoesDeDados.validarNomeDescricao(nome, descricao, "vantagem");

            if (resultado.temErros()) {
                String id = requisicaoResposta.recuperarParametroDaRequisicao("id");

                requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("erros", resultado.toJSON());
                requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("dados", nome + ";" + descricao + ";" + id);
                requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("popupAberto", "true");
                requisicaoResposta.redirecionarPara("/vantagem.jsp");
                return;
            }

            Vantagem vantagem = recuperarRegistroEmEdicaoNaRequisicao(requisicaoResposta);

            editarRegistroNoBanco(vantagem);

            requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("status", true);
            requisicaoResposta.redirecionarPara("/vantagem/atualizar");

        } catch (ViolacaoDeUnicidadeException causa) {
            String errosJSON = "{\"nome\":\"Esta vantagem já está cadastrada\"}";
            requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("erros", errosJSON);
            requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("popupAberto", "true");
            requisicaoResposta.redirecionarPara("/vantagem.jsp");

        } catch (DaoException causa) {
            causa.printStackTrace();
            requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("status", false);
            requisicaoResposta.redirecionarPara("/vantagem.jsp");
        }
    }

    @Override
    public void editarRegistroNoBanco(Vantagem entidade) throws DaoException, OperacoesException {
        VantagemDao dao = new VantagemDao();
        dao.atualizar(entidade);
    }

    @Override
    public Vantagem recuperarRegistroEmEdicaoNaRequisicao(RequisicaoResposta requisicaoResposta)
            throws DaoException, OperacoesException, ValidacaoDeDadosException {

        String idStr = requisicaoResposta.recuperarParametroDaRequisicao("id");
        String nome = requisicaoResposta.recuperarParametroDaRequisicao("nome");
        String descricao = requisicaoResposta.recuperarParametroDaRequisicao("descricao");

        long id = Long.parseLong(idStr);

        return new Vantagem(id, nome, descricao);
    }

    @Override
    public String enderecoDeRedirecionamento() {
        return "/vantagem/atualizar";
    }

    @Override
    public String enderecoDeRedirecionamentoCasoErro() {
        return "/vantagem.jsp";
    }
}