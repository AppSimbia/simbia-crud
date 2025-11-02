package simbia.app.crud.infra.servlet.exception.validacaoDeDados;

import simbia.app.crud.infra.servlet.abstractclasses.OperacoesException;

public class PadraoNomeErradoException extends OperacoesException {
    @Override
    public String gerarMensagem() {
        return "Nome deve conter apenas letras e espaços";
    }
}
