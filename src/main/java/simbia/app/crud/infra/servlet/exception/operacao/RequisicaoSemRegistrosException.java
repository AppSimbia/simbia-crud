package simbia.app.crud.infra.servlet.exception.operacao;

import simbia.app.crud.infra.servlet.abstractclasses.OperacoesException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Exceção lançada quando a requisição não possui registros para serem exibidos.
 *
 * Esta exceção é lançada quando se tenta processar ou exibir uma lista de registros
 * que está vazia ou nula, indicando que não há dados disponíveis para apresentar
 * ao usuário.
 *
 * A mensagem de erro é gerada automaticamente com timestamp no formato
 * "[dd/MM/yyyy HH:mm:ss] - Erro de obrigatoriedade, requisicao não possui registros a serem mostrados."
 */
public class RequisicaoSemRegistrosException extends OperacoesException {

    @Override
    public String gerarMensagem() {
        return "Requisicão sem registros";
    }
}