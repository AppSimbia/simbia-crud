package simbia.app.crud.util;

import simbia.app.crud.infra.dao.exception.errosDeOperacao.NaoHouveAlteracaoNoBancoDeDadosException;
import simbia.app.crud.infra.servlet.exception.operacao.*;
import simbia.app.crud.infra.servlet.exception.validacaoDeDados.PadraoDescricaoErradoException;
import simbia.app.crud.infra.servlet.exception.validacaoDeDados.PadraoEmailErradoException;
import simbia.app.crud.infra.servlet.exception.validacaoDeDados.PadraoNomeErradoException;
import simbia.app.crud.infra.servlet.exception.validacaoDeDados.PadraoSenhaErradoException;
import simbia.app.crud.model.servlet.RequisicaoResposta;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Classe utilitária de validações de dados
 */
public class ValidacoesDeDados {

    // Atributos > Constantes > Expressões Regulares
    private static final String REGEX_EMAIL = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String REGEX_SENHA = "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+=\\[\\]{}|;:'\",.<>?/~`\\\\-]).{8,200}";
    private static final String REGEX_NOME = "^[ a-zA-ZáàâãéèêíïóôõöúçñÁÀÂÃÉÈÊÍÏÓÔÕÖÚÇÑ'\\-\\s]+$";

    // ============================================
    // MÉTODOS DE VALIDAÇÃO INDIVIDUAL
    // ============================================

    /**
     * Valida se o email segue o padrão correto
     *
     * @param email Email a ser validado
     * @throws PadraoEmailErradoException se o padrão estiver incorreto
     */
    public static void validarEmail(String email) throws PadraoEmailErradoException {
        if (email == null || !email.matches(REGEX_EMAIL)) {
            throw new PadraoEmailErradoException();
        }
    }

    public static void validarId(String id) throws NumberFormatException{
        if (id == null){
            throw new NumberFormatException();
        }

        if (id.trim().isEmpty()){
            throw new NumberFormatException();
        }

        long l = Long.parseLong(id);

        if (l < 0) {
            throw new NumberFormatException();
        }
    }

    public static void validarDescricao(String descricao) throws PadraoDescricaoErradoException{
        if (descricao.length() > 200) throw new PadraoDescricaoErradoException();
    }

    /**
     * Valida se a senha segue os requisitos de segurança
     */
    public static void validarSenha(String senha) throws PadraoSenhaErradoException {
        if (senha == null || !senha.matches(REGEX_SENHA)) {
            throw new PadraoSenhaErradoException();
        }
    }

    public static void validarRepeticaoSenha(String senha, String repetirSenha) throws SenhasDiferentesException {
        if (!senha.equals(repetirSenha)) {
            throw new SenhasDiferentesException();
        }
    }

    public static void validarNome(String nome) throws PadraoNomeErradoException {
        if (nome == null || !nome.matches(REGEX_NOME)) {
            throw new PadraoNomeErradoException();
        }
    }

    public static <T> void validarSeExisteRegistroCorrespondenteNoBanco(Optional<T> retornoBanco)
            throws EmailOuSenhaErradosException {
        if (retornoBanco.isEmpty()) {
            throw new EmailOuSenhaErradosException();
        }
    }

    public static void validarSeAdministradorEstaAtutenticado(RequisicaoResposta requisicaoResposta)
            throws UsuarioNaoAutenticadoException {
        if (!requisicaoResposta.existeSessaoDaRequisicao("administradorAutenticado")) {
            throw new UsuarioNaoAutenticadoException();
        }
    }

    public static <T> void validarRegistros(List<T> registros)
            throws RequisicaoSemRegistrosException {
        if (registros == null || registros.isEmpty()) {
            throw new RequisicaoSemRegistrosException();
        }
    }

