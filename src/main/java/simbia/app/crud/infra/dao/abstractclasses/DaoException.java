package simbia.app.crud.infra.dao.abstractclasses;

import java.sql.SQLException;

import static simbia.app.crud.util.UtilitariosException.recuperarDataEHoraFormatada;

public abstract class DaoException extends RuntimeException{

    private String mensagem;
    private String dataHora;

    public DaoException(SQLException causa){
        this.dataHora = recuperarDataEHoraFormatada();
        this.mensagem = gerarMensagemPorSQLException(causa);
    }

    public String toString(){
        return dataHora + mensagem;
    }

    public abstract String gerarMensagemPorSQLException(SQLException causa);
}
