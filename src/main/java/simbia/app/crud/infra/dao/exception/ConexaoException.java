package simbia.app.crud.infra.dao.exception;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Classe que trata erros de conexão com o banco de dados.
 */
public class ConexaoException extends RuntimeException {

    private String mensagem;
    /**
     * Contrutor da classe {@code ConexaoException}
     * @param cause uma {@code SQLException} que denuncia o tipo do erro.
     */
    public ConexaoException(SQLException cause){
        mensagem = Util.gerarMensagem(cause);
    }

    /**
     * Formatador geral da Classe
     * @return Um objeto do tipo {@code String} com a mensagem de erro.
     */
    public String toString(){
        return "ConexaoException: " + mensagem;
    }

    /**
     * Classe utilitária da {@code ConexaoException}
     */
    private static class Util{
        /**
         * Método utilitário de geração de mensagem de erro.
         * @param cause Deve ser um código de erro Postgres.
         * @return Uma mensagem personalizada denunciando o erro.
         */
        private static String gerarMensagem(SQLException cause) {
            String dataHora = "[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "]";
            CodigoErroPostgres erro = CodigoErroPostgres.porSqlState(cause.getSQLState());

            return switch (erro){
                case SENHA_INCORRETA -> dataHora + " - Senha de acesso " + subStringPASS() + " do banco de dados esta incorreta para usuario " + subStringUSER() + ".";
                case BANCO_DE_DADOS_INEXISTENTE -> dataHora + " - Banco de dados " + subStringBD() + " inexistente.\n";
                case HOST_OU_PORTA_ERRADA -> dataHora + " - Host ou porta de acesso incorretos, tambem eh possivel que o padrao de URL esteja incorreto ou o banco de dados esteja desligado";
                case CONEXAO_CAIU -> dataHora + " - Foi possivel realizar a conexao ao banco de dados, porem ela caiu.";
                case DRIVER_INADEQUADO -> dataHora + " - Driver/protocolo de acesso inadequado para o banco de dados.";
                default -> dataHora + " - Erro de conexao desconhecido: " + cause.getSQLState() + ".";
            };
        }

        /**
         * Método que retorna apenas o nome banco de dados do atributo de configuração {@code BD_URL}.
         * @return Um objeto {@code String} com a subString do nome do banco de dados.
         */
        private static String subStringBD(){
            String url =  System.getenv("DB_URL");
            int lastIndexOf = url.lastIndexOf('/');
            return "\u001B[33m" + url.substring(lastIndexOf + 1) + "\u001B[0m";
        }

        /**
         * Método que retorna apenas a senha do banco de dados do atributo de configuração {@code BD_PASSWORD}.
         * @return Um objeto {@code String} com a subString da senha do banco de dados protegida.
         */
        private static String subStringPASS(){
            String senha = System.getenv("DB_PASSWORD");
            String senhaProtegida = "*".repeat(senha.length());
            return "\u001B[33m" + senhaProtegida + "\u001B[0m";
        }

        /**
         * Método que retorna apenas a senha do banco de dados do atributo de configuração {@code BD_USERR}.
         * @return Um objeto {@code String} com a subString do usuário do banco de dados.
         */
        private static String subStringUSER(){
            return "\u001B[33m" + System.getenv("DB_USER") + "\u001B[0m";
        }
    }
}