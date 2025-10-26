package simbia.app.crud.infra.dao.exception.errosDeOperacao;

public class naoHouveAlteracaoNoBancoDeDadosException extends RuntimeException {
  public naoHouveAlteracaoNoBancoDeDadosException(String message) {
    super(message);
  }
}
