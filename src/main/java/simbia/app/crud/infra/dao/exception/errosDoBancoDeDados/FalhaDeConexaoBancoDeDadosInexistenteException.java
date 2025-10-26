package simbia.app.crud.infra.dao.exception.errosDoBancoDeDados;

import simbia.app.crud.infra.dao.abstractclasses.DaoException;

import java.sql.SQLException;

/**
 * Classe de falha de conexao por banco de dados inexistente durante comunicacao com banco de dados postgresql.
 */
public class FalhaDeConexaoBancoDeDadosInexistenteException extends DaoException {
    public FalhaDeConexaoBancoDeDadosInexistenteException(SQLException causa) {
        super(causa);
    }

    @Override
    public String gerarMensagemPorSQLException(SQLException causa) {
        return " - Banco de dados " + Util.subStringBD() + " inexistente.";
    }

    /**
     * Classe utilitária de subStrings das variáveis de ambiente de conexao.
     */
    private static class Util{

        private static String subStringBD(){
            String url =  System.getenv("DB_URL");
            int lastIndexOf = url.lastIndexOf('/');
            return "\u001B[33m" + url.substring(lastIndexOf + 1) + "\u001B[0m";
        }
    }
}
