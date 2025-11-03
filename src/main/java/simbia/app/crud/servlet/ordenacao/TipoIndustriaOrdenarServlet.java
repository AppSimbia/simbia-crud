package simbia.app.crud.servlet.ordenacao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import simbia.app.crud.dao.TipoIndustriaDao;
import simbia.app.crud.infra.dao.abstractclasses.DaoException;
import simbia.app.crud.infra.servlet.abstractclasses.OrdenarServlet;
import simbia.app.crud.infra.servlet.exception.operacao.RequisicaoSemRegistrosException;
import simbia.app.crud.infra.servlet.exception.operacao.RequisicaoSemTipoOrdenacaoException;
import simbia.app.crud.model.dao.TipoIndustria;
import simbia.app.crud.model.ordenacao.OrdenacaoFactory;
import simbia.app.crud.model.servlet.RequisicaoResposta;
import simbia.app.crud.util.ValidacoesDeDados;

import java.io.IOException;
import java.util.List;

@WebServlet("/tipo-industria/ordenar")
public class TipoIndustriaOrdenarServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest requisicao, HttpServletResponse resposta)
            throws ServletException, IOException {

        RequisicaoResposta requisicaoResposta = new RequisicaoResposta(requisicao, resposta);
        String chaveRegistros = "tipoindustriaRegistros";

        try {
            List<TipoIndustria> registros = (List<TipoIndustria>)
                    requisicaoResposta.recuperarAtributoDaRequisicao(chaveRegistros);

            System.out.println("=== DEBUG ORDENAÇÃO ===");
            System.out.println("Registros da sessão: " + (registros != null ? registros.size() : "null"));

            if (registros == null || registros.isEmpty()) {
                TipoIndustriaDao dao = new TipoIndustriaDao();
                try {
                    registros = dao.recuperarTudo();
                    System.out.println("Registros do banco: " + registros.size());
                } catch (DaoException e) {
                    e.printStackTrace();
                    requisicaoResposta.redirecionarPara("/tipo-industria/erroEmRegistros.jsp");
                    return;
                }
            }

            ValidacoesDeDados.validarRegistros(registros);

            String tipoOrdenacao = requisicaoResposta.recuperarParametroDaRequisicao("tipoOrdenacao");
            String ordem = requisicaoResposta.recuperarParametroDaRequisicao("ordem");

            System.out.println("Tipo ordenação: " + tipoOrdenacao);
            System.out.println("Ordem: " + ordem);

            ValidacoesDeDados.validarTipoDeOrdenacao(tipoOrdenacao);

            boolean crescente = ordem == null || !ordem.equals("desc");
            System.out.println("Crescente: " + crescente);

            OrdenarServlet<TipoIndustria> ordenador =
                    OrdenacaoFactory.criarParaTipoIndustria(tipoOrdenacao, crescente);

            ordenador.ordenarComLog(registros);



            requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao(chaveRegistros, registros);
            requisicaoResposta.adicionarAtributoNaRequisicao("criterioOrdenacao", tipoOrdenacao);
            requisicaoResposta.adicionarAtributoNaRequisicao("ordemAtual", crescente ? "asc" : "desc");

            requisicaoResposta.despacharPara("../tipo-industria.jsp");

        } catch (RequisicaoSemRegistrosException | RequisicaoSemTipoOrdenacaoException causa) {
            causa.printStackTrace();
            requisicaoResposta.redirecionarPara("/tipo-industria/registros");
        }
    }
}
