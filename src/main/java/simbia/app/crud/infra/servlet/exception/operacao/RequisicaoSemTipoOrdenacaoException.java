package simbia.app.crud.infra.servlet.exception.operacao;

import simbia.app.crud.infra.servlet.abstractclasses.OperacoesException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Exceção lançada quando a requisição não possui o atributo 'tipoOrdenacao'.
 *
 * Esta exceção é lançada quando se tenta ordenar registros mas o parâmetro ou
 * atributo 'tipoOrdenacao' está ausente na requisição, impossibilitando determinar
 * o critério de ordenação desejado.
 *
 * A mensagem de erro é gerada automaticamente com timestamp no formato
 * "[dd/MM/yyyy HH:mm:ss] - Erro de obrigatoriedade, requisicao não possui atributo 'tipoOrdenacao'."
 */
public class RequisicaoSemTipoOrdenacaoException extends OperacoesException {

    @Override
    public String gerarMensagem() {
        return "Requisição sem tipo de ordenação";
    }
}