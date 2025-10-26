package simbia.app.crud.infra.dao.exception.errosDeOperacao;

import simbia.app.crud.infra.servlet.exception.EmailOuSenhaErradosException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NaoHouveAlteracaoNoBancoDeDadosException extends RuntimeException {

    private String mensagem;

    public NaoHouveAlteracaoNoBancoDeDadosException(){
        this.mensagem = Util.gerarMensagem();
    }

    public String toString(){
        return "NaoHouveAlteracaoNoBancoDeDadosException: " + mensagem;
    }
    public String getMensagem() {
        return mensagem;
    }

    private static class Util{
        private static String gerarMensagem(){
            String dataHora = "[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "]";
            return dataHora + " - Operacao de DAO nao alterou o banco de dados.";
        }
    }
}
