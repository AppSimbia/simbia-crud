package simbia.app.crud.infra.dao.exception;

import simbia.app.crud.infra.dao.abstractclasses.DaoException;

import java.sql.SQLException;

/**
 * Classe de erro de violacao desconhecida durante comunicacao com banco de dados postgresql.
 */
public class ViolacaoDesconhecidaException extends DaoException {

    public ViolacaoDesconhecidaException(SQLException causa){
        super(causa);
    }

    @Override
    public String gerarMensagemPorSQLException(SQLException causa) {
        return " - Violacao desconhecida, SQLSTATE: " + Util.recuperarSQLStateDaSQLException(causa) + ".";
    }

    /**
     * Classe utilitária de recuperacao da informacoes do SQLException original.
     */
    private static class Util{
        private static String recuperarSQLStateDaSQLException(SQLException causa){
            return causa.getSQLState();
        }
    }
}
