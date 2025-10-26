package simbia.app.crud.infra.dao.exception;

import simbia.app.crud.infra.dao.abstractclasses.DaoException;

import java.sql.SQLException;

/**
 * Classe de falha de conexao por senha incorreta durante comunicacao com banco de dados postgresql.
 */
public class FalhaDeConexaoSenhaIncorretaException extends DaoException {

    public FalhaDeConexaoSenhaIncorretaException(SQLException causa){
        super(causa);
    }

    @Override
    public String gerarMensagemPorSQLException(SQLException causa) {
        return " - Senha de acesso " + Util.subStringPASS() + " do banco de dados esta incorreta para usuario " + Util.subStringUSER() + ".";
    }

    /**
     * Classe utilitária de subStrings das variáveis de ambiente de conexao.
     */
    private static class Util{

        private static String subStringPASS(){
            String senha = System.getenv("DB_PASSWORD");
            String senhaProtegida = "*".repeat(senha.length());
            return "\u001B[33m" + senhaProtegida + "\u001B[0m";
        }

        private static String subStringUSER(){
            return "\u001B[33m" + System.getenv("DB_USER") + "\u001B[0m";
        }
    }
}
