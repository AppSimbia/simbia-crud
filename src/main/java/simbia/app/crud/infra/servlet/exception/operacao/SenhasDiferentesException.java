package simbia.app.crud.infra.servlet.exception.operacao;

import simbia.app.crud.infra.servlet.abstractclasses.OperacoesException;

public class SenhasDiferentesException extends OperacoesException {
    @Override
    public String gerarMensagem() {
        return "Senhas devem ser iguais";
    }
}
