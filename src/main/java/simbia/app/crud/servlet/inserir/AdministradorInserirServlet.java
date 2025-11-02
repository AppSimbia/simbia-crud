package simbia.app.crud.servlet.inserir;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import simbia.app.crud.dao.AdministradorDao;
import simbia.app.crud.infra.dao.abstractclasses.DaoException;
import simbia.app.crud.infra.dao.exception.errosDeOperacao.NaoHouveAlteracaoNoBancoDeDadosException;
import simbia.app.crud.infra.dao.exception.errosDoBancoDeDados.*;
import simbia.app.crud.infra.servlet.abstractclasses.InserirServlet;
import simbia.app.crud.infra.servlet.abstractclasses.OperacoesException;
import simbia.app.crud.infra.servlet.exception.operacao.SenhasDiferentesException;
import simbia.app.crud.infra.servlet.exception.validacaoDeDados.PadraoEmailErradoException;
import simbia.app.crud.infra.servlet.exception.validacaoDeDados.PadraoNomeErradoException;
import simbia.app.crud.infra.servlet.exception.validacaoDeDados.PadraoSenhaErradoException;
import simbia.app.crud.model.dao.Administrador;
import simbia.app.crud.model.servlet.RequisicaoResposta;
import simbia.app.crud.util.ValidacoesDeDados;

import java.io.IOException;

@WebServlet("/administrador/inserir")
public class AdministradorInserirServlet extends InserirServlet<Administrador> {

    @Override
    protected void doPost(HttpServletRequest requisicao, HttpServletResponse resposta) throws ServletException, IOException {
        RequisicaoResposta requisicaoResposta = new RequisicaoResposta(requisicao, resposta);

        try{
            Administrador registro = recuperarNovoRegistroNaRequisicao(requisicaoResposta);
            inserirRegistroNoBanco(registro);

            requisicaoResposta.redirecionarPara(enderecoDeRedirecionamento());

        } catch (PadraoNomeErradoException causa) {
            requisicaoResposta.adicionarAtributoNaRequisicao("mensagem", "Nome deve conter apenas letras e espaços.");
            requisicaoResposta.adicionarAtributoNaRequisicao("popupAdicionarAberto", true);
            requisicaoResposta.adicionarAtributoNaRequisicao("dados", dados(requisicaoResposta));

            requisicaoResposta.redirecionarPara(enderecoDeRedirecionamentoCasoErro());

        } catch (PadraoSenhaErradoException causa) {
            requisicaoResposta.adicionarAtributoNaRequisicao("mensagem", "Senha deve conter ao menos um caractere minúsculo, maiúsculo, numérico e especial.");
            requisicaoResposta.adicionarAtributoNaRequisicao("popupAdicionarAberto", true);
            requisicaoResposta.adicionarAtributoNaRequisicao("dados", dados(requisicaoResposta));

            requisicaoResposta.redirecionarPara(enderecoDeRedirecionamentoCasoErro());

        } catch (PadraoEmailErradoException causa) {
            requisicaoResposta.adicionarAtributoNaRequisicao("mensagem", "Insira um email válido.");
            requisicaoResposta.adicionarAtributoNaRequisicao("popupAdicionarAberto", true);
            requisicaoResposta.adicionarAtributoNaRequisicao("dados", dados(requisicaoResposta));

            requisicaoResposta.redirecionarPara(enderecoDeRedirecionamentoCasoErro());

        } catch (SenhasDiferentesException causa) {
            requisicaoResposta.adicionarAtributoNaRequisicao("mensagem", "As suas senhas não correspondem.");
            requisicaoResposta.adicionarAtributoNaRequisicao("popupAdicionarAberto", true);
            requisicaoResposta.adicionarAtributoNaRequisicao("dados", dados(requisicaoResposta));

            requisicaoResposta.redirecionarPara(enderecoDeRedirecionamentoCasoErro());

        } catch (NaoHouveAlteracaoNoBancoDeDadosException causa) {
            requisicaoResposta.adicionarAtributoNaRequisicao("mensagem", "Operação falhou! Tente novamente.");
            requisicaoResposta.adicionarAtributoNaRequisicao("popupAdicionarAberto", false);

            requisicaoResposta.redirecionarPara(enderecoDeRedirecionamentoCasoErro());

        } catch (ViolacaoDeObrigatoriedadeException causa) {
            requisicaoResposta.adicionarAtributoNaRequisicao("mensagem", "Campo obrigatório.");
            requisicaoResposta.adicionarAtributoNaRequisicao("popupAdicionarAberto", true);
            requisicaoResposta.adicionarAtributoNaRequisicao("dados", dados(requisicaoResposta));

            requisicaoResposta.redirecionarPara(enderecoDeRedirecionamentoCasoErro());

        } catch (ViolacaoDeUnicidadeException causa) {
            requisicaoResposta.adicionarAtributoNaRequisicao("mensagem", "Campo já possui registro com esse valor.");
            requisicaoResposta.adicionarAtributoNaRequisicao("popupAdicionarAberto", true);
            requisicaoResposta.adicionarAtributoNaRequisicao("dados", dados(requisicaoResposta));

            requisicaoResposta.redirecionarPara(enderecoDeRedirecionamentoCasoErro());

        } catch (FalhaDeConexaoDriverInadequadoException | FalhaDeConexaoGeralException |
                 FalhaDeConexaoBancoDeDadosInexistenteException | FalhaDeConexaoQuedaRepentina |
                 FalhaDeConexaoSenhaIncorretaException causa) {
            requisicaoResposta.adicionarAtributoNaRequisicao("mensagem", "Erro de conexão! Tente novamente.");
            requisicaoResposta.adicionarAtributoNaRequisicao("popupAdicionarAberto", false);

            requisicaoResposta.redirecionarPara(enderecoDeRedirecionamentoCasoErro());
        }
    }

