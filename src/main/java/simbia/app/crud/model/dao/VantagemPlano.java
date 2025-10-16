package simbia.app.crud.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe de entidade da tabela {@code VANTAGEMPLANO}
 */
public class VantagemPlano {
//atributos
    private long idVantagemPlano; // PK
    private long idPlano; // FK
    private long idVantagem; // FK

//atributos>constantes
    private static final String COLUNA_PK_VANTAGEM_PLANO = "idvantagemplano";
    private static final String COLUNA_FK_PLANO = "idplano";
    private static final String COLUNA_FK_VANTAGEM = "idvantagem";
//construtores
    /**
     * Construtor completo da entidade VantagemPlano a partir de um {@link ResultSet}.
     * @param resultSet Um objeto {@link ResultSet} com os dados extraídos do banco de dados.
     * @throws SQLException Caso algum dado não seja adequado.
     */
    public VantagemPlano(ResultSet resultSet) throws SQLException {
        this.idVantagemPlano = resultSet.getLong(COLUNA_PK_VANTAGEM_PLANO);
        this.idPlano = resultSet.getLong(COLUNA_FK_PLANO);
        this.idVantagem = resultSet.getLong(COLUNA_FK_VANTAGEM);
    }

    /**
     * Construtor completo da entidade VantagemPlano.
     * @param idVantagemPlano ID único da relação vantagem-plano (PK)
     * @param idPlano ID do plano (FK)
     * @param idVantagem ID da vantagem (FK)
     */
    public VantagemPlano(long idVantagemPlano, long idPlano, long idVantagem) {
        this.idVantagemPlano = idVantagemPlano;
        this.idPlano = idPlano;
        this.idVantagem = idVantagem;
    }

    /**
     * Construtor da entidade VantagemPlano sem a {@code primary key}.
     * @param idPlano ID do plano (FK)
     * @param idVantagem ID da vantagem (FK)
     */
    public VantagemPlano(long idPlano, long idVantagem) {
        this.idPlano = idPlano;
        this.idVantagem = idVantagem;
    }

    public VantagemPlano(){}
//toString
    @Override
    public String toString() {
        return "VantagemPlano{" +
                "idVantagemPlano=" + idVantagemPlano +
                ", idPlano=" + idPlano +
                ", idVantagem=" + idVantagem +
                '}';
    }

//getters
    public long getIdVantagemPlano() {
        return idVantagemPlano;
    }

    public long getIdPlano() {
        return idPlano;
    }

    public long getIdVantagem() {
        return idVantagem;
    }

//setters
    public void setIdVantagemPlano(long idVantagemPlano) {
        this.idVantagemPlano = idVantagemPlano;
    }

    public void setIdPlano(long idPlano) {
        this.idPlano = idPlano;
    }

    public void setIdVantagem(long idVantagem) {
        this.idVantagem = idVantagem;
    }
}