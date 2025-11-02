package simbia.app.crud.servlet.inserir;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import simbia.app.crud.dao.VantagemPlanoDao;
import simbia.app.crud.infra.dao.abstractclasses.DaoException;
import simbia.app.crud.infra.dao.exception.errosDeOperacao.NaoHouveAlteracaoNoBancoDeDadosException;
import simbia.app.crud.infra.dao.exception.errosDoBancoDeDados.*;
import simbia.app.crud.infra.servlet.abstractclasses.InserirServlet;
import simbia.app.crud.infra.servlet.abstractclasses.OperacoesException;
import simbia.app.crud.model.dao.VantagemPlano;
import simbia.app.crud.model.servlet.RequisicaoResposta;
import simbia.app.crud.util.ValidacoesDeDados;

import java.io.IOException;

@WebServlet("/vantagem-plano/inserir")
public class VantagemPlanoInserirServlet extends InserirServlet<VantagemPlano> {
    @Override
    protected void doPost(HttpServletRequest requisicao, HttpServletResponse resposta)
            throws ServletException, IOException {

        RequisicaoResposta requisicaoResposta = new RequisicaoResposta(requisicao, resposta);

        try {
            // Recupera dados do formulário
            String idPlano = requisicaoResposta.recuperarParametroDaRequisicao("idPlano");
            String idVantagem = requisicaoResposta.recuperarParametroDaRequisicao("idVantagem");

            // VALIDAÇÃO UNIFICADA
            ValidacoesDeDados.ResultadoValidacao resultado =
                    ValidacoesDeDados.validarVantagemPlano(idPlano, idVantagem);

            // Se houver erros, retorna para o popup
            if (resultado.temErros()) {
                String errosJSON = resultado.toJSON();
                requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("erros", errosJSON);
                requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("dados",
                        idPlano + ";" + idVantagem);
                requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("popupAberto", "true");
                requisicaoResposta.redirecionarPara("/vantagem-plano.jsp");
                return;
            }

            // Se passou nas validações, insere no banco
            VantagemPlano registro = new VantagemPlano(
                    Long.parseLong(idVantagem),
                    Long.parseLong(idPlano)
            );
            VantagemPlanoDao dao = new VantagemPlanoDao();
            dao.inserir(registro);

            requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("status", true);
            requisicaoResposta.redirecionarPara("/vantagem-plano/atualizar");

        } catch (ViolacaoDeUnicidadeException causa) {
            // Trata erro de chave duplicada
            String errosJSON = "{\"combinação\":\"Esta combinação já existe\"}";
            requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("erros", errosJSON);
            requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("popupAberto", "true");
            requisicaoResposta.redirecionarPara("/vantagem-plano.jsp");

        } catch (DaoException causa) {
            causa.printStackTrace();
            requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("status", false);
            requisicaoResposta.redirecionarPara("/vantagem-plano.jsp");
        }
    }

    @Override
    public void inserirRegistroNoBanco(VantagemPlano entidade) throws DaoException, OperacoesException {
        VantagemPlanoDao dao = new VantagemPlanoDao();

        dao.inserir(entidade);
    }

    @Override
    public VantagemPlano recuperarNovoRegistroNaRequisicao(RequisicaoResposta requisicaoResposta) throws NumberFormatException{
        String idPlano = requisicaoResposta.recuperarParametroDaRequisicao("id-plano");
        String idVantagem = requisicaoResposta.recuperarParametroDaRequisicao("id-vantagem");

        ValidacoesDeDados.validarId(idPlano);
        ValidacoesDeDados.validarId(idVantagem);

        return new VantagemPlano(Long.parseLong(idPlano), Long.parseLong(idVantagem));
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
