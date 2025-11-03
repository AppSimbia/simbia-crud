package simbia.app.crud.servlet.ordenacao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import simbia.app.crud.dao.CategoriaProdutoDao;
import simbia.app.crud.infra.dao.abstractclasses.DaoException;
import simbia.app.crud.infra.servlet.abstractclasses.OrdenarServlet;
import simbia.app.crud.infra.servlet.exception.operacao.RequisicaoSemRegistrosException;
import simbia.app.crud.infra.servlet.exception.operacao.RequisicaoSemTipoOrdenacaoException;
import simbia.app.crud.model.dao.CategoriaProduto;
import simbia.app.crud.model.ordenacao.OrdenacaoFactory;
import simbia.app.crud.model.servlet.RequisicaoResposta;
import simbia.app.crud.util.ValidacoesDeDados;

import java.io.IOException;
import java.util.List;

@WebServlet("/categoria-produto/ordenar")
public class CategoriaProdutoOrdenarServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest requisicao, HttpServletResponse resposta)
            throws ServletException, IOException {

        RequisicaoResposta requisicaoResposta = new RequisicaoResposta(requisicao, resposta);
        String chaveRegistros = "categoriaprodutoRegistros";

        try {
            List<CategoriaProduto> registros = (List<CategoriaProduto>)
                    requisicaoResposta.recuperarAtributoDaRequisicao(chaveRegistros);

            if (registros == null || registros.isEmpty()) {
                CategoriaProdutoDao dao = new CategoriaProdutoDao();
                try {
                    registros = dao.recuperarTudo();
                } catch (DaoException e) {
                    e.printStackTrace();
                    requisicaoResposta.redirecionarPara("/categoria-produto/erroEmRegistros.jsp");
                    return;
                }
            }

            ValidacoesDeDados.validarRegistros(registros);

            String tipoOrdenacao = requisicaoResposta.recuperarParametroDaRequisicao("tipoOrdenacao");
            String ordem = requisicaoResposta.recuperarParametroDaRequisicao("ordem");

            ValidacoesDeDados.validarTipoDeOrdenacao(tipoOrdenacao);

            boolean crescente = ordem == null || !ordem.equals("desc");

            OrdenarServlet<CategoriaProduto> ordenador =
                    OrdenacaoFactory.criarParaCategoriaProduto(tipoOrdenacao, crescente);

            ordenador.ordenarComLog(registros);

            requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao(chaveRegistros, registros);
            requisicaoResposta.adicionarAtributoNaRequisicao("criterioOrdenacao", tipoOrdenacao);
            requisicaoResposta.adicionarAtributoNaRequisicao("ordemAtual", crescente ? "asc" : "desc");

            requisicaoResposta.despacharPara("../categoria-produto.jsp");

        } catch (RequisicaoSemRegistrosException | RequisicaoSemTipoOrdenacaoException causa) {
            causa.printStackTrace();
            requisicaoResposta.redirecionarPara("/categoria-produto/registros");
        }
    }
}
