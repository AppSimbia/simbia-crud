package simbia.app.crud.infra.servlet.abstractclasses;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import simbia.app.crud.model.servlet.RequisicaoResposta;

import java.io.IOException;

/**
 * Servlet abstrato para atualizar (limpar cache de) registros em exibição.
 *
 * Fluxo: remove atributos de erro e cache da sessão → redireciona para endpoint
 * que recarrega os dados atualizados.
 *
 * Este servlet é útil após operações de CREATE, UPDATE ou DELETE, quando é necessário
 * forçar o recarregamento dos dados para refletir as mudanças no banco de dados.
 *
 * As subclasses devem implementar os métodos abstratos para definir qual tabela
 * atualizar e para onde redirecionar após a limpeza.
 */
public abstract class AtualizarServlet extends HttpServlet {

    /**
     * Processa a requisição: limpa cache e atributos de erro, depois redireciona.
     * Responde a qualquer tipo de requisição HTTP (GET, POST, etc).
     */
    @Override
    protected void service(HttpServletRequest requisicao, HttpServletResponse resposta)
            throws ServletException, IOException {
        RequisicaoResposta requisicaoResposta = new RequisicaoResposta(requisicao, resposta);

        requisicaoResposta.removerAtributoNaRequisicao("erro");
        requisicaoResposta.removerAtributoNaRequisicao(bancoDeDados() + "Formatados");
        requisicaoResposta.removerAtributoNaSessao(bancoDeDados() + "Registros");

        requisicaoResposta.redirecionarPara(enderecoDeRedirecionamento());
    }

    /**
     * Retorna o nome da tabela/banco de dados (ex: "usuario", "produto").
     * Usado para formar as chaves dos atributos a serem removidos.
     */
    public abstract String bancoDeDados();

    /**
     * Retorna o caminho do endpoint para redirecionar após limpar o cache.
     * Geralmente aponta para um servlet que recarrega os dados (ex: "/carregarUsuarios").
     */
    public abstract String enderecoDeRedirecionamento();
}