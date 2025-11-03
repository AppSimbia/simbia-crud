package simbia.app.crud.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe de entidade da tabela {@code VANTAGEM}
 */
public class Vantagem {
//atributos
    private long idVantagem; //PK
    private String nomeVantagem;
    private String descricao;

//atributos>constantes
    private static final String COLUNA_PK_VANTAGEM = "idvantagem";
    private static final String COLUNA_NOME_VANTAGEM = "cnmvantagem";
    private static final String COLUNA_DESCRICAO_VANTAGEM = "cdescricao";

//construtores
    /**
     * Construtor completo da entidade Vantagem a partir de um {@link ResultSet}.
     * @param resultSet Um objeto {@link ResultSet} com os dados extraídos do banco de dados.
     * @throws SQLException Caso algum dado não seja adequado.
     */
    public Vantagem(ResultSet resultSet) throws SQLException {
        this.idVantagem = resultSet.getLong(COLUNA_PK_VANTAGEM);
        this.nomeVantagem = resultSet.getString(COLUNA_NOME_VANTAGEM);
        this.descricao = resultSet.getString(COLUNA_DESCRICAO_VANTAGEM);
    }

    /**
     * Construtor completo da entidade Vantagem.
     * @param idVantagem ID único da vantagem
     * @param nomeVantagem Nome da vantagem
     * @param descricao Descrição da vantagem
     */
    public Vantagem(long idVantagem, String nomeVantagem, String descricao) {
        this.idVantagem = idVantagem;
        this.nomeVantagem = nomeVantagem;
        this.descricao = descricao;
    }

    /**
     * Construtor da entidade Vantagem sem a {@code primary key}.
     * @param nomeVantagem Nome da vantagem
     * @param descricao Descrição da vantagem
     */
    public Vantagem(String nomeVantagem, String descricao) {
        this.nomeVantagem = nomeVantagem;
        this.descricao = descricao;
    }

    public Vantagem(){}

//toString
    @Override
    public String toString() {
        return "Vantagem{" +
                "idVantagem=" + idVantagem +
                ", nomeVantagem='" + nomeVantagem + '\'' +
                ", descricao='" + descricao + '\'' +
                '}';
    }

//getters
    public long getIdVantagem() {
        return idVantagem;
    }

    public String getNomeVantagem() {
        return nomeVantagem;
    }

    public String getDescricao() {
        return descricao;
    }

//setters
    public void setIdVantagem(long idVantagem) {
        this.idVantagem = idVantagem;
    }

    public void setNomeVantagem(String nomeVantagem) {
        this.nomeVantagem = nomeVantagem;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}