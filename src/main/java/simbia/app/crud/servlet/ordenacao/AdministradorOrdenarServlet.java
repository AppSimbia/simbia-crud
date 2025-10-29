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

/**
 * Classe de servlet de ordenação dos registros em exibição.
 */
@WebServlet("/administrador/ordenar")
public class AdministradorOrdenarServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest requisicao, HttpServletResponse resposta)
            throws ServletException, IOException {

        RequisicaoResposta requisicaoResposta = new RequisicaoResposta(requisicao, resposta);
        String chaveRegistros = "administradorRegistros";

        try {
            List<Administrador> registros = (List<Administrador>)
                    requisicaoResposta.recuperarAtributoDaRequisicao("administradorRegistros");

            if (registros == null || registros.isEmpty()) {
                // recarrega diretamente do banco
                AdministradorDao dao = new AdministradorDao();
                try {
                    registros = dao.recuperarTudo();
                } catch (DaoException e) {
                    e.printStackTrace();
                    requisicaoResposta.redirecionarPara("/administrador/erroEmRegistros.jsp");
                    return;
                }
            }

            ValidacoesDeDados.validarRegistros(registros);

            String tipoOrdenacao = requisicaoResposta.recuperarParametroDaRequisicao("tipoOrdenacao");
            String ordem = requisicaoResposta.recuperarParametroDaRequisicao("ordem");

            ValidacoesDeDados.validarTipoDeOrdenacao(tipoOrdenacao);

            boolean crescente = ordem == null || !ordem.equals("desc");

            OrdenarServlet<Administrador> ordenador =
                    OrdenacaoFactory.criarParaAdministrador(tipoOrdenacao, crescente);

            ordenador.ordenarComLog(registros);

            requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao(chaveRegistros, registros);
            requisicaoResposta.adicionarAtributoNaRequisicao("criterioOrdenacao", tipoOrdenacao);
            requisicaoResposta.adicionarAtributoNaRequisicao("ordemAtual", crescente ? "asc" : "desc");

            requisicaoResposta.despacharPara("../administrador.jsp");

        } catch (RequisicaoSemRegistrosException | RequisicaoSemTipoOrdenacaoException causa) {
            causa.printStackTrace();
            requisicaoResposta.redirecionarPara("/administrador/registros");
        }
    }
}