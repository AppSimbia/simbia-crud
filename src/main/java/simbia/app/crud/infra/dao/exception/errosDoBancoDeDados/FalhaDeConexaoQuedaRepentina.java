package simbia.app.crud.infra.dao.exception;

import simbia.app.crud.infra.dao.abstractclasses.DaoException;

import java.sql.SQLException;

public class FalhaDeConexaoQuedaRepentina extends DaoException {

    public FalhaDeConexaoQuedaRepentina(SQLException causa) {
        super(causa);
    }

  @Override
  public String gerarMensagemPorSQLException(SQLException causa) {
    return " - Foi possivel realizar a conexao ao banco de dados, porem ela caiu repentinamente.";
  }
}
