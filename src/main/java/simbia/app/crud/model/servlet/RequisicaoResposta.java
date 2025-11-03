package simbia.app.crud.model.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Classe utilitária que encapsula objetos {@link HttpServletRequest} e {@link HttpServletResponse},
 * fornecendo abstracoes simplificadas para manipulação de requisições e respostas HTTP.
 */
public class RequisicaoResposta {

//atributos
    private final HttpServletResponse resposta;
    private final HttpServletRequest requisicao;

//construtor
    /**
     * Construtor que inicializa a classe com os objetos de requisição e resposta HTTP.
     * @param requisicao Objeto {@link HttpServletRequest} contendo os dados da requisição HTTP
     * @param resposta Objeto {@link HttpServletResponse} para enviar respostas ao cliente
     */
    public RequisicaoResposta(HttpServletRequest requisicao, HttpServletResponse resposta){
        this.resposta = resposta;
        this.requisicao = requisicao;
    }
    //métodos
    /**
     * Obtém o valor de um parâmetro da requisição HTTP.
     * @param nome Nome do parâmetro a ser recuperado
     * @return Valor do parâmetro como {@code String}, ou {@code null} se o parâmetro não existir
     */
    public String recuperarParametroDaRequisicao(String nome) {
        return requisicao.getParameter(nome);
    }

    /**
     * Obtém o valor de um atributo da sessão HTTP.
     * @param nome Nome do atributo a ser recuperado
     * @return Valor do atributo como {@code Object}, ou {@code null} se o atributo não existir
     */
    public Object recuperarAtributoDaSessao(String nome) {
        return requisicao.getSession().getAttribute(nome);
    }

    /**
     * Obtém o valor de um atributo da requisição HTTP.
     * @param nome Nome do atributo a ser recuperado
     * @return Valor do atributo como {@code Object}, ou {@code null} se o atributo não existir
     */
    public Object recuperarAtributoDaRequisicao(String nome) {
        return requisicao.getAttribute(nome);
    }


    /**
     * Retorna o objeto {@link HttpServletResponse} encapsulado.
     * @return Objeto de resposta HTTP original
     */
    public HttpServletResponse getResposta() {
        return resposta;
    }

    /**
     * Retorna o objeto {@link HttpServletRequest} encapsulado.
     * @return Objeto de requisição HTTP original
     */
    public HttpServletRequest getRequisicao() {
        return requisicao;
    }

    /**
     * Adiciona um atributo ao escopo da requisição HTTP.
     * O atributo ficará disponível apenas durante a requisição atual.
     * @param chave Nome do atributo (identificador)
     * @param obj Objeto a ser armazenado como atributo
     */
    public void adicionarAtributoNaRequisicao(String chave, Object obj){
        requisicao.setAttribute(chave, obj);
    }

    /**
     * Atualiza um atributo no escopo da requisição HTTP.
     * @param chave Nome do atributo a ser atualizado
     * @param obj Novo valor do objeto
     */
    public void atualizarAtributoNaRequisicao(String chave, Object obj){
        requisicao.setAttribute(chave, obj);
    }

    /**
     * Adiciona um atributo ao escopo da sessão HTTP.
     * O atributo ficará disponível durante toda a sessão do usuário.
     * @param chave Nome do atributo (identificador)
     * @param obj Objeto a ser armazenado na sessão
     */
    public void adicionarAtributoNaSessaoDaRequisicao(String chave, Object obj){
        requisicao.getSession().setAttribute(chave, obj);
    }

    /**
     * Despacha a requisição para outro recurso (JSP, Servlet, HTML) mantendo a URL original.
     * @param endereco Caminho relativo do recurso de destino
     * @throws ServletException Se ocorrer um erro durante o despacho da requisição
     * @throws IOException Se ocorrer um erro de entrada/saída durante o forward
     */
    public void despacharPara(String endereco) throws ServletException, IOException {
        RequestDispatcher dispachador = requisicao.getRequestDispatcher(endereco);
        dispachador.forward(requisicao, resposta);
    }

    /**
     * Redireciona o cliente para uma nova URL. O context path da aplicação é automaticamente adicionado.
     * @param url URL relativa de destino, sem o context path
     * @throws IOException Se ocorrer um erro durante o redirecionamento
     */
    public void redirecionarPara(String url) throws IOException {
        resposta.sendRedirect(requisicao.getContextPath() + url);
    }

    /**
     * Remove um atributo do escopo da requisição HTTP.
     * @param chave Nome do atributo a ser removido
     */
    public void removerAtributoNaRequisicao(String chave){
        requisicao.removeAttribute(chave);
    }

    /**
     * Remove um atributo do escopo da sessão HTTP.
     * @param chave Nome do atributo a ser removido
     */
    public void removerAtributoNaSessao(String chave){
        requisicao.getSession().removeAttribute(chave);
    }

    /**
     * Verifica se existe um atributo especifico na requisicão.
     *
     * @param nome Nome do atributo a ser verificado
     * @return {@code true} caso exista, {@code false} caso o contrario
     */
    public boolean existeAtributoNaRequisicao(String nome){
        return requisicao.getAttribute(nome) != null;
    }

    /**
     * Verifica se existe um atributo especifico na sessão da requisição.
     *
     * @param nome Nome do atributo a ser verificado
     * @return {@code true} caso exista, {@code false} caso o contrário
     */
    public boolean existeSessaoDaRequisicao(String nome){
        return requisicao.getSession().getAttribute(nome) != null;
    }
}