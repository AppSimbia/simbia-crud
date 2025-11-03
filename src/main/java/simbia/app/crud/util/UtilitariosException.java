package simbia.app.crud.util;

import simbia.app.crud.infra.dao.exception.errosDoBancoDeDados.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Classe utilitária para tratamento de exceções e formatação de timestamps.
 *
 * Fornece métodos auxiliares para converter SQLException em exceções especializadas
 * do sistema e para gerar timestamps formatados para mensagens de erro.
 */
public class UtilitariosException {

    /**
     * Gera uma exceção especializada com base no código de erro SQL (SQLState).
     *
     * Analisa o SQLState da SQLException recebida e retorna uma exceção específica
     * do domínio da aplicação, facilitando o tratamento de erros de banco de dados.
     *
     * Mapeamento de erros:
     * - VIOLACAO_DE_UNICIDADE → ViolacaoDeUnicidadeException
     * - VIOLACAO_DE_OBRIGATORIEDADE → ViolacaoDeObrigatoriedadeException
     * - VIOLACAO_DE_TAMANHO → ViolacaoDeTamanhoException
     * - VIOLACAO_DE_REGISTRO_DE_FK_INEXISTENTE → ViolacaoDeRegistroDeChaveEstrangeiraException
     * - HOST_OU_PORTA_ERRADA → FalhaDeConexaoGeralException
     * - SENHA_INCORRETA → FalhaDeConexaoSenhaIncorretaException
     * - BANCO_DE_DADOS_INEXISTENTE → FalhaDeConexaoBancoDeDadosInexistenteException
     * - CONEXAO_CAIU → FalhaDeConexaoQuedaRepentina
     * - DRIVER_INADEQUADO → FalhaDeConexaoDriverInadequadoException
     * - Outros casos → ViolacaoDesconhecidaException
     *
     * @param causa a SQLException original capturada
     * @return uma RuntimeException especializada correspondente ao tipo de erro SQL
     */
    public static RuntimeException gerarExceptionEspecializadaPorSQLException(SQLException causa) {
        CodigoErroPostgres tipoDeErro = CodigoErroPostgres.porSqlState(causa.getSQLState());

        return switch (tipoDeErro) {
            case VIOLACAO_DE_UNICIDADE -> new ViolacaoDeUnicidadeException(causa);

            case VIOLACAO_DE_OBRIGATORIEDADE -> new ViolacaoDeObrigatoriedadeException(causa);

            case VIOLACAO_DE_TAMANHO -> new ViolacaoDeTamanhoException(causa);

            case VIOLACAO_DE_REGISTRO_DE_FK_INEXISTENTE -> new ViolacaoDeRegistroDeChaveEstrangeiraException(causa);

            case HOST_OU_PORTA_ERRADA -> new FalhaDeConexaoGeralException(causa);

            case SENHA_INCORRETA -> new FalhaDeConexaoSenhaIncorretaException(causa);

            case BANCO_DE_DADOS_INEXISTENTE -> new FalhaDeConexaoBancoDeDadosInexistenteException(causa);

            case CONEXAO_CAIU -> new FalhaDeConexaoQuedaRepentina(causa);

            case DRIVER_INADEQUADO -> new FalhaDeConexaoDriverInadequadoException(causa);

            default -> throw new ViolacaoDesconhecidaException(causa);
        };
    }

    /**
     * Recupera a data e hora atual formatada para uso em mensagens de erro.
     * Formato: "[dd/MM/yyyy HH:mm:ss]"
     *
     * @return string com timestamp formatado entre colchetes
     */
    public static String recuperarDataEHoraFormatada() {
        return "[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "]";
    }
}