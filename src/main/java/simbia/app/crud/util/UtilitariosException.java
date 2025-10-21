package simbia.app.crud.util;

import simbia.app.crud.infra.dao.exception.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class UtilitariosException {
    public static RuntimeException gerarExceptionEspecializadaPorSQLException(SQLException causa){
        CodigoErroPostgres tipoDeErro = CodigoErroPostgres.porSqlState(causa.getSQLState());

        return switch (tipoDeErro){
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

    public static String recuperarDataEHoraFormatada(){
        return "[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "]";
    }
}

