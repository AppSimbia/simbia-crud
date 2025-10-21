package simbia.app.crud.infra.dao.exception;

import simbia.app.crud.infra.dao.abstractclasses.DaoException;

import java.sql.SQLException;

/**
 * Classe de erro de violacao obrigatoriedade durante comunicacao com banco de dados postgresql.
 */
public class ViolacaoDeObrigatoriedadeException extends DaoException {
//atributos
    private String campoViolado;
    private String tabelaViolada;

//construtor
    public ViolacaoDeObrigatoriedadeException(SQLException causa) {
        super(causa);

        this.campoViolado = Util.subStringCampoDeViolacaoDeObrigatoriedade(causa);
        this.tabelaViolada = Util.subStringTabelaDeViolacaoDeObrigatoriedade(causa);
    }

//getters

    public String getCampoViolado() {
        return campoViolado;
    }

    public String getTabelaViolada() {
        return tabelaViolada;
    }

//outros
    @Override
    public String gerarMensagemPorSQLException(SQLException causa) {
        return " - Violação de obrigatoriedade, valor do campo \"" + campoViolado + "\" da tabela \"" + tabelaViolada + "\" é nulo.";
    }



    /**
     * Classe utilitária de subStrings da mensagem original de erro.
     */
    private static class Util{

        private static String subStringCampoDeViolacaoDeObrigatoriedade(SQLException causa) {
            String messege = causa.getMessage();
            String[] mensagemFatiada = messege.replaceAll("of", "|").split("\\|");
            return "\u001B[33m" + mensagemFatiada[0].substring(mensagemFatiada[0].indexOf("\"") + 1, mensagemFatiada[0].lastIndexOf("\"")) + "\u001B[0m";
        }

        private static String subStringTabelaDeViolacaoDeObrigatoriedade(SQLException causa) {
            String messege = causa.getMessage();
            String[] mensagemFatiada = messege.replaceAll("of", "|").split("\\|");
            return "\u001B[33m" + mensagemFatiada[1].substring(mensagemFatiada[1].indexOf("\"") + 1, mensagemFatiada[1].lastIndexOf("\"")) + "\u001B[0m";
        }
    }
}