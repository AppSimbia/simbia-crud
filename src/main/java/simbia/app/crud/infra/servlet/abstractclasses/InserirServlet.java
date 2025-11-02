package simbia.app.crud.infra.servlet.abstractclasses;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import simbia.app.crud.infra.dao.abstractclasses.DaoException;
import simbia.app.crud.model.servlet.RequisicaoResposta;

import java.io.IOException;

public abstract class InserirServlet<T> extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest requisicao, HttpServletResponse resposta) throws ServletException, IOException {
        RequisicaoResposta requisicaoResposta = new RequisicaoResposta(requisicao, resposta);

        try{
            inserirNovoRegistro(requisicaoResposta);
            requisicaoResposta.despacharPara(enderecoDeDespache());

        } catch (DaoException causa){

        } catch (OperacoesException causa){

        } catch (ValidacaoDeDadosException causa){

        }
    }

    private void inserirNovoRegistro(RequisicaoResposta requisicaoResposta)
            throws DaoException, OperacoesException, ValidacaoDeDadosException {
        T novoRegistro = recuperarNovoRegistroNaRequisicao(requisicaoResposta);

        inserirRegistroNoBanco(novoRegistro);
    }

    public abstract void inserirRegistroNoBanco(T entidade);

    public abstract T recuperarNovoRegistroNaRequisicao(RequisicaoResposta requisicaoResposta);

    public abstract String enderecoDeDespache();

    public abstract String enderecoDeDespacheCasoErro();
}