package simbia.app.crud.infra.dao.exception;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Classe que trata erros relacionados às operações de DAO (Data Access Object).
 */
public class DaoException extends RuntimeException {

    private String mensagem;

    public DaoException(SQLException causa) {
        mensagem = Util.gerarMensagem(causa);
    }

    public String toString() {
        return "DaoException: " + mensagem;
    }

    /**
     * Classe utilitária da {@code DaoException}
     */
    private static class Util {

        private static String gerarMensagem(SQLException causa) {
            String dataHora = "[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "]";
            CodigoErroPostgres erro = CodigoErroPostgres.porSqlState(causa.getSQLState());

            return switch (erro) {
                case VIOLACAO_DE_UNICIDADE -> dataHora + " - Violação de unicidade, campo \"" + subStringCampoDeViolacaoDeUnicidade(subStringConstraint(causa)) + "\" da tabela \"" + subStringTabelaDeViolacaoDeUnicidade(subStringConstraint(causa)) + "\" já possuí este valor.";
                case VIOLACAO_DE_OBRIGATORIEDADE -> dataHora + " - Violação de obrigatoriedade, valor do campo \"" + subStringCampoDeViolacaoDeObrigatoriedade(causa) + "\" da tabela \"" + subStringTabelaDeViolacaoDeObrigatoriedade(causa) + "\" é nulo.";
                case VIOLACAO_DE_TAMANHO -> dataHora + " - Violação de tamanho, numero de caracteres maior que \"" + subStringTamanhoMaximoCaracteres(causa) + "\".";
                case VIOLACAO_DE_REGISTRO_DE_FK_INEXISTENTE -> dataHora + " - Violação de registro de chave estrangeira, valor \"" + subStringValorDeViolacaoDeRegistroFK(causa) + "\" do campo \"" + subStringCampoDeViolacaoDeRegistroFK(causa) + "\" não existe na tabela \"" + subStringTabelaDeViolacaoDeRegistroFK(causa) + "\".";
                default -> dataHora + " - Erro de DAO desconhecido: " + causa.getSQLState();
            };
        }

        private static String subStringConstraint(SQLException causa) {
            String messege = causa.getMessage();
            return "\u001B[33m" + messege.substring(messege.indexOf("\"") + 1, messege.lastIndexOf("\"")) + "\u001B[0m";
        }

        private static String subStringCampoDeViolacaoDeObrigatoriedade(SQLException causa) {
            String messege = causa.getMessage();
            String[] mensagemFatiada = messege.replaceAll("of", "|").split("\\|");
            return "\u001B[33m" + mensagemFatiada[0].substring(mensagemFatiada[0].indexOf("\"") + 1, mensagemFatiada[0].lastIndexOf("\"")) + "\u001B[0m";
        }

        private static String subStringTabelaDeViolacaoDeObrigatoriedade(SQLException causa) {
            String messege = causa.getMessage();
            String[] mensagemFatiada = messege.replaceAll("of", "|").split("\\|");
            return "\u001B[33m" + mensagemFatiada[1].substring(mensagemFatiada[1].indexOf("\"") + 1, mensagemFatiada[1].lastIndexOf("\"")) + "\u001B[0m";
        }

        private static String subStringTamanhoMaximoCaracteres(SQLException causa) {
            String messege = causa.getMessage();
            return "\u001B[33m" + messege.substring(messege.indexOf("(") + 1, messege.indexOf(")")) + "\u001B[0m";
        }

        private static String subStringCampoDeViolacaoDeRegistroFK(SQLException causa) {
            String messege = causa.getMessage();
            return "\u001B[33m" + messege.substring(messege.indexOf("(") + 1, messege.indexOf(")")) + "\u001B[0m";
        }

        private static String subStringValorDeViolacaoDeRegistroFK(SQLException causa) {
            String messege = causa.getMessage();
            return "\u001B[33m" + messege.substring(messege.lastIndexOf("(") + 1, messege.lastIndexOf(")")) + "\u001B[0m";
        }

        private static String subStringTabelaDeViolacaoDeRegistroFK(SQLException causa) {
            String messege = causa.getMessage();
            return "\u001B[33m" + messege.substring(messege.indexOf("(") + 3, messege.indexOf(")")) + "\u001B[0m";
        }

        private static String subStringCampoDeViolacaoDeUnicidade(String regra){
            return "\u001B[33m" + regra.split("_")[1] + "\u001B[0m";
        }

        private static String subStringTabelaDeViolacaoDeUnicidade(String regra){
            return "\u001B[33m" + regra.split("_")[0] + "\u001B[0m";
        }
    }
}