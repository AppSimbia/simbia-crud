package simbia.app.crud.infra.dao.exception.errosDoBancoDeDados;

import simbia.app.crud.infra.dao.abstractclasses.DaoException;

import java.sql.SQLException;

/**
 * Classe de erro de violacao unicidade durante comunicacao com banco de dados postgresql.
 */
public class ViolacaoDeUnicidadeException extends DaoException {
//atributos
  private String tabelaViolada;
  private String campoViolado;
  private String constraintViolada;

//construtor
  public ViolacaoDeUnicidadeException(SQLException causa){
    super(causa);

    String constraintViolada = Util.subStringConstraint(causa);

    this.constraintViolada = constraintViolada;
    this.campoViolado = Util.subStringCampoDeViolacaoDeUnicidade(constraintViolada);
    this.tabelaViolada = Util.subStringTabelaDeViolacaoDeUnicidade(constraintViolada);
  }

//getters
  public String getTabelaViolada() {
    return tabelaViolada;
  }

  public String getCampoViolado() {
    return campoViolado;
  }

  public String getConstraintViolada() {
    return constraintViolada;
  }

//outros
  @Override
  public String gerarMensagemPorSQLException(SQLException causa) {
    return "Um " + Util.subStringCampoDeViolacaoDeUnicidade(Util.subStringConstraint(causa)) + " como este já foi registrado antes";
  }

  /**
   * Classe utilitária de subStrings da mensagem original de erro.
   */
  private static class Util{
    private static String subStringCampoDeViolacaoDeUnicidade(String constraint){
      return "\u001B[33m" + constraint.split("_")[1] + "\u001B[0m";
    }

    private static String subStringTabelaDeViolacaoDeUnicidade(String constraint){
      return "\u001B[33m" + constraint.split("_")[0] + "\u001B[0m";
    }

    private static String subStringConstraint(SQLException causa) {
      String messege = causa.getMessage();
      return "\u001B[33m" + messege.substring(messege.indexOf("\"") + 1, messege.lastIndexOf("\"")) + "\u001B[0m";
    }
  }
}
