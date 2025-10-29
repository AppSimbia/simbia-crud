package simbia.app.crud.infra.dao.conection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static simbia.app.crud.util.UtilitariosException.gerarExceptionEspecializadaPorSQLException;

/**
 * Classe que manipula a conexao com o banco de dados Postegresql
 **/
public class ManipuladorConexao {

    /**
     * método que estabelece conexao atravez das variaveis de ambiente recuperadas do {@code System}
     * e com o Driver Postgresql de JDBC.
     *
     * @return Um objeto {@code Connection} manipulavel
     */
    public static Connection conectar(){
        try{
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(System.getenv("DB_URL"), System.getenv("DB_USER"), System.getenv("DB_PASSWORD"));
        }catch(ClassNotFoundException e){
            throw new RuntimeException("Não foi possível acessar o driver para a conexão com o banco de dados.\n" + e);
        } catch (SQLException causa) {
            throw gerarExceptionEspecializadaPorSQLException(causa);
        }
    }

    /**
     * Metodo que fecha um objeto de conexao Postgresql.
     *
     * @param conexao O obejeto {@code Connection} a ser fechado.
     */
    public static void desconectar(Connection conexao){
        try{
            if (conexao != null && !conexao.isClosed()){
                conexao.close();
            }
        } catch (SQLException causa) {
            throw gerarExceptionEspecializadaPorSQLException(causa);
        }
    }
}
