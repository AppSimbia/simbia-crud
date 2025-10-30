package simbia.app.crud.infra.servlet.abstractclasses;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import simbia.app.crud.model.servlet.RequisicaoResposta;

import java.io.IOException;

public abstract class PopUpAdicionarServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest requisicao, HttpServletResponse resposta) throws ServletException, IOException {
        RequisicaoResposta requisicaoResposta = new RequisicaoResposta(requisicao, resposta);

        atualizarEstadoDoPopUpAdicionar(requisicaoResposta);
        requisicaoResposta.despacharPara(enderecoDeDespache());
    }

    private void atualizarEstadoDoPopUpAdicionar(RequisicaoResposta requisicaoResposta){
        if (popupEstaAberto(requisicaoResposta)){
            fecharPopup(requisicaoResposta);
        }else{
            abrirPopup(requisicaoResposta);
        }
    }

    private void abrirPopup(RequisicaoResposta requisicaoResposta){
        requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao(nomeDaTabela() + "Popup", "adicionar");
    }

    private void fecharPopup(RequisicaoResposta requisicaoResposta){
        requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao(nomeDaTabela() + "Popup", null);
    }

    private boolean popupEstaAberto(RequisicaoResposta requisicaoResposta){
        if (requisicaoResposta.existeSessaoDaRequisicao(nomeDaTabela() + "Popup")){
            if (requisicaoResposta.recuperarAtributoDaSessao(nomeDaTabela() + "Popup").equals("adicionar")){
                return true;
            }
        }
        return false;
    }
    public abstract String nomeDaTabela();

    public abstract String enderecoDeDespache();
}