    @Override
    public void inserirRegistroNoBanco(Administrador entidade) throws DaoException, OperacoesException {
        AdministradorDao dao = new AdministradorDao();
        dao.inserir(entidade);
    }

    @Override
    public Administrador recuperarNovoRegistroNaRequisicao(RequisicaoResposta requisicaoResposta)
            throws PadraoNomeErradoException, PadraoEmailErradoException,
            PadraoSenhaErradoException, SenhasDiferentesException {
        String nome = requisicaoResposta.recuperarParametroDaRequisicao("nome");
        String email = requisicaoResposta.recuperarParametroDaRequisicao("email");
        String senha = requisicaoResposta.recuperarParametroDaRequisicao("senha");
        String repetirSenha = requisicaoResposta.recuperarParametroDaRequisicao("repetir-senha");

        ValidacoesDeDados.validarNome(nome);
        ValidacoesDeDados.validarEmail(email);
        ValidacoesDeDados.validarSenha(senha);
        ValidacoesDeDados.validarRepeticaoSenha(senha, repetirSenha);

        return new Administrador(email, senha, nome);
    }

    @Override
    public String enderecoDeRedirecionamento() {
        return "/administrador/atualizar";
    }

    @Override
    public String enderecoDeRedirecionamentoCasoErro() {
        return "/administrador.jsp";
    }

    private static String dados(RequisicaoResposta requisicaoResposta){
        String nome = requisicaoResposta.recuperarParametroDaRequisicao("nome");
        String email = requisicaoResposta.recuperarParametroDaRequisicao("email");
        String senha = requisicaoResposta.recuperarParametroDaRequisicao("senha");
        String repetirSenha = requisicaoResposta.recuperarParametroDaRequisicao("repetir-senha");

        StringBuilder dados = new StringBuilder();

        dados.append(nome != null ? nome : "null").append(";");
        dados.append(email != null ? email : "null").append(";");
        dados.append(senha != null ? senha : "null").append(";");
        dados.append(repetirSenha != null ? repetirSenha : "");

        return dados.toString();
    }
}