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
            requisicaoResposta.despacharPara(enderecoDeRedirecionamento());

        } catch (DaoException causa){
            //impementação que deve sobescrever e tratar excessões

        } catch (OperacoesException causa){
            //impementação que deve sobescrever e tratar excessões

        } catch (ValidacaoDeDadosException causa){
            //impementação que deve sobescrever e tratar excessões

        }
    }

    private void inserirNovoRegistro(RequisicaoResposta requisicaoResposta)
            throws DaoException, OperacoesException, ValidacaoDeDadosException {
        T novoRegistro = recuperarNovoRegistroNaRequisicao(requisicaoResposta);

        inserirRegistroNoBanco(novoRegistro);
    }

    public abstract void inserirRegistroNoBanco(T entidade);

    public abstract T recuperarNovoRegistroNaRequisicao(RequisicaoResposta requisicaoResposta);

    public abstract String enderecoDeRedirecionamento();

    public abstract String enderecoDeRedirecionamentoCasoErro();
}