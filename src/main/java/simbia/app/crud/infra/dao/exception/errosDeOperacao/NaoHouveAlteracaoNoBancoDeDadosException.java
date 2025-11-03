package simbia.app.crud.infra.dao.exception.errosDeOperacao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Classe de erro de operacao, especifica de quando a operacao DELETE, INSERT ou UPDATE
 * nao altera o banco de dados.
 */
public class NaoHouveAlteracaoNoBancoDeDadosException extends RuntimeException {
//atributos
    private String mensagem;
//contrutor

    public NaoHouveAlteracaoNoBancoDeDadosException(){
        this.mensagem = Util.gerarMensagem();
    }

//toString
    public String toString(){
        return "NaoHouveAlteracaoNoBancoDeDadosException: " + mensagem;
    }

//getters
    public String getMensagem() {
        return mensagem;
    }

    /**
     * Classe utilitaria que gera a mensagem de erro especifica
     */
    private static class Util{
        private static String gerarMensagem(){
            String dataHora = "[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "]";
            return dataHora + " - Operacao de DAO nao alterou o banco de dados.";
        }
    }
}
