package simbia.app.crud.servlet.autenticacao;

import java.io.IOException;
import java.util.Optional;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import simbia.app.crud.dao.AdministradorDao;
import simbia.app.crud.infra.dao.abstractclasses.DaoException;
import simbia.app.crud.infra.servlet.abstractclasses.OperacoesException;
import simbia.app.crud.infra.servlet.abstractclasses.ValidacaoDeDadosException;
import simbia.app.crud.infra.servlet.exception.operacao.EmailOuSenhaErradosException;
import simbia.app.crud.infra.servlet.exception.validacaoDeDados.PadraoEmailErradoException;
import simbia.app.crud.infra.servlet.exception.validacaoDeDados.PadraoSenhaErradoException;
import simbia.app.crud.model.dao.Administrador;
import simbia.app.crud.model.servlet.RequisicaoResposta;
import simbia.app.crud.util.ValidacoesDeDados;

/**
 * Servlet de validação de usuário do sistema de CRUD.
 *
 * Fluxo: recupera email e senha da requisição → valida formato dos dados → busca
 * administrador no banco → armazena usuário autenticado na sessão → redireciona
 * para página de administração (ou despacha para página de login com erro).
 *
 * Mapeado na URL "/entrar", processa requisições POST do formulário de login.
 */
@WebServlet("/entrar")
public class ValidarUsuarioServlet extends HttpServlet {

    /**
     * Processa a requisição POST: valida credenciais e autentica o usuário.
     * Em caso de sucesso, redireciona para a página de administração.
     * Em caso de erro, despacha de volta para a página de login com mensagem de erro.
     */
    @Override
    protected void doPost(HttpServletRequest requisicao, HttpServletResponse resposta)
            throws ServletException, IOException {
        RequisicaoResposta requisicaoResposta = new RequisicaoResposta(requisicao, resposta);

        try {
            verificarUsuario(requisicaoResposta);
            requisicaoResposta.redirecionarPara("/administrador.jsp");

        } catch (DaoException causa) {
            causa.printStackTrace();
            requisicaoResposta.adicionarAtributoNaRequisicao("erro", "Servidor instável;Por favor, tente novamente");
            requisicaoResposta.despacharPara("entrar.jsp");

        } catch (ValidacaoDeDadosException | OperacoesException causa) {
            causa.printStackTrace();
            requisicaoResposta.adicionarAtributoNaRequisicao("erro", "Informações erradas;Email ou senha incorretos");
            requisicaoResposta.despacharPara("entrar.jsp");
        }
    }

    /**
     * Verifica as credenciais do usuário e armazena o administrador autenticado na sessão.
     * Recupera email e senha, busca no banco e salva na sessão como "administradorAutenticado".
     */
    private static void verificarUsuario(RequisicaoResposta requisicaoResposta)
            throws EmailOuSenhaErradosException, PadraoEmailErradoException, PadraoSenhaErradoException {
        String email = recuperarAtributoEmailDaRequisicao(requisicaoResposta);
        String senha = recuperarAtributoSenhaDaRequisicao(requisicaoResposta);

        Administrador registroCorrespondenteNoBanco = recuperarAdministradorNoBanco(email, senha);

        requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("administradorAutenticado", registroCorrespondenteNoBanco);
    }

    /**
     * Recupera o administrador do banco de dados com base no email e senha fornecidos.
     * Valida se existe um registro correspondente às credenciais.
     */
    private static Administrador recuperarAdministradorNoBanco(String email, String senha)
            throws EmailOuSenhaErradosException {
        AdministradorDao dao = new AdministradorDao();
        Optional<Administrador> retornoBanco = dao.recuperarPeloEmailESenha(email, senha);

        ValidacoesDeDados.validarSeExisteRegistroCorrespondenteNoBanco(retornoBanco);

        return retornoBanco.get();
    }

    /**
     * Recupera e valida o email da requisição.
     * Verifica se o email está no formato correto antes de retorná-lo.
     */
    private static String recuperarAtributoEmailDaRequisicao(RequisicaoResposta requisicaoResposta)
            throws PadraoEmailErradoException {
        String email = requisicaoResposta.recuperarParametroDaRequisicao("email");

        ValidacoesDeDados.validarEmail(email);

        return email;
    }

    /**
     * Recupera e valida a senha da requisição.
     * Verifica se a senha está no formato correto antes de retorná-la.
     */
    private static String recuperarAtributoSenhaDaRequisicao(RequisicaoResposta requisicaoResposta)
            throws PadraoSenhaErradoException {
        String senha = requisicaoResposta.recuperarParametroDaRequisicao("senha");

        ValidacoesDeDados.validarSenha(senha);

        return senha;
    }
}