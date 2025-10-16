package simbia.app.crud.model.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe de entidade da tabela {@code PLANO}
 */
public class Plano {
//atributos
    private long idPlano; //PK
    private BigDecimal valor;
    private boolean ativo;
    private String nomePlano;

//atributos>constantes
    private static final String COLUNA_PK_PLANO = "idplano";
    private static final String COLUNA_VALOR_PLANO = "nvalor";
    private static final String COLUNA_ATIVO_PLANO = "nativo";
    private static final String COLUNA_NOME_PLANO = "cnmplano";

//construtores
    /**
     * Construtor completo da entidade Plano a partir de um {@link ResultSet}.
     * @param resultSet Um objeto {@link ResultSet} com os dados extraídos do banco de dados.
     * @throws SQLException Caso algum dado não seja adequado.
     */
    public Plano(ResultSet resultSet) throws SQLException {
        this.idPlano = resultSet.getLong(COLUNA_PK_PLANO);
        this.valor = resultSet.getBigDecimal(COLUNA_VALOR_PLANO);
        this.ativo = resultSet.getInt(COLUNA_ATIVO_PLANO) > 0;
        this.nomePlano = resultSet.getString(COLUNA_NOME_PLANO);
    }

    /**
     * Construtor completo da entidade Plano.
     * @param idPlano ID único do plano
     * @param valor Valor do plano
     * @param ativo Status ativo/inativo
     * @param nomePlano Nome do plano
     */
    public Plano(long idPlano, BigDecimal valor, boolean ativo, String nomePlano) {
        this.idPlano = idPlano;
        this.valor = valor;
        this.ativo = ativo;
        this.nomePlano = nomePlano;
    }

    /**
     * Construtor da entidade Plano sem a {@code primary key}.
     * @param valor Valor do plano
     * @param ativo Status ativo/inativo
     * @param nomePlano Nome do plano
     */
    public Plano(BigDecimal valor, boolean ativo, String nomePlano) {
        this.valor = valor;
        this.ativo = ativo;
        this.nomePlano = nomePlano;
    }

    public Plano(){}

//toString
    @Override
    public String toString() {
        return "Plano{" +
                "idPlano=" + idPlano +
                ", valor=" + valor +
                ", ativo=" + ativo +
                ", nomePlano='" + nomePlano + '\'' +
                '}';
    }

//getters
    public long getIdPlano() {
        return idPlano;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public boolean isAtivo() { // Convenção para boolean: usar "is" em vez de "get"
        return ativo;
    }

    public String getNomePlano() {
        return nomePlano;
    }

//setters
    public void setIdPlano(long idPlano) {
        this.idPlano = idPlano;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public void setNomePlano(String nomePlano) {
        this.nomePlano = nomePlano;
    }
}