package simbia.app.crud.infra.dao.exception;

import simbia.app.crud.infra.dao.abstractclasses.DaoException;

import java.sql.SQLException;

/**
 * Classe de erro de violacao de registro de chave estrangeira durante comunicacao com banco de dados postgresql.
 */
public class ViolacaoDeRegistroDeChaveEstrangeiraException extends DaoException {
//atributos
    private String campoViolado;
    private String tabelaViolada;
    private String valorDeViolacao;

//construtor
    public ViolacaoDeRegistroDeChaveEstrangeiraException(SQLException causa){
        super(causa);

        this.campoViolado = Util.subStringCampoDeViolacaoDeRegistroFK(causa);
        this.tabelaViolada = Util.subStringTabelaDeViolacaoDeRegistroFK(causa);
        this.valorDeViolacao = Util.subStringValorDeViolacaoDeRegistroFK(causa);
    }
//getters

    public String getCampoViolado() {
        return campoViolado;
    }

    public String getTabelaViolada() {
        return tabelaViolada;
    }

    public String getValorDeViolacao() {
        return valorDeViolacao;
    }

//outros
    @Override
    public String gerarMensagemPorSQLException(SQLException causa) {
        return " - Violação de registro de chave estrangeira, valor \"" + valorDeViolacao + "\" do campo \"" + campoViolado + "\" não existe na tabela \"" + tabelaViolada + "\".";
    }

    /**
     * Classe utilitária de subStrings da mensagem original de erro.
     */
    private static class Util{

        private static String subStringValorDeViolacaoDeRegistroFK(SQLException causa) {
            String messege = causa.getMessage();
            return "\u001B[33m" + messege.substring(messege.lastIndexOf("(") + 1, messege.lastIndexOf(")")) + "\u001B[0m";
        }

        private static String subStringTabelaDeViolacaoDeRegistroFK(SQLException causa) {
            String messege = causa.getMessage();
            return "\u001B[33m" + messege.substring(messege.indexOf("(") + 3, messege.indexOf(")")) + "\u001B[0m";
        }

        private static String subStringCampoDeViolacaoDeRegistroFK(SQLException causa) {
            String messege = causa.getMessage();
            return "\u001B[33m" + messege.substring(messege.indexOf("(") + 1, messege.indexOf(")")) + "\u001B[0m";
        }
    }
}
