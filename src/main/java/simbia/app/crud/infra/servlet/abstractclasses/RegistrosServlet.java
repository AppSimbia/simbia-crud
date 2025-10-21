package simbia.app.crud.infra.servlet.abstractclasses;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import simbia.app.crud.infra.dao.abstractclasses.DaoException;
import simbia.app.crud.model.servlet.RequisicaoResposta;

import java.io.IOException;
import java.util.List;

public abstract class RegistrosServlet<T> extends HttpServlet {
    @Override
    protected void service(HttpServletRequest requisicao, HttpServletResponse resposta) throws ServletException, IOException {
        RequisicaoResposta requisicaoResposta = new RequisicaoResposta(requisicao, resposta);
        String chave = nomeDaTabela() + "Registros";
        try{
            requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao(chave, recuperarRegistrosDaTabela());
            requisicaoResposta.despacharPara(enderecoDeDespache());
        } catch (DaoException causa) {
            causa.printStackTrace();
            requisicaoResposta.redirecionarPara(enderecoDeDespacheCasoErro());
        }

    }

    public abstract List<T> recuperarRegistrosDaTabela();

    public abstract String nomeDaTabela();

    public abstract String enderecoDeDespache();

    public abstract String enderecoDeDespacheCasoErro();
}
