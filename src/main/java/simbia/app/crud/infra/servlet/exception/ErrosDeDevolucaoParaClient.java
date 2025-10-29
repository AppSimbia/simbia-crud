package simbia.app.crud.infra.servlet.exception;

/**
 * Enumeração dos tipos de erro que podem ser devolvidos para o cliente.
 *
 * Define os possíveis erros que ocorrem durante o processamento de requisições
 * e que devem ser comunicados ao usuário através da interface.
 *
 * Valores:
 * - EMAIL_OU_SENHA_INCORRETOS: credenciais de autenticação inválidas
 * - ERRO_DE_COMUNICACAO_COM_O_BANCO_DE_DADOS: falha na comunicação com o banco de dados
 */
public enum ErrosDeDevolucaoParaClient {
    EMAIL_OU_SENHA_INCORRETOS,
    ERRO_DE_COMUNICACAO_COM_O_BANCO_DE_DADOS
}
