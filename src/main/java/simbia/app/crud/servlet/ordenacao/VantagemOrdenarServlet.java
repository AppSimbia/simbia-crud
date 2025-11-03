package simbia.app.crud.servlet.ordenacao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import simbia.app.crud.dao.VantagemDao;
import simbia.app.crud.infra.dao.abstractclasses.DaoException;
import simbia.app.crud.infra.servlet.abstractclasses.OrdenarServlet;
import simbia.app.crud.infra.servlet.exception.operacao.RequisicaoSemRegistrosException;
import simbia.app.crud.infra.servlet.exception.operacao.RequisicaoSemTipoOrdenacaoException;
import simbia.app.crud.model.dao.Vantagem;
import simbia.app.crud.model.ordenacao.OrdenacaoFactory;
import simbia.app.crud.model.servlet.RequisicaoResposta;
import simbia.app.crud.util.ValidacoesDeDados;

import java.io.IOException;
import java.util.List;

/**
 * Classe de servlet de ordenação dos registros em exibição.
 */
@WebServlet("/vantagem/ordenar")
public class VantagemOrdenarServlet extends HttpServlet {

    private static final String CHAVE_REGISTROS = "vantagemRegistros";

    @Override
    protected void service(HttpServletRequest requisicao, HttpServletResponse resposta)
            throws ServletException, IOException {

        RequisicaoResposta requisicaoResposta = new RequisicaoResposta(requisicao, resposta);

        try {
            List<Vantagem> registros = recuperarRegistros(requisicaoResposta);
            String tipoOrdenacao = requisicaoResposta.recuperarParametroDaRequisicao("tipoOrdenacao");
            String ordem = requisicaoResposta.recuperarParametroDaRequisicao("ordem");
            System.out.println(">>> tipoOrdenacao: " + requisicaoResposta.recuperarParametroDaRequisicao("tipoOrdenacao"));
            System.out.println(">>> ordem: " + requisicaoResposta.recuperarParametroDaRequisicao("ordem"));
            ValidacoesDeDados.validarTipoDeOrdenacao(tipoOrdenacao);

            boolean crescente = isOrdemCrescente(ordem);
            ordenarRegistros(registros, tipoOrdenacao, crescente);

            salvarEstadoOrdenacao(requisicaoResposta, registros, tipoOrdenacao, crescente);
            requisicaoResposta.despacharPara("../vantagem.jsp");

        } catch (RequisicaoSemRegistrosException | RequisicaoSemTipoOrdenacaoException e) {
            e.printStackTrace();
            requisicaoResposta.redirecionarPara("/vantagem/registros");
        } catch (DaoException e) {
            e.printStackTrace();
            requisicaoResposta.redirecionarPara("/vantagem/erroEmRegistros.jsp");
        }
    }


    private List<Vantagem> recuperarRegistros(RequisicaoResposta requisicaoResposta)
            throws DaoException, RequisicaoSemRegistrosException {

        List<Vantagem> registros =
                (List<Vantagem>) requisicaoResposta.recuperarAtributoDaRequisicao(CHAVE_REGISTROS);

        if (registros == null || registros.isEmpty()) {
            registros = new VantagemDao().recuperarTudo();
        }

        ValidacoesDeDados.validarRegistros(registros);
        return registros;
    }


    private boolean isOrdemCrescente(String ordem) {
        return ordem == null || !ordem.equalsIgnoreCase("desc");
    }

    private void ordenarRegistros(List<Vantagem> registros, String tipoOrdenacao, boolean crescente)
            throws RequisicaoSemTipoOrdenacaoException {

        OrdenarServlet<Vantagem> ordenador =
                OrdenacaoFactory.criarParaVantagem(tipoOrdenacao, crescente);

        ordenador.ordenarComLog(registros);
    }

    private void salvarEstadoOrdenacao(RequisicaoResposta requisicaoResposta, List<Vantagem> registros,
                                       String tipoOrdenacao, boolean crescente) {

        requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao(CHAVE_REGISTROS, registros);
        requisicaoResposta.adicionarAtributoNaRequisicao("criterioOrdenacao", tipoOrdenacao);
        requisicaoResposta.adicionarAtributoNaRequisicao("ordemAtual", crescente ? "asc" : "desc");
    }
}
