package simbia.app.crud.servlet.inserir;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import simbia.app.crud.dao.AdministradorDao;
import simbia.app.crud.infra.dao.abstractclasses.DaoException;
import simbia.app.crud.infra.dao.exception.errosDoBancoDeDados.*;
import simbia.app.crud.infra.servlet.abstractclasses.InserirServlet;
import simbia.app.crud.infra.servlet.abstractclasses.OperacoesException;
import simbia.app.crud.infra.servlet.exception.operacao.SenhasDiferentesException;
import simbia.app.crud.infra.servlet.exception.validacaoDeDados.PadraoEmailErradoException;
import simbia.app.crud.infra.servlet.exception.validacaoDeDados.PadraoNomeErradoException;
import simbia.app.crud.infra.servlet.exception.validacaoDeDados.PadraoSenhaErradoException;
import simbia.app.crud.model.dao.Administrador;
import simbia.app.crud.model.servlet.RequisicaoResposta;
import simbia.app.crud.util.ValidacoesDeDados;

import java.io.IOException;

/**
 * Servlet para inserir novos administradores no sistema.
 */
@WebServlet("/administrador/inserir")
public class AdministradorInserirServlet extends InserirServlet<Administrador> {

    /**
     * Processa a requisição POST para inserir um administrador.
     * Valida os dados, insere no banco e trata possíveis erros.
     */
    @Override
    protected void doPost(HttpServletRequest requisicao, HttpServletResponse resposta)
            throws ServletException, IOException {
        RequisicaoResposta requisicaoResposta = new RequisicaoResposta(requisicao, resposta);

        try {
            // Recupera dados do formulário
            String nome = requisicaoResposta.recuperarParametroDaRequisicao("nome");
            String email = requisicaoResposta.recuperarParametroDaRequisicao("email");
            String senha = requisicaoResposta.recuperarParametroDaRequisicao("senha");
            String repetirSenha = requisicaoResposta.recuperarParametroDaRequisicao("repetir-senha");

            // VALIDAÇÃO UNIFICADA - UMA LINHA!
            ValidacoesDeDados.ResultadoValidacao resultado =
                    ValidacoesDeDados.validarAdministrador(nome, email, senha, repetirSenha);

            // Se houver erros, retorna para o popup
            if (resultado.temErros()) {
                String errosJSON = resultado.toJSON();
                requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("erros", errosJSON);
                requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("dados",
                        nome + ";" + email + ";" + senha + ";" + repetirSenha);
                requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("popupAberto", "true");
                requisicaoResposta.redirecionarPara("/administrador.jsp");
                return;
            }

            // Se passou nas validações, insere no banco
            Administrador admin = new Administrador(email, senha, nome);
            AdministradorDao dao = new AdministradorDao();
            dao.inserir(admin);

            requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("status", true);
            requisicaoResposta.redirecionarPara("/administrador/atualizar");

        } catch (ViolacaoDeUnicidadeException causa) {
            // Trata erro de email duplicado
            String errosJSON = "{\"email\":\"Este email já está cadastrado\"}";
            requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("erros", errosJSON);
            requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("popupAberto", "true");
            requisicaoResposta.redirecionarPara("/administrador.jsp");

        } catch (DaoException causa) {
            causa.printStackTrace();
            requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("status", false);
            requisicaoResposta.redirecionarPara("/administrador.jsp");
        }
    }

    /**
     * Insere o administrador no banco de dados usando o DAO.
     */
    @Override
    public void inserirRegistroNoBanco(Administrador entidade) throws DaoException, OperacoesException {
        AdministradorDao dao = new AdministradorDao();
        dao.inserir(entidade);
    }

    /**
     * Recupera e valida os dados do formulário de cadastro.
     * Retorna um objeto Administrador pronto para ser inserido.
     */
    @Override
    public Administrador recuperarNovoRegistroNaRequisicao(RequisicaoResposta requisicaoResposta)
            throws PadraoNomeErradoException, PadraoEmailErradoException,
            PadraoSenhaErradoException, SenhasDiferentesException {
        String nome = requisicaoResposta.recuperarParametroDaRequisicao("nome");
        String email = requisicaoResposta.recuperarParametroDaRequisicao("email");
        String senha = requisicaoResposta.recuperarParametroDaRequisicao("senha");
        String repetirSenha = requisicaoResposta.recuperarParametroDaRequisicao("repetir-senha");

        ValidacoesDeDados.validarNome(nome);
        ValidacoesDeDados.validarEmail(email);
        ValidacoesDeDados.validarSenha(senha);
        ValidacoesDeDados.validarRepeticaoSenha(senha, repetirSenha);

        return new Administrador(email, senha, nome);
    }

    /**
     * Retorna o endereço para onde redirecionar após inserção bem-sucedida.
     */
    @Override
    public String enderecoDeRedirecionamento() {
        return "/administrador/atualizar";
    }

    /**
     * Retorna o endereço para onde despachar em caso de erro.
     */
    @Override
    public String enderecoDeRedirecionamentoCasoErro() {
        return "/administrador.jsp";
    }
}