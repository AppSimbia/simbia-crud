package simbia.app.crud.infra.servlet.exception.validacaoDeDados;

import simbia.app.crud.infra.servlet.abstractclasses.ValidacaoDeDadosException;

public class PadraoDescricaoErradoException extends ValidacaoDeDadosException {
    @Override
    public String gerarMensagem() {
        return "Padrao descrição errado, excede número máximo de carcteres";
    }
}
