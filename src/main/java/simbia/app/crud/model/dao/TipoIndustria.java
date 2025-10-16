package simbia.app.crud.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe de entidade da tabela {@code TIPOINDUSTRIA}
 */
public class TipoIndustria {
//atributos
    private long idTipoIndustria; // PK
    private String nomeTipoIndustria;
    private String descricao;

//atributos>constantes
    private static final String COLUNA_PK_TIPO_INDUSTRIA = "idtipoindustria";
    private static final String COLUNA_NOME_TIPO_INDUSTRIA = "cnmtipoindustria";
    private static final String COLUNA_DESCRICAO_TIPO_INDUSTRIA = "cdescricao";

//construtores
    /**
     * Construtor completo da entidade TipoIndustria a partir de um {@link ResultSet}.
     * @param resultSet Um objeto {@link ResultSet} com os dados extraídos do banco de dados.
     * @throws SQLException Caso algum dado não seja adequado.
     */
    public TipoIndustria(ResultSet resultSet) throws SQLException {
        this.idTipoIndustria = resultSet.getLong(COLUNA_PK_TIPO_INDUSTRIA);
        this.nomeTipoIndustria = resultSet.getString(COLUNA_NOME_TIPO_INDUSTRIA);
        this.descricao = resultSet.getString(COLUNA_DESCRICAO_TIPO_INDUSTRIA);
    }

    /**
     * Construtor completo da entidade TipoIndustria.
     * @param idTipoIndustria ID único do tipo de indústria (PK)
     * @param nomeTipoIndustria Nome do tipo de indústria
     * @param descricao Descrição do tipo de indústria
     */
    public TipoIndustria(long idTipoIndustria, String nomeTipoIndustria, String descricao) {
        this.idTipoIndustria = idTipoIndustria;
        this.nomeTipoIndustria = nomeTipoIndustria;
        this.descricao = descricao;
    }

    /**
     * Construtor da entidade TipoIndustria sem a {@code primary key}.
     * @param nomeTipoIndustria Nome do tipo de indústria
     * @param descricao Descrição do tipo de indústria
     */
    public TipoIndustria(String nomeTipoIndustria, String descricao) {
        this.nomeTipoIndustria = nomeTipoIndustria;
        this.descricao = descricao;
    }

    public TipoIndustria(){}
//toString
    @Override
    public String toString() {
        return "TipoIndustria{" +
                "idTipoIndustria=" + idTipoIndustria +
                ", nomeTipoIndustria='" + nomeTipoIndustria + '\'' +
                ", descricao='" + descricao + '\'' +
                '}';
    }

//getters
    public long getIdTipoIndustria() {
        return idTipoIndustria;
    }

    public String getNomeTipoIndustria() {
        return nomeTipoIndustria;
    }

    public String getDescricao() {
        return descricao;
    }

//setters
    public void setIdTipoIndustria(long idTipoIndustria) {
        this.idTipoIndustria = idTipoIndustria;
    }

    public void setNomeTipoIndustria(String nomeTipoIndustria) {
        this.nomeTipoIndustria = nomeTipoIndustria;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}