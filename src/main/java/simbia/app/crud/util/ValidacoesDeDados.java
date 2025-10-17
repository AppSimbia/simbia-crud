package simbia.app.crud.util;

import simbia.app.crud.infra.servlet.exception.*;
import simbia.app.crud.model.servlet.RequisicaoResposta;

import java.util.Optional;

/**
 * Classe utilitária de validações de dados
 */
public class ValidacoesDeDados {
    //atributos>constantes>expressoes-regulares
    private static String REGEX_EMAIL = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static String REGEX_SENHA = "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+=\\[\\]{}|;:'\",.<>?/~`\\\\-]).{8,200}";

    public static void validarEmail(String email) throws PadraoEmailErradoException{
        if (!email.matches(REGEX_EMAIL)) throw new PadraoEmailErradoException();
    }

    public static void validarSenha(String senha) throws PadraoSenhaErradoException{
        if (!senha.matches(REGEX_SENHA)) throw new PadraoSenhaErradoException();
    }

    public static <T> void validarSeExisteRegistroCorrespondenteNoBanco(Optional<T> retornoBanco) throws EmailOuSenhaErradosException{
        if (retornoBanco.isEmpty()) throw new EmailOuSenhaErradosException();
    }

    public static void validarSeAdministradorEstaAtutenticado(RequisicaoResposta requisicaoResposta) throws UsuarioNaoAutenticadoException {
        if (!requisicaoResposta.existeSessaoDaRequisicao("administradorAutenticado")) throw new UsuarioNaoAutenticadoException();
    }

    public static void validarTipoDeOrdenacao(String tipoOrdenacao) throws RequisicaoSemTipoOrdenacaoException{
        if(     tipoOrdenacao != "porId"
                && tipoOrdenacao != "porEmail"
                && tipoOrdenacao != "porNome"
                && tipoOrdenacao != "porValor"
        ) throw new RequisicaoSemTipoOrdenacaoException();
    }
}
