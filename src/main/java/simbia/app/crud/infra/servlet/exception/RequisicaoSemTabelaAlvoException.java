package simbia.app.crud.infra.servlet.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RequisicaoSemTabelaAlvoException extends RuntimeException{
    private String mensagem;

    public RequisicaoSemTabelaAlvoException(){
        this.mensagem = Util.gerarMensagem();
    }

    public String toString(){
        return "RequisicaoSemTabelaARecuperarException: " + mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }

    private static class Util{
        private static String gerarMensagem(){
            String dataHora = "[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "]";
            return dataHora + " - Tentativa de recuperar registros sem tabela alvo.";
        }
    }
}
