package simbia.app.crud.servlet.editar;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import simbia.app.crud.dao.TipoIndustriaDao;
import simbia.app.crud.infra.dao.abstractclasses.DaoException;
import simbia.app.crud.infra.dao.exception.errosDoBancoDeDados.ViolacaoDeUnicidadeException;
import simbia.app.crud.infra.servlet.abstractclasses.EditarServlet;
import simbia.app.crud.infra.servlet.abstractclasses.OperacoesException;
import simbia.app.crud.infra.servlet.abstractclasses.ValidacaoDeDadosException;
import simbia.app.crud.model.dao.TipoIndustria;
import simbia.app.crud.model.servlet.RequisicaoResposta;
import simbia.app.crud.util.ValidacoesDeDados;

import java.io.IOException;

@WebServlet("/tipo-industria/alterar")
public class TipoIndustriaEditarServlet extends EditarServlet<TipoIndustria> {

    @Override
    protected void doPost(HttpServletRequest requisicao, HttpServletResponse resposta)
            throws ServletException, IOException {
        RequisicaoResposta requisicaoResposta = new RequisicaoResposta(requisicao, resposta);

        try {
            String nome = requisicaoResposta.recuperarParametroDaRequisicao("nome");
            String descricao = requisicaoResposta.recuperarParametroDaRequisicao("descricao");

            ValidacoesDeDados.ResultadoValidacao resultado =
                    ValidacoesDeDados.validarNomeDescricao(nome, descricao, "Permissao");

            if (resultado.temErros()) {
                String id = requisicaoResposta.recuperarParametroDaRequisicao("id");

                requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("erros", resultado.toJSON());

                requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("dados", nome + ";" + descricao + ";" + id);


                requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("popupAberto", "true");
                requisicaoResposta.redirecionarPara("/tipo-industria.jsp");
                return;
            }

            TipoIndustria tipoIndustria = recuperarRegistroEmEdicaoNaRequisicao(requisicaoResposta);

            editarRegistroNoBanco(tipoIndustria);

            requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("status", true);
            requisicaoResposta.redirecionarPara("/tipo-industria/atualizar");

        } catch (ViolacaoDeUnicidadeException causa) {
            String errosJSON = "{\"nome\":\"Este tipo de indústria já está cadastrado\"}";
            requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("erros", errosJSON);
            requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("popupAberto", "true");
            requisicaoResposta.redirecionarPara("/tipo-industria.jsp");

        } catch (DaoException causa) {
            causa.printStackTrace();
            requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("status", false);
            requisicaoResposta.redirecionarPara("/tipo-industria.jsp");
        }
    }

    @Override
    public void editarRegistroNoBanco(TipoIndustria entidade) throws DaoException, OperacoesException {
        TipoIndustriaDao dao = new TipoIndustriaDao();
        dao.atualizar(entidade);
    }

    @Override
    public TipoIndustria recuperarRegistroEmEdicaoNaRequisicao(RequisicaoResposta requisicaoResposta)
            throws DaoException, OperacoesException, ValidacaoDeDadosException {

        String idStr = requisicaoResposta.recuperarParametroDaRequisicao("id");
        String nome = requisicaoResposta.recuperarParametroDaRequisicao("nome");
        String descricao = requisicaoResposta.recuperarParametroDaRequisicao("descricao");

        long id = Long.parseLong(idStr);

        return new TipoIndustria(id, nome, descricao);
    }

    @Override
    public String enderecoDeRedirecionamento() {
        return "/tipo-industria/atualizar";
    }

    @Override
    public String enderecoDeRedirecionamentoCasoErro() {
        return "/tipo-industria.jsp";
    }
}