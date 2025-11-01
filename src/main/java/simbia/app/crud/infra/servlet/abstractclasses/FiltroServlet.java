package simbia.app.crud.infra.servlet.abstractclasses;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import simbia.app.crud.infra.servlet.exception.operacao.RequisicaoSemRegistrosException;
import simbia.app.crud.model.servlet.RequisicaoResposta;
import simbia.app.crud.util.ValidacoesDeDados;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet abstrato para filtrar registros em exibição.
 *
 * Fluxo: recupera registros da sessão → aplica filtro baseado em regex → armazena
 * registros filtrados na requisição com chave "nomeDaTabela + Formatados" → despacha
 * para página de sucesso (ou redireciona para erro caso não existam registros).
 *
 * O filtro é obtido do parâmetro "filtro" da requisição e aplicado de forma case-insensitive.
 * Se o parâmetro estiver vazio ou ausente, todos os registros são retornados.
 *
 * As subclasses devem implementar os métodos abstratos para definir qual tabela filtrar,
 * como aplicar o filtro em cada entidade e para onde navegar após a filtragem.
 */
public abstract class FiltroServlet<T> extends HttpServlet {

    /**
     * Processa a requisição GET: filtra registros e navega para página de resultado.
     * Aplica o filtro aos registros da sessão e despacha para a página de sucesso.
     */
    @Override
    protected void doGet(HttpServletRequest requisicao, HttpServletResponse resposta)
            throws ServletException, IOException {
        RequisicaoResposta requisicaoResposta = new RequisicaoResposta(requisicao, resposta);

        try {
            filtrarRegitrosDaRequisicao(requisicaoResposta);
            requisicaoResposta.despacharPara(enderecoDeDespache());

        } catch (RequisicaoSemRegistrosException causa) {
            requisicaoResposta.redirecionarPara(enderecoDeDespacheCasoErro());
        }
    }

    /**
     * Filtra os registros da sessão com base no parâmetro "filtro" da requisição.
     * Recupera registros da sessão, aplica regex de filtro e salva resultado na requisição.
     */
    private void filtrarRegitrosDaRequisicao(RequisicaoResposta requisicaoResposta)
            throws RequisicaoSemRegistrosException {
        List<T> registros = (List<T>) requisicaoResposta.recuperarAtributoDaSessao(nomeDaTabela() + "Registros");
        List<T> registrosFiltrados = new ArrayList<>();

        ValidacoesDeDados.validarRegistros(registros);

        String regexFiltro = gerarRegexDeFiltroDaRequisicao(requisicaoResposta);

        for (T entidade : registros) {
            if (entidadeCorrepondeAoFiltro(regexFiltro, entidade)) {
                registrosFiltrados.add(entidade);
            }
        }

        requisicaoResposta.adicionarAtributoNaRequisicao(nomeDaTabela() + "Formatados", registrosFiltrados);
    }

    /**
     * Gera a expressão regular de filtro a partir do parâmetro "filtro" da requisição.
     * Se o filtro estiver vazio ou ausente, retorna ".*" (corresponde a qualquer texto).
     * Caso contrário, retorna ".*filtro.*" em lowercase para busca case-insensitive.
     */
    private String gerarRegexDeFiltroDaRequisicao(RequisicaoResposta requisicaoResposta) {
        String filtro = requisicaoResposta.recuperarParametroDaRequisicao("filtro");
        String regexDeFiltro = ".*";

        if ((filtro != null && !filtro.trim().isEmpty())) {
            regexDeFiltro = ".*" + filtro.toLowerCase() + ".*";
        }

        return regexDeFiltro;
    }

    /**
     * Verifica se a entidade corresponde ao filtro especificado pela regex.
     * A implementação deve converter os campos relevantes da entidade para lowercase
     * e verificar se correspondem ao padrão regex fornecido.
     */
    public abstract boolean entidadeCorrepondeAoFiltro(String regexFiltro, T entidade);

    /**
     * Retorna o nome da tabela (ex: "usuario", "produto").
     * Usado para formar as chaves na sessão e na requisição.
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