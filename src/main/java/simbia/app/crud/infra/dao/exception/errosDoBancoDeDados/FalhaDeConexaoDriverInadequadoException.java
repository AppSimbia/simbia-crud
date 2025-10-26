package simbia.app.crud.infra.dao.exception;

import simbia.app.crud.infra.dao.abstractclasses.DaoException;

import java.sql.SQLException;

public class FalhaDeConexaoDriverInadequadoException extends DaoException {

    public FalhaDeConexaoDriverInadequadoException(SQLException causa) {
        super(causa);
    }

    @Override
    public String gerarMensagemPorSQLException(SQLException causa) {
        return " - Driver/protocolo de acesso inadequado para o banco de dados.";
    }
}
