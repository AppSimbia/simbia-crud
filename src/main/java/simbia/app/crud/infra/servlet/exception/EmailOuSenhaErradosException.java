package simbia.app.crud.infra.servlet.exception;

import simbia.app.crud.infra.dao.conection.ManipuladorConexao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EmailOuSenhaErradosException extends RuntimeException{

    private String mensagem;

    public EmailOuSenhaErradosException(){
        this.mensagem = Util.gerarMensagem();
    }

    public String toString(){
        return "EmailOuSenhaIncorretosException: " + mensagem;
    }
    public String getMensagem() {
        return mensagem;
    }

    private static class Util{
        private static String gerarMensagem(){
            String dataHora = "[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "]";
            return dataHora + " - Erro de credenciais, email ou senha incorretos.";
        }
    }
}
