package simbia.app.crud.infra.dao.exception.errosDoBancoDeDados;

import simbia.app.crud.infra.dao.abstractclasses.DaoException;

import java.sql.SQLException;

/**
 * Classe de falha de conexao geral durante comunicacao com banco de dados postgresql.
 */
public class FalhaDeConexaoGeralException extends DaoException {

    public FalhaDeConexaoGeralException(SQLException causa){
        super(causa);
    }

    @Override
    public String gerarMensagemPorSQLException(SQLException causa) {
        return " - Host ou porta de acesso incorretos, tambem eh possivel que o padrao de URL esteja incorreto ou o banco de dados esteja desligado";
    }
}
