package simbia.app.crud.infra.dao.abstractclasses;

import java.sql.SQLException;

import static simbia.app.crud.util.UtilitariosException.recuperarDataEHoraFormatada;

/**
 * Classe abstrata de excessoes relacionadas ao DAO
 */
public abstract class DaoException extends RuntimeException{
//atributos
    private String mensagem;
    private String dataHora;

//contrutor
    public DaoException(SQLException causa){
        this.dataHora = recuperarDataEHoraFormatada();
        this.mensagem = gerarMensagemPorSQLException(causa);
    }

//toString
    public String toString(){
        return dataHora + mensagem;
    }

//getter
    public String getMensagem(){
        return mensagem;
    }
//outros

    /**
     * Método que gera a mensagem de erro personalizada
     *
      * @param causa objeto SQLException originalmente capturado
     * @return uma mensagem personalizada descritiva
     */
    public abstract String gerarMensagemPorSQLException(SQLException causa);
}
