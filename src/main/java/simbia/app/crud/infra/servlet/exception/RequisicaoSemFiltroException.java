package simbia.app.crud.infra.servlet.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RequisicaoSemFiltroException extends RuntimeException {
    private String mensagem;

    public RequisicaoSemFiltroException(){
        this.mensagem = Util.gerarMensagem();
    }

    public String toString(){
        return "RequisicaoSemFiltroException: " + mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }

    private static class Util{
        private static String gerarMensagem(){
            String dataHora = "[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "]";
            return dataHora + " - Tentativa de filtro sem parametros adequados.";
        }
    }
}
