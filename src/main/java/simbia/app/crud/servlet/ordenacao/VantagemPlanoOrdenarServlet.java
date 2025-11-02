package simbia.app.crud.servlet.ordenacao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import simbia.app.crud.dao.VantagemPlanoDao;
import simbia.app.crud.infra.dao.abstractclasses.DaoException;
import simbia.app.crud.infra.servlet.abstractclasses.OrdenarServlet;
import simbia.app.crud.infra.servlet.exception.operacao.RequisicaoSemRegistrosException;
import simbia.app.crud.infra.servlet.exception.operacao.RequisicaoSemTipoOrdenacaoException;
import simbia.app.crud.model.dao.VantagemPlano;
import simbia.app.crud.model.ordenacao.OrdenacaoFactory;
import simbia.app.crud.model.servlet.RequisicaoResposta;
import simbia.app.crud.util.ValidacoesDeDados;

import java.io.IOException;
import java.util.List;

@WebServlet("/vantagem-plano/ordenar")
public class VantagemPlanoOrdenarServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest requisicao, HttpServletResponse resposta)
            throws ServletException, IOException {

        RequisicaoResposta requisicaoResposta = new RequisicaoResposta(requisicao, resposta);
        String chaveRegistros = "vantagemplanoRegistros";

        try {
            List<VantagemPlano> registros = (List<VantagemPlano>)
                    requisicaoResposta.recuperarAtributoDaRequisicao(chaveRegistros);

            if (registros == null || registros.isEmpty()) {
                VantagemPlanoDao dao = new VantagemPlanoDao();
                try {
                    registros = dao.recuperarTudo();
                } catch (DaoException e) {
                    e.printStackTrace();
                    requisicaoResposta.redirecionarPara("/vantagem-plano/erroEmRegistros.jsp");
                    return;
                }
            }

            ValidacoesDeDados.validarRegistros(registros);

            String tipoOrdenacao = requisicaoResposta.recuperarParametroDaRequisicao("tipoOrdenacao");
            String ordem = requisicaoResposta.recuperarParametroDaRequisicao("ordem");
            System.out.println("=== DEBUG VANTAGEM PLANO ===");
            System.out.println("Chave usada: " + chaveRegistros);
            System.out.println("Registros encontrados: " + (registros != null ? registros.size() : "null"));


            ValidacoesDeDados.validarTipoDeOrdenacao(tipoOrdenacao);

            boolean crescente = ordem == null || !ordem.equals("desc");

            OrdenarServlet<VantagemPlano> ordenador =
                    OrdenacaoFactory.criarParaVantagemPlano(tipoOrdenacao, crescente);

            ordenador.ordenarComLog(registros);

            System.out.println("DEPOIS DA ORDENAÇÃO:");
            for (int i = 0; i < Math.min(3, registros.size()); i++) {
                VantagemPlano vp = registros.get(i);
                System.out.println("  [" + i + "] ID=" + vp.getIdVantagemPlano() + " IdPlano=" + vp.getIdPlano());
            }
            System.out.println("============================");


            requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao(chaveRegistros, registros);
            requisicaoResposta.adicionarAtributoNaRequisicao("criterioOrdenacao", tipoOrdenacao);
            requisicaoResposta.adicionarAtributoNaRequisicao("ordemAtual", crescente ? "asc" : "desc");

            requisicaoResposta.despacharPara("../vantagem-plano.jsp");

        } catch (RequisicaoSemRegistrosException | RequisicaoSemTipoOrdenacaoException causa) {
            causa.printStackTrace();
            requisicaoResposta.redirecionarPara("/vantagem-plano/registros");
        }
    }
}
