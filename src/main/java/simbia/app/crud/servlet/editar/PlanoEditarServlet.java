package simbia.app.crud.servlet.editar;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import simbia.app.crud.dao.PlanoDao;
import simbia.app.crud.infra.dao.abstractclasses.DaoException;
import simbia.app.crud.infra.dao.exception.errosDeOperacao.NaoHouveAlteracaoNoBancoDeDadosException;
import simbia.app.crud.infra.dao.exception.errosDoBancoDeDados.*;
import simbia.app.crud.infra.servlet.abstractclasses.EditarServlet;
import simbia.app.crud.infra.servlet.abstractclasses.OperacoesException;
import simbia.app.crud.model.dao.Plano;
import simbia.app.crud.model.servlet.RequisicaoResposta;
import simbia.app.crud.util.ValidacoesDeDados;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/plano/alterar")
public class PlanoEditarServlet extends EditarServlet<Plano> {

    @Override
    protected void doPost(HttpServletRequest requisicao, HttpServletResponse resposta)
            throws ServletException, IOException {
        RequisicaoResposta requisicaoResposta = new RequisicaoResposta(requisicao, resposta);

        try {
            String idStr = requisicaoResposta.recuperarParametroDaRequisicao("id");
            String nome = requisicaoResposta.recuperarParametroDaRequisicao("nome");
            String valorStr = requisicaoResposta.recuperarParametroDaRequisicao("valor");
            String status = requisicaoResposta.recuperarParametroDaRequisicao("status");

            ValidacoesDeDados.ResultadoValidacao resultado =
                    ValidacoesDeDados.validarPlano(nome, valorStr, status);

            if (resultado.temErros()) {
                String errosJSON = resultado.toJSON();
                requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("erros", errosJSON);
                String statusFormatado = "ativo".equals(status) ? "true" : "false";
                requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("dados",
                        idStr + ";" + nome + ";" + valorStr + ";" + statusFormatado);
                requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("popupAberto", "true");
                requisicaoResposta.redirecionarPara("/plano.jsp");
                return;
            }

            long id = Long.parseLong(idStr);
            BigDecimal valor = new BigDecimal(valorStr);
            boolean ativo = "ativo".equals(status);
            Plano plano = new Plano(id, valor, ativo, nome);

            PlanoDao dao = new PlanoDao();
            dao.atualizar(plano);

            requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("status", true);
            requisicaoResposta.redirecionarPara("/plano/atualizar");

        } catch (NumberFormatException causa) {
            tratarErro(requisicaoResposta, "{\"geral\":\"ID ou valor inválido\"}", "");

        } catch (ViolacaoDeUnicidadeException causa) {
            String idStr = requisicaoResposta.recuperarParametroDaRequisicao("id");
            String nome = requisicaoResposta.recuperarParametroDaRequisicao("nome");
            String valorStr = requisicaoResposta.recuperarParametroDaRequisicao("valor");
            String status = requisicaoResposta.recuperarParametroDaRequisicao("status");
            String statusFormatado = "ativo".equals(status) ? "true" : "false";

            tratarErro(requisicaoResposta,
                    "{\"nome\":\"Já existe um plano com este nome\"}",
                    idStr + ";" + nome + ";" + valorStr + ";" + statusFormatado);

        } catch (ViolacaoDeObrigatoriedadeException causa) {
            String idStr = requisicaoResposta.recuperarParametroDaRequisicao("id");
            String nome = requisicaoResposta.recuperarParametroDaRequisicao("nome");
            String valorStr = requisicaoResposta.recuperarParametroDaRequisicao("valor");
            String status = requisicaoResposta.recuperarParametroDaRequisicao("status");
            String statusFormatado = "ativo".equals(status) ? "true" : "false";

            tratarErro(requisicaoResposta,
                    "{\"geral\":\"Campo obrigatório não preenchido\"}",
                    idStr + ";" + nome + ";" + valorStr + ";" + statusFormatado);

        } catch (ViolacaoDeTamanhoException causa) {
            String idStr = requisicaoResposta.recuperarParametroDaRequisicao("id");
            String nome = requisicaoResposta.recuperarParametroDaRequisicao("nome");
            String valorStr = requisicaoResposta.recuperarParametroDaRequisicao("valor");
            String status = requisicaoResposta.recuperarParametroDaRequisicao("status");
            String statusFormatado = "ativo".equals(status) ? "true" : "false";

            tratarErro(requisicaoResposta,
                    "{\"nome\":\"Nome muito longo para o banco de dados\"}",
                    idStr + ";" + nome + ";" + valorStr + ";" + statusFormatado);

        } catch (NaoHouveAlteracaoNoBancoDeDadosException causa) {
            tratarErro(requisicaoResposta,
                    "{\"geral\":\"Plano não encontrado ou nenhuma alteração foi feita\"}",
                    "");

        } catch (FalhaDeConexaoDriverInadequadoException | FalhaDeConexaoGeralException |
                 FalhaDeConexaoBancoDeDadosInexistenteException | FalhaDeConexaoQuedaRepentina |
                 FalhaDeConexaoSenhaIncorretaException causa) {
            causa.printStackTrace();
            requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("status", false);
            requisicaoResposta.redirecionarPara("/plano.jsp");

        } catch (DaoException causa) {
            causa.printStackTrace();
            requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("status", false);
            requisicaoResposta.redirecionarPara("/plano.jsp");
        }
    }

    private void tratarErro(RequisicaoResposta requisicaoResposta, String errosJSON, String dados) {
        try {
            requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("erros", errosJSON);
            requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("dados", dados);
            requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("popupAberto", "true");
            requisicaoResposta.redirecionarPara("/plano.jsp");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void editarRegistroNoBanco(Plano entidade) throws DaoException, OperacoesException {
        PlanoDao dao = new PlanoDao();
        dao.atualizar(entidade);
    }

    @Override
    public Plano recuperarRegistroEmEdicaoNaRequisicao(RequisicaoResposta requisicaoResposta)
            throws NumberFormatException {

        String idStr = requisicaoResposta.recuperarParametroDaRequisicao("id");
        String nome = requisicaoResposta.recuperarParametroDaRequisicao("nome");
        String valorStr = requisicaoResposta.recuperarParametroDaRequisicao("valor");
        String statusStr = requisicaoResposta.recuperarParametroDaRequisicao("status");

        long id = Long.parseLong(idStr);
        BigDecimal valor = null;

        if (valorStr != null && !valorStr.trim().isEmpty()) {
            valor = new BigDecimal(valorStr.trim().replace(",", "."));
        }

        boolean ativo = "ativo".equals(statusStr);

        return new Plano(id, valor, ativo, nome);
    }

    @Override
    public String enderecoDeRedirecionamento() {
        return "/plano/atualizar";
    }

    @Override
    public String enderecoDeRedirecionamentoCasoErro() {
        return "/plano.jsp";
    }
}