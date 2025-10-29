package simbia.app.crud.infra.servlet.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Exceção lançada quando a senha fornecida não está no padrão de formatação esperado.
 *
 * Esta exceção é lançada durante a validação de entrada quando a senha não corresponde
 * aos requisitos de formatação (ex: tamanho mínimo, caracteres especiais, etc).
 *
 * A mensagem de erro é gerada automaticamente com timestamp no formato
 * "[dd/MM/yyyy HH:mm:ss] - Erro de credenciais, senha fora do padrao de formatacao."
 */
public class PadraoSenhaErradoException extends RuntimeException {

    private String mensagem;

    /**
     * Constrói uma nova exceção de padrão de senha incorreto.
     * A mensagem é gerada automaticamente com o timestamp atual.
     */
    public PadraoSenhaErradoException() {
        this.mensagem = Util.gerarMensagem();
    }

    /**
     * Retorna a representação em string desta exceção.
     * Formato: "PadraoSenhaIncorretoException: [timestamp] - Erro de credenciais, senha fora do padrao de formatacao."
     */
    public String toString() {
        return "PadraoSenhaIncorretoException: " + mensagem;
    }

    /**
     * Retorna a mensagem de erro com timestamp.
     */
    public String getMensagem() {
        return mensagem;
    }

    /**
     * Classe utilitária interna para geração de mensagens de erro.
     */
    private static class Util {
        /**
         * Gera a mensagem de erro formatada com timestamp atual.
         * Formato: "[dd/MM/yyyy HH:mm:ss] - Erro de credenciais, senha fora do padrao de formatacao."
         */
        private static String gerarMensagem() {
            String dataHora = "[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "]";
            return dataHora + " - Erro de credenciais, senha fora do padrao de formatacao.";
        }
    }
}