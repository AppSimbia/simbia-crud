package simbia.app.crud.infra.servlet.abstractclasses;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import simbia.app.crud.model.servlet.RequisicaoResposta;

import java.io.IOException;

public abstract class AtualizarServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest requisicao, HttpServletResponse resposta) throws ServletException, IOException {
        RequisicaoResposta requisicaoResposta = new RequisicaoResposta(requisicao, resposta);

        requisicaoResposta.removerAtributoNaRequisicao("erro");
        requisicaoResposta.removerAtributoNaRequisicao(bancoDeDados() + "Formatados");
        requisicaoResposta.removerAtributoNaSessao(bancoDeDados() + "Registros");

        requisicaoResposta.redirecionarPara(enderecoDeRedirecionamento());
    }

    public abstract String bancoDeDados();

    public abstract String enderecoDeRedirecionamento();
}
