package simbia.app.crud.infra.dao.conection;

import io.github.cdimascio.dotenv.Dotenv;
import simbia.app.crud.infra.dao.exception.ConexaoException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ManipuladorConexao {
    public static Connection conectar(){
        try{
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(System.getenv("DB_URL"), System.getenv("DB_USER"), System.getenv("DB_PASSWORD"));
        }catch(ClassNotFoundException e){
            throw new RuntimeException("Não foi possível acessar o driver para a conexão com o banco de dados.\n" + e);
        } catch (SQLException e) {
            throw new ConexaoException(e);
        }
    }
    public static void desconectar(Connection con){
        try{
            if (con != null && !con.isClosed()){
                con.close();
            }
        } catch (SQLException e) {
            throw new ConexaoException(e);
        }
    }
}
