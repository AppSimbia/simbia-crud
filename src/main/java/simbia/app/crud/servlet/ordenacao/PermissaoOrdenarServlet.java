package simbia.app.crud.servlet.ordenacao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import simbia.app.crud.dao.PermissaoDao;
import simbia.app.crud.infra.dao.abstractclasses.DaoException;
import simbia.app.crud.infra.servlet.abstractclasses.OrdenarServlet;
import simbia.app.crud.infra.servlet.exception.operacao.RequisicaoSemRegistrosException;
import simbia.app.crud.infra.servlet.exception.operacao.RequisicaoSemTipoOrdenacaoException;
import simbia.app.crud.model.dao.Permissao;
import simbia.app.crud.model.ordenacao.OrdenacaoFactory;
import simbia.app.crud.model.servlet.RequisicaoResposta;
import simbia.app.crud.util.ValidacoesDeDados;

import java.io.IOException;
import java.util.List;

@WebServlet("/permissao/ordenar")
public class PermissaoOrdenarServlet extends HttpServlet {

    private static final String CHAVE_REGISTROS = "permissaoRegistros";

    @Override
    protected void service(HttpServletRequest requisicao, HttpServletResponse resposta)
            throws ServletException, IOException {

        RequisicaoResposta requisicaoResposta = new RequisicaoResposta(requisicao, resposta);

        try {
            List<Permissao> registros = recuperarRegistros(requisicaoResposta);
            String tipoOrdenacao = requisicaoResposta.recuperarParametroDaRequisicao("tipoOrdenacao");
            String ordem = requisicaoResposta.recuperarParametroDaRequisicao("ordem");

            ValidacoesDeDados.validarTipoDeOrdenacao(tipoOrdenacao);

            boolean crescente = isOrdemCrescente(ordem);
            ordenarRegistros(registros, tipoOrdenacao, crescente);

            salvarEstadoOrdenacao(requisicaoResposta, registros, tipoOrdenacao, crescente);
            requisicaoResposta.despacharPara("../permissao.jsp");

        } catch (RequisicaoSemRegistrosException | RequisicaoSemTipoOrdenacaoException e) {
            e.printStackTrace();
            requisicaoResposta.redirecionarPara("/permissao/registros");
        } catch (DaoException e) {
            e.printStackTrace();
            requisicaoResposta.redirecionarPara("/permissao/erroEmRegistros.jsp");
        }
    }


     private List<Permissao> recuperarRegistros(RequisicaoResposta requisicaoResposta)
            throws DaoException, RequisicaoSemRegistrosException {
        
        List<Permissao> registros =
                (List<Permissao>) requisicaoResposta.recuperarAtributoDaRequisicao(CHAVE_REGISTROS);

        if (registros == null || registros.isEmpty()) {
            registros = new PermissaoDao().recuperarTudo();
        }

        ValidacoesDeDados.validarRegistros(registros);
        return registros;
    }


    private boolean isOrdemCrescente(String ordem) {
        return ordem == null || !ordem.equalsIgnoreCase("desc");
    }

    private void ordenarRegistros(List<Permissao> registros, String tipoOrdenacao, boolean crescente)
            throws RequisicaoSemTipoOrdenacaoException {

        OrdenarServlet<Permissao> ordenador =
                OrdenacaoFactory.criarParaPermissao(tipoOrdenacao, crescente);

        ordenador.ordenarComLog(registros);
    }

    private void salvarEstadoOrdenacao(RequisicaoResposta requisicaoResposta, List<Permissao> registros,
                                       String tipoOrdenacao, boolean crescente) {

        requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao(CHAVE_REGISTROS, registros);
        requisicaoResposta.adicionarAtributoNaRequisicao("criterioOrdenacao", tipoOrdenacao);
        requisicaoResposta.adicionarAtributoNaRequisicao("ordemAtual", crescente ? "asc" : "desc");
    }
}
