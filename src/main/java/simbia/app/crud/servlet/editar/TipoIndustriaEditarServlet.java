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
import simbia.app.crud.infra.servlet.exception.validacaoDeDados.PadraoEmailErradoException;
import simbia.app.crud.infra.servlet.exception.validacaoDeDados.PadraoNomeErradoException;
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
            // Recupera dados do formulário
            String nome = requisicaoResposta.recuperarParametroDaRequisicao("nome");
            String descricao = requisicaoResposta.recuperarParametroDaRequisicao("descricao");

            // VALIDAÇÃO UNIFICADA - UMA LINHA!
            ValidacoesDeDados.ResultadoValidacao resultado =
                    ValidacoesDeDados.validarNomeDescricao(nome, descricao, "TipoIndustria");

            // Se houver erros, retorna para o popup
            if (resultado.temErros()) {
                String errosJSON = resultado.toJSON();
                requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("erros", errosJSON);
                requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("dados",
                        nome + ";" + descricao);
                requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("popupAberto", "true");
                requisicaoResposta.redirecionarPara("/tipo-industria.jsp");
                return;
            }

            // Se passou nas validações, cria objeto e insere no banco
            TipoIndustria tipoIndustria = new TipoIndustria(nome, descricao);
            TipoIndustriaDao dao = new TipoIndustriaDao();
            dao.inserir(tipoIndustria);

            requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("status", true);
            requisicaoResposta.redirecionarPara("/tipo-industria/atualizar");

        } catch (ViolacaoDeUnicidadeException causa) {
            // Trata erro de nome duplicado
            String errosJSON = "{\"nome\":\"Este nome já está cadastrado\"}";
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
            throws PadraoNomeErradoException, PadraoEmailErradoException {
        String nome = requisicaoResposta.recuperarParametroDaRequisicao("nome");
        String descricao = requisicaoResposta.recuperarParametroDaRequisicao("descricao");

        ValidacoesDeDados.validarDescricao("descricao");
        ValidacoesDeDados.validarNome("nome");

        return new TipoIndustria(nome, descricao);
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
