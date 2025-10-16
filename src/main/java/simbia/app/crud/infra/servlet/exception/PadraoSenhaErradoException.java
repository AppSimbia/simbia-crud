package simbia.app.crud.infra.servlet.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PadraoSenhaErradoException extends RuntimeException{

    private String mensagem;

    public PadraoSenhaErradoException(){
        this.mensagem = Util.gerarMensagem();
    }

    public String toString(){
        return "PadraoSenhaIncorretoException: " + mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }

    private static class Util{
        private static String gerarMensagem(){
            String dataHora = "[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "]";
            return dataHora + " - Erro de credenciais, senha fora do padrao de formatacao.";
        }
    }
}
