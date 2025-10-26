package simbia.app.crud.infra.dao.exception;

/**
 * Enumeração de códigos de erro do PostgreSQL.
 */
public enum CodigoErroPostgres {
    //erros>conexao
    SENHA_INCORRETA("28P01"),
    BANCO_DE_DADOS_INEXISTENTE("3D000"),
    HOST_OU_PORTA_ERRADA("08001"),
    CONEXAO_CAIU("08006"),
    DRIVER_INADEQUADO("08P01"),

    //erros>credenciais
    VIOLACAO_DE_UNICIDADE("23505"),
    VIOLACAO_DE_OBRIGATORIEDADE("23502"),
    VIOLACAO_DE_TAMANHO("22001"),
    VIOLACAO_DE_REGISTRO_DE_FK_INEXISTENTE("23503");

    private final String codigo;

    CodigoErroPostgres(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public static CodigoErroPostgres porSqlState(String sqlState) {
        CodigoErroPostgres erroCodigo = null;
        for (CodigoErroPostgres erro : values()) {
            if (erro.codigo.equals(sqlState)) {
                erroCodigo = erro;
            }
        }
        return erroCodigo;
    }
}