    public static void validarTipoDeOrdenacao(String tipoOrdenacao)
            throws RequisicaoSemTipoOrdenacaoException {
        if (tipoOrdenacao == null || tipoOrdenacao.trim().isEmpty()) {
            throw new RequisicaoSemTipoOrdenacaoException();
        }

        List<String> tiposValidos = Arrays.asList(
                "porId", "porNome", "porEmail",
                "porNomePermissao", "porDescricao",
                "porValor", "porTitulo",
                "porIdPlano", "porIdVantagem"
        );

        if (!tiposValidos.contains(tipoOrdenacao)) {
            throw new RequisicaoSemTipoOrdenacaoException();
        }
    }

    public static void validarSucessoDeOperacao(boolean sucesso)
            throws NaoHouveAlteracaoNoBancoDeDadosException {
        if (!sucesso) {
            throw new NaoHouveAlteracaoNoBancoDeDadosException();
        }
    }

    // ============================================
    // VALIDAÇÃO EM LOTE PARA POPUPS
    // ============================================

    /**
     * Classe interna para armazenar erros de validação
     */
    public static class ResultadoValidacao {
        private Map<String, String> erros = new HashMap<>();

        public void adicionarErro(String campo, String mensagem) {
            erros.put(campo, mensagem);
        }

        public boolean temErros() {
            return !erros.isEmpty();
        }

        public Map<String, String> getErros() {
            return erros;
        }

        /**
         * Converte erros para JSON que o JavaScript pode consumir
         * Formato: {"campo":"mensagem","campo2":"mensagem2"}
         */
        public String toJSON() {
            if (erros.isEmpty()) return "{}";

            StringBuilder json = new StringBuilder("{");
            int i = 0;
            for (Map.Entry<String, String> entry : erros.entrySet()) {
                if (i > 0) json.append(",");
                json.append("\"").append(entry.getKey()).append("\":\"")
                        .append(entry.getValue().replace("\"", "\\\"")).append("\"");
                i++;
            }
            json.append("}");
            return json.toString();
        }
    }

    /**
     * Valida campos de Administrador para inserção
     */
    public static ResultadoValidacao validarAdministrador(String nome, String email, String senha, String repetirSenha) {
        ResultadoValidacao resultado = new ResultadoValidacao();

        // Valida nome
        if (nome == null || nome.trim().isEmpty()) {
            resultado.adicionarErro("nome", "Nome é obrigatório");
        } else if (!nome.matches(REGEX_NOME)) {
            resultado.adicionarErro("nome", "Nome deve conter apenas letras e espaços");
        } else if (nome.length() < 3 || nome.length() > 100) {
            resultado.adicionarErro("nome", "Nome deve ter entre 3 e 100 caracteres");
        }

        // Valida email
        if (email == null || email.trim().isEmpty()) {
            resultado.adicionarErro("email", "Email é obrigatório");
        } else if (!email.matches(REGEX_EMAIL)) {
            resultado.adicionarErro("email", "Email inválido");
        }

        // Valida senha
        if (senha == null || senha.trim().isEmpty()) {
            resultado.adicionarErro("senha", "Senha é obrigatória");
        } else if (!senha.matches(REGEX_SENHA)) {
            resultado.adicionarErro("senha", "Senha deve ter ao menos 1 caractere minúsculo, maiúsculo, numérico e especial");
        }

        // Valida repetição de senha
        if (repetirSenha == null || repetirSenha.trim().isEmpty()) {
            resultado.adicionarErro("repetir-senha", "Repita a senha");
        } else if (senha != null && !senha.equals(repetirSenha)) {
            resultado.adicionarErro("repetir-senha", "As senhas não correspondem");
        }

        return resultado;
    }

    public static ResultadoValidacao validarAdministradorSemSenha(String nome, String email) {
        ResultadoValidacao resultado = new ResultadoValidacao();

        // Valida nome
        if (nome == null || nome.trim().isEmpty()) {
            resultado.adicionarErro("nome", "Nome é obrigatório");
        } else if (!nome.matches(REGEX_NOME)) {
            resultado.adicionarErro("nome", "Nome deve conter apenas letras e espaços");
        } else if (nome.length() < 3 || nome.length() > 100) {
            resultado.adicionarErro("nome", "Nome deve ter entre 3 e 100 caracteres");
        }

        // Valida email
        if (email == null || email.trim().isEmpty()) {
            resultado.adicionarErro("email", "Email é obrigatório");
        } else if (!email.matches(REGEX_EMAIL)) {
            resultado.adicionarErro("email", "Email inválido");
        }

        return resultado;
    }

