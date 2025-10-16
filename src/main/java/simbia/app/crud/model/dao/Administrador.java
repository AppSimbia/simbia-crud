package simbia.app.crud.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe de entidade da tabela {@code ADMIN}
 */
public class Administrador {
//atributos
    private long idAdministrador; //PK
    private String email;
    private String senha;
    private String nome;

//atributos>constantes
    private static final String COLUNA_PK_ADMINISTRADOR = "idadministrador";
    private static final String COLUNA_EMAIL_ADMINISTRADOR = "cemail";
    private static final String COLUNA_SENHA_ADMINISTRADOR = "csenha";
    private static final String COLUNA_NOME_ADMINISTRADOR = "cnome";

//construtores
    /**
     * Construtor completo da entidade Administrador
     * @param resultSet Um objeto {@link ResultSet} com as informações extraídas do banco de dados.
     * @throws SQLException Caso algum dado não seja adequado.
     */
    public Administrador(ResultSet resultSet) throws SQLException{
        this.idAdministrador = resultSet.getLong(COLUNA_PK_ADMINISTRADOR);
        this.email = resultSet.getString(COLUNA_EMAIL_ADMINISTRADOR);
        this.senha = resultSet.getString(COLUNA_SENHA_ADMINISTRADOR);
        this.nome = resultSet.getString(COLUNA_NOME_ADMINISTRADOR);
    }

    /**
     * Construtor completo da entidade Administrador.
     * @param idAdministrador ID único do administrador
     * @param email Email do administrador
     * @param senha Senha do administrador
     * @param nome Nome do administrador
     */
    public Administrador(long idAdministrador, String email, String senha, String nome) {
        this.idAdministrador = idAdministrador;
        this.email = email;
        this.senha = senha;
        this.nome = nome;
    }

    /**
     * Construtor da entidade Administrador sem a {@code primary key}.
     * @param email Email do administrador
     * @param senha Senha do administrador
     * @param nome Nome do Administrador
     */
    public Administrador(String email, String senha, String nome) {
        this.email = email;
        this.senha = senha;
        this.nome = nome;
    }
    /**
     * Construtor da entidade Administrador sem a {@code primary key} e o nome.
     * @param email Email do administrador
     * @param senha Senha do administrador
     */
    public Administrador(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public Administrador(){}

//toString
    @Override
    public String toString() {
        return "Administrador{" +
                "idAdministrador=" + idAdministrador +
                ", email='" + email + '\'' +
                ", nome='" + nome + '\'' +
                ", senha=[PROTEGIDO]" +
                '}';
    }

//getters
    public long getIdAdministrador() {
        return idAdministrador;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public String getNome(){
        return nome;
    }

//setters
    public void setIdAdministrador(long idAdministrador) {
        this.idAdministrador = idAdministrador;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setNome(String nome){
        this.nome = nome;
    }
}