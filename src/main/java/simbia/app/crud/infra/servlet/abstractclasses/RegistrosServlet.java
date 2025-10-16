package simbia.app.crud.infra.servlet.abstractclasses;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import simbia.app.crud.infra.dao.exception.ConexaoException;
import simbia.app.crud.infra.dao.exception.DaoException;
import simbia.app.crud.infra.servlet.exception.ErrosDeDevolucaoParaClient;
import simbia.app.crud.model.servlet.RequisicaoResposta;

import java.io.IOException;
import java.util.List;

public abstract class RegistrosServlet<T> extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest requisicao, HttpServletResponse resposta) throws ServletException, IOException {
        RequisicaoResposta requisicaoResposta = new RequisicaoResposta(requisicao, resposta);
        try {
            String chave = nomeDaTabela();
            requisicaoResposta.adicionarAtributoNaRequisicao(chave, recuperarRegistrosDaTabela());

        } catch (DaoException | ConexaoException causa){
            requisicaoResposta.adicionarAtributoNaRequisicao("erro", ErrosDeDevolucaoParaClient.ERRO_DE_COMUNICACAO_COM_O_BANCO_DE_DADOS);
            requisicaoResposta.despacharPara(enderecoDeDespacheCasoErro());
        }
    }

    public abstract List<T> recuperarRegistrosDaTabela();

    public abstract String nomeDaTabela();

    public abstract String enderecoDeDespacheCasoErro();
}
