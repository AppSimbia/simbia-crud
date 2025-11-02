package simbia.app.crud.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe de entidade da tabela {@code CATEGORIAPRODUTO}
 */
public class CategoriaProduto {
    //atributos
    private long idCategoriaProduto; //PK
    private String nomeCategoria;
    private String descricao;

    //atributos>constantes
    private static final String COLUNA_PK_CATEGORIA_PRODUTO = "idcategoriaproduto";
    private static final String COLUNA_NOME_CATEGORIA_PRODUTO = "cnmcategoria";
    private static final String COLUNA_DESCRICAO_CATEGORIA_PRODUTO = "cdescricao";

//construtores
    /**
     * Construtor completo da entidade CategoriaProduto a partir de um {@link ResultSet}.
     * @param resultSet Um objeto {@link ResultSet} com os dados extraídos do banco de dados.
     * @throws SQLException Caso algum dado não seja adequado.
     */
    public CategoriaProduto(ResultSet resultSet) throws SQLException {
        this.idCategoriaProduto = resultSet.getLong(COLUNA_PK_CATEGORIA_PRODUTO);
        this.nomeCategoria = resultSet.getString(COLUNA_NOME_CATEGORIA_PRODUTO);
        this.descricao = resultSet.getString(COLUNA_DESCRICAO_CATEGORIA_PRODUTO);
    }

    /**
     * Construtor completo da entidade CategoriaProduto.
     * @param idCategoriaProduto ID único da categoria de produto
     * @param nomeCategoria Nome da categoria de produto
     * @param descricao Descrição da categoria de produto
     */
    public CategoriaProduto(long idCategoriaProduto, String nomeCategoria, String descricao) {
        this.idCategoriaProduto = idCategoriaProduto;
        this.nomeCategoria = nomeCategoria;
        this.descricao = descricao;
    }

    /**
     * Construtor da entidade CategoriaProduto sem a {@code primary key}.
     * @param nomeCategoria Nome da categoria de produto
     * @param descricao Descrição da categoria de produto
     */
    public CategoriaProduto(String nomeCategoria, String descricao) {
        this.nomeCategoria = nomeCategoria;
        this.descricao = descricao;
    }

    public CategoriaProduto(){}
    //toString
    @Override
    public String toString() {
        return "CategoriaProduto{" +
                "idCategoriaProduto=" + idCategoriaProduto +
                ", nomeCategoria='" + nomeCategoria + '\'' +
                ", descricao='" + descricao + '\'' +
                '}';
    }

    //getters
    public long getIdCategoriaProduto() {
        return idCategoriaProduto;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public String getDescricao() {
        return descricao;
    }

    //setters
    public void setIdCategoriaProduto(long idCategoriaProduto) {
        this.idCategoriaProduto = idCategoriaProduto;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
