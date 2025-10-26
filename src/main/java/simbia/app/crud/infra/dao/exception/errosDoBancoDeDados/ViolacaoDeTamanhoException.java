package simbia.app.crud.infra.dao.exception.errosDoBancoDeDados;

import simbia.app.crud.infra.dao.abstractclasses.DaoException;

import java.sql.SQLException;

/**
 * Classe de erro de violacao tamanho durante comunicacao com banco de dados postgresql.
 */
public class ViolacaoDeTamanhoException extends DaoException {
//atributos
    private String tamanhoMaximoViolado;

//construtor
    public ViolacaoDeTamanhoException(SQLException causa) {
        super(causa);

        this.tamanhoMaximoViolado = Util.subStringTamanhoMaximoCaracteres(causa);
    }

//getters

    public String getTamanhoMaximoViolado() {
        return tamanhoMaximoViolado;
    }

//outros
    @Override
    public String gerarMensagemPorSQLException(SQLException causa) {
        return " - Violação de tamanho, numero de caracteres maior que \"" + tamanhoMaximoViolado + "\".";
    }

    /**
     * Classe utilitária de subStrings da mensagem original de erro.
     */
    private static class Util{

        private static String subStringTamanhoMaximoCaracteres(SQLException causa) {
            String messege = causa.getMessage();
            return "\u001B[33m" + messege.substring(messege.indexOf("(") + 1, messege.indexOf(")")) + "\u001B[0m";
        }
    }
}
