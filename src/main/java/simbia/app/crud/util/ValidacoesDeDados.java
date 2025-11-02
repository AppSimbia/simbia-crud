package simbia.app.crud.util;

import simbia.app.crud.infra.dao.exception.errosDeOperacao.NaoHouveAlteracaoNoBancoDeDadosException;
import simbia.app.crud.infra.servlet.exception.operacao.EmailOuSenhaErradosException;
import simbia.app.crud.infra.servlet.exception.operacao.RequisicaoSemRegistrosException;
import simbia.app.crud.infra.servlet.exception.operacao.RequisicaoSemTipoOrdenacaoException;
import simbia.app.crud.infra.servlet.exception.operacao.UsuarioNaoAutenticadoException;
import simbia.app.crud.infra.servlet.exception.validacaoDeDados.PadraoEmailErradoException;
import simbia.app.crud.infra.servlet.exception.validacaoDeDados.PadraoSenhaErradoException;
import simbia.app.crud.model.servlet.RequisicaoResposta;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Arrays;

/**
 * Classe utilitária de validações de dados
 */
public class ValidacoesDeDados {

    // Atributos > Constantes > Expressões Regulares
    private static final String REGEX_EMAIL = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String REGEX_SENHA = "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+=\\[\\]{}|;:'\",.<>?/~`\\\\-]).{8,200}";

    // Métodos de Validação

    /**
     * Valida se o email segue o padrão correto
     * @param email Email a ser validado
     * @throws PadraoEmailErradoException se o padrão estiver incorreto
     */
    public static void validarEmail(String email) throws PadraoEmailErradoException {
        if (email == null || !email.matches(REGEX_EMAIL)) {
            throw new PadraoEmailErradoException();
        }
    }

    /**
     * Valida se a senha segue os requisitos de segurança
     * Requisitos: 8-200 caracteres, maiúscula, minúscula, número e caractere especial
     * @param senha Senha a ser validada
     * @throws PadraoSenhaErradoException se não atender aos requisitos
     */
    public static void validarSenha(String senha) throws PadraoSenhaErradoException {
        if (senha == null || !senha.matches(REGEX_SENHA)) {
            throw new PadraoSenhaErradoException();
        }
    }

    /**
     * Valida se existe um registro correspondente no banco
     * @param retornoBanco Optional com o resultado da consulta
     * @throws EmailOuSenhaErradosException se não encontrar registro
     */
    public static <T> void validarSeExisteRegistroCorrespondenteNoBanco(Optional<T> retornoBanco)
            throws EmailOuSenhaErradosException {
        if (retornoBanco.isEmpty()) {
            throw new EmailOuSenhaErradosException();
        }
    }

    /**
     * Valida se o administrador está autenticado na sessão
     * @param requisicaoResposta Objeto com dados da requisição
     * @throws UsuarioNaoAutenticadoException se não estiver autenticado
     */
    public static void validarSeAdministradorEstaAtutenticado(RequisicaoResposta requisicaoResposta)
            throws UsuarioNaoAutenticadoException {
        if (!requisicaoResposta.existeSessaoDaRequisicao("administradorAutenticado")) {
            throw new UsuarioNaoAutenticadoException();
        }
    }

    /**
     * Valida se a lista de registros não está vazia
     * @param registros Lista a ser validada
     * @throws RequisicaoSemRegistrosException se lista for nula ou vazia
     */
    public static <T> void validarRegistros(List<T> registros)
            throws RequisicaoSemRegistrosException {
        if (registros == null || registros.isEmpty()) {
            throw new RequisicaoSemRegistrosException();
        }
    }

    /**
     * Valida se o tipo de ordenação é válido
     * @param tipoOrdenacao String com o tipo (ex: "porId", "porNome")
     * @throws RequisicaoSemTipoOrdenacaoException se inválido ou nulo
     */
    public static void validarTipoDeOrdenacao(String tipoOrdenacao)
            throws RequisicaoSemTipoOrdenacaoException {

        // Verifica se foi fornecido
        if (tipoOrdenacao == null || tipoOrdenacao.trim().isEmpty()) {
            throw new RequisicaoSemTipoOrdenacaoException();
        }

        // Lista de tipos válidos para todas as entidades
        List<String> tiposValidos = Arrays.asList(
                // Administrador
                "porId", "porNome", "porEmail",
                // Permissão
                "porNomePermissao", "porDescricao",
                // Plano/Vantagem
                "porValor", "porTitulo",
                // VantagemPlano
                "porIdPlano", "porIdVantagem"
        );

        if (!tiposValidos.contains(tipoOrdenacao)) {
            throw new RequisicaoSemTipoOrdenacaoException();
        }
    }
    /**
     * Valida se uma operação no banco foi bem-sucedida
     * @param sucesso resultado da operação
     * @throws NaoHouveAlteracaoNoBancoDeDadosException se falhou
     */
    public static void validarSucessoDeOperacao(boolean sucesso)
            throws NaoHouveAlteracaoNoBancoDeDadosException {
        if (!sucesso) {
            throw new NaoHouveAlteracaoNoBancoDeDadosException();
        }
    }

    /**
     * Retorna data/hora atual formatada para logs
     * @return String no formato "dd/MM/yyyy HH:mm:ss"
     */
    private static String obterDataHoraAtual() {
        LocalDateTime agora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return agora.format(formatter);
    }
}