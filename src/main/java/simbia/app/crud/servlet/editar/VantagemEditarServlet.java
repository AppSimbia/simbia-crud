package simbia.app.crud.servlet.editar;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import simbia.app.crud.dao.VantagemDao;
import simbia.app.crud.infra.dao.abstractclasses.DaoException;
import simbia.app.crud.infra.dao.exception.errosDoBancoDeDados.ViolacaoDeUnicidadeException;
import simbia.app.crud.infra.servlet.abstractclasses.EditarServlet;
import simbia.app.crud.infra.servlet.abstractclasses.OperacoesException;
import simbia.app.crud.infra.servlet.exception.validacaoDeDados.PadraoEmailErradoException;
import simbia.app.crud.infra.servlet.exception.validacaoDeDados.PadraoNomeErradoException;
import simbia.app.crud.model.dao.Vantagem;
import simbia.app.crud.model.servlet.RequisicaoResposta;
import simbia.app.crud.util.ValidacoesDeDados;

import java.io.IOException;

@WebServlet("/vantagem/alterar")
public class VantagemEditarServlet extends EditarServlet<Vantagem> {
    @Override
    protected void doPost(HttpServletRequest requisicao, HttpServletResponse resposta)
            throws ServletException, IOException {
        RequisicaoResposta requisicaoResposta = new RequisicaoResposta(requisicao, resposta);

        try {
            // Recupera dados do formulário
            String nome = requisicaoResposta.recuperarParametroDaRequisicao("nome");
            String descricao = requisicaoResposta.recuperarParametroDaRequisicao("descricao");

            // VALIDAÇÃO UNIFICADA - UMA LINHA!
            ValidacoesDeDados.ResultadoValidacao resultado =
                    ValidacoesDeDados.validarNomeDescricao(nome, descricao, "vantagem");

            // Se houver erros, retorna para o popup
            if (resultado.temErros()) {
                String errosJSON = resultado.toJSON();
                requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("erros", errosJSON);
                requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("dados", nome + ";" + descricao);
                requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("popupAberto", "true");
                requisicaoResposta.redirecionarPara("/vantagem.jsp");
                return;
            }

            // Se passou nas validações, insere no banco
            Vantagem vantagem = new Vantagem(nome, descricao);
            VantagemDao dao = new VantagemDao();
            dao.inserir(vantagem);

            requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("status", true);
            requisicaoResposta.redirecionarPara("/vantagem/atualizar");

        } catch (ViolacaoDeUnicidadeException causa) {
            // Trata erro de nome duplicado
            String errosJSON = "{\"nome\":\"Esta vantagem já está cadastrada\"}";
            requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("erros", errosJSON);
            requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("popupAberto", "true");
            requisicaoResposta.redirecionarPara("/vantagem.jsp");

        } catch (DaoException causa) {
            // Outros erros de banco
            causa.printStackTrace();
            requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("status", false);
            requisicaoResposta.redirecionarPara("/vantagem.jsp");
        }
    }

    @Override
    public void editarRegistroNoBanco(Vantagem entidade) throws DaoException, OperacoesException {
        VantagemDao dao = new VantagemDao();

        dao.atualizar(entidade);
    }

    @Override
    public Vantagem recuperarRegistroEmEdicaoNaRequisicao(RequisicaoResposta requisicaoResposta)
            throws PadraoNomeErradoException, PadraoEmailErradoException {
        String nome = requisicaoResposta.recuperarParametroDaRequisicao("nome");
        String descricao = requisicaoResposta.recuperarParametroDaRequisicao("descricao");

        ValidacoesDeDados.validarDescricao("descricao");
        ValidacoesDeDados.validarNome("nome");

        return new Vantagem(nome, descricao);
    }

    @Override
    public String enderecoDeRedirecionamento() {
        return "/vantagem/atualizar";
    }

    @Override
    public String enderecoDeRedirecionamentoCasoErro() {
        return "/vantagem.jsp";
    }
}
