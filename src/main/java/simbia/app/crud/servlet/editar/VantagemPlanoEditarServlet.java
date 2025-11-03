package simbia.app.crud.servlet.editar;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import simbia.app.crud.dao.VantagemPlanoDao;
import simbia.app.crud.infra.dao.abstractclasses.DaoException;
import simbia.app.crud.infra.dao.exception.errosDeOperacao.NaoHouveAlteracaoNoBancoDeDadosException;
import simbia.app.crud.infra.dao.exception.errosDoBancoDeDados.ViolacaoDeUnicidadeException;
import simbia.app.crud.infra.servlet.abstractclasses.EditarServlet;
import simbia.app.crud.infra.servlet.abstractclasses.OperacoesException;
import simbia.app.crud.infra.servlet.abstractclasses.ValidacaoDeDadosException;
import simbia.app.crud.model.dao.VantagemPlano;
import simbia.app.crud.model.servlet.RequisicaoResposta;
import simbia.app.crud.util.ValidacoesDeDados;

import java.io.IOException;

@WebServlet("/vantagem-plano/alterar")
public class VantagemPlanoEditarServlet extends EditarServlet<VantagemPlano> {

    @Override
    protected void doPost(HttpServletRequest requisicao, HttpServletResponse resposta)
            throws ServletException, IOException {
        RequisicaoResposta requisicaoResposta = new RequisicaoResposta(requisicao, resposta);

        try {
            String idPlano = requisicaoResposta.recuperarParametroDaRequisicao("idPlano");
            String idVantagem = requisicaoResposta.recuperarParametroDaRequisicao("idVantagem");

            ValidacoesDeDados.ResultadoValidacao resultado =
                    ValidacoesDeDados.validarVantagemPlano(idPlano, idVantagem);

            if (resultado.temErros()) {
                String id = requisicaoResposta.recuperarParametroDaRequisicao("id");
                requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("erros", resultado.toJSON());
                requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("dados",
                        idPlano + ";" + idVantagem + ";" + id);
                requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("popupAberto", "true");

                requisicaoResposta.redirecionarPara("/vantagem-plano.jsp");
                return;
            }

            VantagemPlano vantagemPlano = recuperarRegistroEmEdicaoNaRequisicao(requisicaoResposta);
            editarRegistroNoBanco(vantagemPlano);

            requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("status", true);
            requisicaoResposta.redirecionarPara("/vantagem-plano/atualizar");

        } catch (ViolacaoDeUnicidadeException causa) {
            String errosJSON = "{\"combinação\":\"Esta combinação já existe\"}";
            requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("erros", errosJSON);
            requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("popupAberto", "true");
            requisicaoResposta.redirecionarPara("/vantagem-plano.jsp");

        } catch (NaoHouveAlteracaoNoBancoDeDadosException causa) {
            requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("status", true);
            requisicaoResposta.redirecionarPara("/vantagem-plano/atualizar");

        } catch (DaoException | OperacoesException | ValidacaoDeDadosException causa) {
            causa.printStackTrace();
            requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("status", false);
            requisicaoResposta.redirecionarPara("/vantagem-plano.jsp");
        }
    }

    @Override
    public void editarRegistroNoBanco(VantagemPlano entidade) throws DaoException, OperacoesException {
        VantagemPlanoDao dao = new VantagemPlanoDao();
        dao.atualizar(entidade);
    }

    @Override
    public VantagemPlano recuperarRegistroEmEdicaoNaRequisicao(RequisicaoResposta requisicaoResposta)
            throws DaoException, OperacoesException, ValidacaoDeDadosException {

        String idStr = requisicaoResposta.recuperarParametroDaRequisicao("id");
        String idPlano = requisicaoResposta.recuperarParametroDaRequisicao("idPlano");
        String idVantagem = requisicaoResposta.recuperarParametroDaRequisicao("idVantagem");

        long id = Long.parseLong(idStr);
        long longIdPlano = Long.parseLong(idPlano);
        long longIdVantagem = Long.parseLong(idVantagem);

        VantagemPlano vantagemPlano = new VantagemPlano(longIdPlano, longIdVantagem);

        vantagemPlano.setIdVantagemPlano(id);

        return vantagemPlano;
    }

    @Override
    public String enderecoDeRedirecionamento() {
        return "/vantagem-plano/atualizar";
    }

    @Override
    public String enderecoDeRedirecionamentoCasoErro() {
        return "/vantagem-plano.jsp";
    }
}