    /**
     * Valida campos de Plano para inserção
     */
    public static ResultadoValidacao validarPlano(String nome, String valorStr, String status) {
        ResultadoValidacao resultado = new ResultadoValidacao();

        // Valida nome
        if (nome == null || nome.trim().isEmpty()) {
            resultado.adicionarErro("nome", "Nome do plano é obrigatório");
        } else if (nome.length() < 2 || nome.length() > 50) {
            resultado.adicionarErro("nome", "Nome deve ter entre 2 e 50 caracteres");
        }

        // Valida valor
        if (valorStr == null || valorStr.trim().isEmpty()) {
            resultado.adicionarErro("valor", "Valor é obrigatório");
        } else {
            try {
                BigDecimal valor = new BigDecimal(valorStr);
                if (valor.compareTo(BigDecimal.ZERO) < 0) {
                    resultado.adicionarErro("valor", "Valor deve ser positivo");
                }
            } catch (NumberFormatException e) {
                resultado.adicionarErro("valor", "Valor inválido");
            }
        }

        // Valida status
        if (status == null || status.trim().isEmpty()) {
            resultado.adicionarErro("status", "Status é obrigatório");
        }

        return resultado;
    }

    /**
     * Valida campos genéricos com nome e descrição (Permissão, Vantagem, TipoIndustria, CategoriaProduto)
     */
    public static ResultadoValidacao validarNomeDescricao(String nome, String descricao, String tipoEntidade) {
        ResultadoValidacao resultado = new ResultadoValidacao();

        // Valida nome
        if (nome == null || nome.trim().isEmpty()) {
            resultado.adicionarErro("nome", "Nome é obrigatório");
        } else if (nome.length() < 2 || nome.length() > 100) {
            resultado.adicionarErro("nome", "Nome deve ter entre 2 e 100 caracteres");
        }

        // Valida descrição
        if (descricao == null || descricao.trim().isEmpty()) {
            resultado.adicionarErro("descricao", "Descrição é obrigatória");
        } else if (descricao.length() < 10 || descricao.length() > 500) {
            resultado.adicionarErro("descricao", "Descrição deve ter entre 10 e 500 caracteres");
        }

        return resultado;
    }

    /**
     * Valida VantagemPlano (IDs de FK)
     */
    public static ResultadoValidacao validarVantagemPlano(String idPlanoStr, String idVantagemStr) {
        ResultadoValidacao resultado = new ResultadoValidacao();

        // Valida ID do Plano
        if (idPlanoStr == null || idPlanoStr.trim().isEmpty()) {
            resultado.adicionarErro("id-plano", "ID do plano é obrigatório");
        } else {
            try {
                long idPlano = Long.parseLong(idPlanoStr);
                if (idPlano <= 0) {
                    resultado.adicionarErro("id-plano", "ID do plano deve ser positivo");
                }
            } catch (NumberFormatException e) {
                resultado.adicionarErro("id-plano", "ID do plano inválido");
            }
        }

        // Valida ID da Vantagem
        if (idVantagemStr == null || idVantagemStr.trim().isEmpty()) {
            resultado.adicionarErro("id-vantagem", "ID da vantagem é obrigatório");
        } else {
            try {
                long idVantagem = Long.parseLong(idVantagemStr);
                if (idVantagem <= 0) {
                    resultado.adicionarErro("id-vantagem", "ID da vantagem deve ser positivo");
                }
            } catch (NumberFormatException e) {
                resultado.adicionarErro("id-vantagem", "ID da vantagem inválido");
            }
        }

        return resultado;
    }
}