package simbia.app.crud.infra.dao.conection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static simbia.app.crud.util.UtilitariosException.gerarExceptionEspecializadaPorSQLException;


public class ManipuladorConexao {
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
    public static void desconectar(Connection con){
        try{
            if (con != null && !con.isClosed()){
                con.close();
            }
        } catch (SQLException causa) {
            throw gerarExceptionEspecializadaPorSQLException(causa);
        }
    }
}
