package simbia.app.crud.infra.servlet.abstractclasses;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import simbia.app.crud.infra.dao.abstractclasses.DaoException;
import simbia.app.crud.model.servlet.RequisicaoResposta;

import java.io.IOException;
import java.util.List;

/**
 * Servlet abstrato para carregar registros de uma tabela e armazená-los na sessão.
 *
 * Fluxo: recupera registros do banco → salva na sessão com chave "nomeDaTabela + Registros"
 * → despacha para página de sucesso (ou redireciona para erro caso falhe).
 *
 * As subclasses devem implementar os métodos abstratos para definir qual tabela carregar
 * e para onde navegar após o carregamento.
 */
public abstract class RegistrosServlet<T> extends HttpServlet {

    /**
     * Processa a requisição: carrega registros da tabela, salva na sessão e navega.
     * Responde a qualquer tipo de requisição HTTP (GET, POST, etc).
     */
    @Override
    protected void service(HttpServletRequest requisicao, HttpServletResponse resposta)
            throws ServletException, IOException {

        RequisicaoResposta requisicaoResposta = new RequisicaoResposta(requisicao, resposta);
        String chave = nomeDaTabela() + "Registros";

        try {
            // Carrega registros e salva na sessão
            requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao(chave, recuperarRegistrosDaTabela());

            // Vai para página de sucesso (forward)
            requisicaoResposta.despacharPara(enderecoDeDespache());

        } catch (DaoException causa) {
            causa.printStackTrace();

            // Vai para página de erro (redirect)
            requisicaoResposta.redirecionarPara(enderecoDeDespacheCasoErro());
        }
    }

    /**
     * Retorna todos os registros da tabela vindos do banco de dados.
     * Geralmente chama um método do DAO como "recuperarTudo()".
     */
    public abstract List<T> recuperarRegistrosDaTabela();

    /**
     * Retorna o nome da tabela (ex: "usuario", "produto").
     * Usado para formar a chave na sessão.
     */
    public abstract String nomeDaTabela();

    /**
     * Retorna o caminho da página JSP de sucesso (ex: "/usuarios.jsp").
     */
    public abstract String enderecoDeDespache();

    /**
     * Retorna o caminho da página JSP de erro (ex: "/erro.jsp").
     */
    public abstract String enderecoDeDespacheCasoErro();
}