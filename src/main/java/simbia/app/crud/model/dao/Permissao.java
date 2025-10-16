package simbia.app.crud.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe de entidade da tabela {@code PERMISSAO}
 */
public class Permissao {
//atributos
    private long idPermissao; // PK
    private String nomePermissao;
    private String descricao;

//atributos>constantes
    private static final String COLUNA_PK_PERMISSAO = "idpermissao";
    private static final String COLUNA_NOME_PERMISSAO = "cnmpermissao";
    private static final String COLUNA_DESCRICAO_PERMISSAO = "cdescricao";

//construtores
    /**
     * Construtor completo da entidade Permissao a partir de um {@link ResultSet}.
     * @param resultSet Um objeto {@link ResultSet} com os dados extraídos do banco de dados.
     * @throws SQLException Caso algum dado não seja adequado.
     */
    public Permissao(ResultSet resultSet) throws SQLException {
        this.idPermissao = resultSet.getLong(COLUNA_PK_PERMISSAO);
        this.nomePermissao = resultSet.getString(COLUNA_NOME_PERMISSAO);
        this.descricao = resultSet.getString(COLUNA_DESCRICAO_PERMISSAO);
    }

    /**
     * Construtor completo da entidade Permissao.
     * @param idPermissao ID único da permissão (PK)
     * @param nomePermissao Nome da permissão
     * @param descricao Descrição da permissão
     */
    public Permissao(long idPermissao, String nomePermissao, String descricao) {
        this.idPermissao = idPermissao;
        this.nomePermissao = nomePermissao;
        this.descricao = descricao;
    }

    /**
     * Construtor da entidade Permissao sem a {@code primary key}.
     * @param nomePermissao Nome da permissão
     * @param descricao Descrição da permissão
     */
    public Permissao(String nomePermissao, String descricao) {
        this.nomePermissao = nomePermissao;
        this.descricao = descricao;
    }

    public Permissao(){}
//toString
    @Override
    public String toString() {
        return "Permissao{" +
                "idPermissao=" + idPermissao +
                ", nomePermissao='" + nomePermissao + '\'' +
                ", descricao='" + descricao + '\'' +
                '}';
    }

//getters
    public long getIdPermissao() {
        return idPermissao;
    }

    public String getNomePermissao() {
        return nomePermissao;
    }

    public String getDescricao() {
        return descricao;
    }

//setters
    public void setIdPermissao(long idPermissao) {
        this.idPermissao = idPermissao;
    }

    public void setNomePermissao(String nomePermissao) {
        this.nomePermissao = nomePermissao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}