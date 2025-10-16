package simbia.app.crud.servlet;

import java.io.IOException;
import java.util.Optional;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import simbia.app.crud.dao.AdministradorDao;
import simbia.app.crud.infra.dao.exception.ConexaoException;
import simbia.app.crud.infra.dao.exception.DaoException;
import simbia.app.crud.infra.servlet.exception.EmailOuSenhaErradosException;
import simbia.app.crud.infra.servlet.exception.ErrosDeDevolucaoParaClient;
import simbia.app.crud.infra.servlet.exception.PadraoEmailErradoException;
import simbia.app.crud.infra.servlet.exception.PadraoSenhaErradoException;
import simbia.app.crud.model.dao.Administrador;
import simbia.app.crud.model.servlet.RequisicaoResposta;
import simbia.app.crud.util.ValidacoesDeDados;

/**
 * Servlet de validação de usuario do sintema de CRUD.
 */
@WebServlet("/entrar")
public class ValidarUsuarioServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest requisicao, HttpServletResponse resposta) throws ServletException, IOException {
        RequisicaoResposta requisicaoResposta = new RequisicaoResposta(requisicao, resposta);
        try {
            verificarUsuario(requisicaoResposta);
            requisicaoResposta.redirecionarPara("/administrador/registros");
            
        } catch (EmailOuSenhaErradosException | PadraoSenhaErradoException | PadraoEmailErradoException causa) {
            causa.printStackTrace();
            requisicaoResposta.adicionarAtributoNaRequisicao("erro", ErrosDeDevolucaoParaClient.EMAIL_OU_SENHA_INCORRETOS);
            requisicaoResposta.despacharPara("entrar.jsp");
            
        } catch (DaoException | ConexaoException causa){
            causa.printStackTrace();
            requisicaoResposta.adicionarAtributoNaRequisicao("erro", ErrosDeDevolucaoParaClient.ERRO_DE_COMUNICACAO_COM_O_BANCO_DE_DADOS);
            requisicaoResposta.despacharPara("entrar.jsp");
        }
    }

    private static void verificarUsuario(RequisicaoResposta requisicaoResposta) throws EmailOuSenhaErradosException, PadraoEmailErradoException, PadraoSenhaErradoException, DaoException{
        String email = recuperarAtributoEmailDaRequisicao(requisicaoResposta);
        String senha = recuperarAtributoSenhaDaRequisicao(requisicaoResposta);

        Administrador registroCorrespondenteNoBanco = recuperarAdministradorNoBanco(email, senha);

        requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("administradorVerificado", registroCorrespondenteNoBanco);
    }

    private static Administrador recuperarAdministradorNoBanco(String email, String senha) throws DaoException, ConexaoException {
        AdministradorDao dao = new AdministradorDao();
        Optional<Administrador> retornoBanco = dao.recuperarPeloEmailESenha(email, senha);

        ValidacoesDeDados.validarSeExisteRegistroCorrespondenteNoBanco(retornoBanco);

        return retornoBanco.get();
    }

    private static String recuperarAtributoEmailDaRequisicao(RequisicaoResposta requisicaoResposta) throws PadraoEmailErradoException{
        String email = requisicaoResposta.recuperarParametroDaRequisicao("email");

        ValidacoesDeDados.validarEmail(email);

        return email;
    }

    private static String recuperarAtributoSenhaDaRequisicao(RequisicaoResposta requisicaoResposta) throws PadraoSenhaErradoException{
        String senha = requisicaoResposta.recuperarParametroDaRequisicao("senha");

        ValidacoesDeDados.validarSenha(senha);

        return senha;
    }
}
