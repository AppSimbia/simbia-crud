package simbia.app.crud.infra.servlet.exception;

/**
 * Enumeração dos tipos de erro que podem ser devolvidos para o cliente.
 *
 * Define os possíveis erros que ocorrem durante o processamento de requisições
 * e que devem ser comunicados ao usuário através da interface.
 */
public enum ErrosDeDevolucaoParaClient {
    EMAIL_OU_SENHA_INCORRETOS,
    ERRO_DE_COMUNICACAO_COM_O_BANCO_DE_DADOS,
    NAO_HOUVE_ALTERACAO_NO_BANCO
}
