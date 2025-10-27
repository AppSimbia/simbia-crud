package simbia.app.crud.servlet.ordenacao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import simbia.app.crud.dao.AdministradorDao;
import simbia.app.crud.infra.dao.abstractclasses.DaoException;
import simbia.app.crud.infra.servlet.abstractclasses.OrdenarServlet;
import simbia.app.crud.infra.servlet.exception.RequisicaoSemRegistrosException;
import simbia.app.crud.infra.servlet.exception.RequisicaoSemTipoOrdenacaoException;
import simbia.app.crud.model.dao.Administrador;
import simbia.app.crud.model.ordenacao.OrdenacaoFactory;
import simbia.app.crud.model.servlet.RequisicaoResposta;
import simbia.app.crud.util.ValidacoesDeDados;

import java.io.IOException;
import java.util.List;

@WebServlet("/administrador/ordenar")
public class AdministradorOrdenarServlet extends HttpServlet {

    private static final String CHAVE_REGISTROS = "administradorRegistros";

    @Override
    protected void service(HttpServletRequest requisicao, HttpServletResponse resposta)
            throws ServletException, IOException {

        RequisicaoResposta requisicaoResposta = new RequisicaoResposta(requisicao, resposta);

        try {
            List<Administrador> registros = recuperarRegistros(requisicaoResposta);
            String tipoOrdenacao = requisicaoResposta.recuperarParametroDaRequisicao("tipoOrdenacao");
            String ordem = requisicaoResposta.recuperarParametroDaRequisicao("ordem");

            ValidacoesDeDados.validarTipoDeOrdenacao(tipoOrdenacao);

            boolean crescente = isOrdemCrescente(ordem);
            ordenarRegistros(registros, tipoOrdenacao, crescente);

            salvarEstadoOrdenacao(requisicaoResposta, registros, tipoOrdenacao, crescente);
            requisicaoResposta.despacharPara("../administrador.jsp");

        } catch (RequisicaoSemRegistrosException | RequisicaoSemTipoOrdenacaoException e) {
            e.printStackTrace();
            requisicaoResposta.redirecionarPara("/administrador/registros");
        } catch (DaoException e) {
            e.printStackTrace();
            requisicaoResposta.redirecionarPara("/administrador/erroEmRegistros.jsp");
        }
    }


     private List<Administrador> recuperarRegistros(RequisicaoResposta requisicaoResposta)
            throws DaoException, RequisicaoSemRegistrosException {

        @SuppressWarnings("unchecked")
        List<Administrador> registros =
                (List<Administrador>) requisicaoResposta.recuperarAtributoDaRequisicao(CHAVE_REGISTROS);

        if (registros == null || registros.isEmpty()) {
            registros = new AdministradorDao().recuperarTudo();
        }

        ValidacoesDeDados.validarRegistros(registros);
        return registros;
    }


    private boolean isOrdemCrescente(String ordem) {
        return ordem == null || !ordem.equalsIgnoreCase("desc");
    }

    private void ordenarRegistros(List<Administrador> registros, String tipoOrdenacao, boolean crescente)
            throws RequisicaoSemTipoOrdenacaoException {

        OrdenarServlet<Administrador> ordenador =
                OrdenacaoFactory.criarParaAdministrador(tipoOrdenacao, crescente);

        ordenador.ordenarComLog(registros);
    }

    private void salvarEstadoOrdenacao(RequisicaoResposta requisicaoResposta, List<Administrador> registros,
                                       String tipoOrdenacao, boolean crescente) {

        requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao(CHAVE_REGISTROS, registros);
        requisicaoResposta.adicionarAtributoNaRequisicao("criterioOrdenacao", tipoOrdenacao);
        requisicaoResposta.adicionarAtributoNaRequisicao("ordemAtual", crescente ? "asc" : "desc");
    }
}
