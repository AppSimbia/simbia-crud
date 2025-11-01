package simbia.app.crud.infra.servlet.exception.operacao;

import simbia.app.crud.infra.servlet.abstractclasses.OperacoesException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Exceção lançada quando um usuário tenta acessar recursos sem estar autenticado.
 *
 * Esta exceção é lançada quando se tenta acessar páginas ou funcionalidades restritas
 * sem possuir uma sessão válida de autenticação, indicando uma tentativa de acesso
 * não autorizado aos recursos do sistema.
 *
 * A mensagem de erro é gerada automaticamente com timestamp no formato
 * "[dd/MM/yyyy HH:mm:ss] - Tentativa de acessar as tabelas sem devida autenticacao."
 */
public class UsuarioNaoAutenticadoException extends OperacoesException {
    @Override
    public String gerarMensagem() {
        return "Autentique-se antes de acessar essa página";
    }
}