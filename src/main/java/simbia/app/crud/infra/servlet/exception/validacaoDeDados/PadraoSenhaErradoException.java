package simbia.app.crud.infra.servlet.exception.validacaoDeDados;

import simbia.app.crud.infra.servlet.abstractclasses.ValidacaoDeDadosException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Exceção lançada quando a senha fornecida não está no padrão de formatação esperado.
 *
 * Esta exceção é lançada durante a validação de entrada quando a senha não corresponde
 * aos requisitos de formatação (ex: tamanho mínimo, caracteres especiais, etc).
 *
 * A mensagem de erro é gerada automaticamente com timestamp no formato
 * "[dd/MM/yyyy HH:mm:ss] - Erro de credenciais, senha fora do padrao de formatacao."
 */
public class PadraoSenhaErradoException extends ValidacaoDeDadosException {

    @Override
    public String gerarMensagem() {
        return "Senha inválida, por favor digite uma senha com pelo menos 1 caracter minúsculo, maiúsculo, numérico e especial";
    }
}