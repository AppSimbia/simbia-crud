package simbia.app.crud.servlet.inserir;

import jakarta.servlet.ServletException;
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

/**
 * Servlet para inserir novos administradores no sistema.
 */
public class AdministradorInserirServlet extends InserirServlet<Administrador> {

    /**
     * Processa a requisição POST para inserir um administrador.
     * Valida os dados, insere no banco e trata possíveis erros.
     */
    @Override
    protected void doPost(HttpServletRequest requisicao, HttpServletResponse resposta) throws ServletException, IOException {
        RequisicaoResposta requisicaoResposta = new RequisicaoResposta(requisicao, resposta);

        try{
            Administrador registro = recuperarNovoRegistroNaRequisicao(requisicaoResposta);
            inserirRegistroNoBanco(registro);
            requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("status", "Operação concluída com sucesso!");

            requisicaoResposta.redirecionarPara(enderecoDeDespache());

        } catch (PadraoNomeErradoException causa) {
            requisicaoResposta.adicionarAtributoNaRequisicao("status", "erro-nome");
            requisicaoResposta.adicionarAtributoNaRequisicao("mensagem", "Nome deve conter apenas letras e espaços.");
            requisicaoResposta.adicionarAtributoNaRequisicao("popupAdicionarAberto", true);
            requisicaoResposta.adicionarAtributoNaRequisicao("dados", dados(requisicaoResposta));

            requisicaoResposta.despacharPara(enderecoDeDespacheCasoErro());

        } catch (PadraoSenhaErradoException causa) {
            requisicaoResposta.adicionarAtributoNaRequisicao("status", "erro-senha");
            requisicaoResposta.adicionarAtributoNaRequisicao("mensagem", "Senha deve conter ao menos um caractere minúsculo, maiúsculo, numérico e especial.");
            requisicaoResposta.adicionarAtributoNaRequisicao("popupAdicionarAberto", true);
            requisicaoResposta.adicionarAtributoNaRequisicao("dados", dados(requisicaoResposta));

            requisicaoResposta.despacharPara(enderecoDeDespacheCasoErro());

        } catch (PadraoEmailErradoException causa) {
            requisicaoResposta.adicionarAtributoNaRequisicao("status", "erro-email");
            requisicaoResposta.adicionarAtributoNaRequisicao("mensagem", "Insira um email válido.");
            requisicaoResposta.adicionarAtributoNaRequisicao("popupAdicionarAberto", true);
            requisicaoResposta.adicionarAtributoNaRequisicao("dados", dados(requisicaoResposta));

            requisicaoResposta.despacharPara(enderecoDeDespacheCasoErro());

        } catch (SenhasDiferentesException causa) {
            requisicaoResposta.adicionarAtributoNaRequisicao("status", "erro-senha-diferentes");
            requisicaoResposta.adicionarAtributoNaRequisicao("mensagem", "As suas senhas não correspondem.");
            requisicaoResposta.adicionarAtributoNaRequisicao("popupAdicionarAberto", true);
            requisicaoResposta.adicionarAtributoNaRequisicao("dados", dados(requisicaoResposta));

            requisicaoResposta.despacharPara(enderecoDeDespacheCasoErro());

        } catch (NaoHouveAlteracaoNoBancoDeDadosException causa) {
            requisicaoResposta.adicionarAtributoNaRequisicao("status", "falhou");
            requisicaoResposta.adicionarAtributoNaRequisicao("mensagem", "Operação falhou! Tente novamente.");
            requisicaoResposta.adicionarAtributoNaRequisicao("popupAdicionarAberto", false);

            requisicaoResposta.despacharPara(enderecoDeDespacheCasoErro());

        } catch (ViolacaoDeObrigatoriedadeException causa) {
            requisicaoResposta.adicionarAtributoNaRequisicao("status", causa.getCampoViolado() + "-vazio");
            requisicaoResposta.adicionarAtributoNaRequisicao("mensagem", "Campo obrigatório.");
            requisicaoResposta.adicionarAtributoNaRequisicao("popupAdicionarAberto", true);
            requisicaoResposta.adicionarAtributoNaRequisicao("dados", dados(requisicaoResposta));

            requisicaoResposta.despacharPara(enderecoDeDespacheCasoErro());

        } catch (ViolacaoDeUnicidadeException causa) {
            requisicaoResposta.adicionarAtributoNaRequisicao("status", causa.getCampoViolado() + "-repetido");
            requisicaoResposta.adicionarAtributoNaRequisicao("mensagem", "Campo já possui registro com esse valor.");
            requisicaoResposta.adicionarAtributoNaRequisicao("popupAdicionarAberto", true);
            requisicaoResposta.adicionarAtributoNaRequisicao("dados", dados(requisicaoResposta));

            requisicaoResposta.despacharPara(enderecoDeDespacheCasoErro());

        } catch (FalhaDeConexaoDriverInadequadoException | FalhaDeConexaoGeralException |
                 FalhaDeConexaoBancoDeDadosInexistenteException | FalhaDeConexaoQuedaRepentina |
                 FalhaDeConexaoSenhaIncorretaException causa) {
            requisicaoResposta.adicionarAtributoNaRequisicao("status", "erro-conexao");
            requisicaoResposta.adicionarAtributoNaRequisicao("mensagem", "Erro de conexão! Tente novamente.");
            requisicaoResposta.adicionarAtributoNaRequisicao("popupAdicionarAberto", false);

            requisicaoResposta.despacharPara(enderecoDeDespacheCasoErro());
        }
    }

    /**
     * Insere o administrador no banco de dados usando o DAO.
     */
    @Override
    public void inserirRegistroNoBanco(Administrador entidade) throws DaoException, OperacoesException {
        AdministradorDao dao = new AdministradorDao();
        dao.inserir(entidade);
    }

    /**
     * Recupera e valida os dados do formulário de cadastro.
     * Retorna um objeto Administrador pronto para ser inserido.
     */
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

    /**
     * Retorna o endereço para onde redirecionar após inserção bem-sucedida.
     */
    @Override
    public String enderecoDeDespache() {
        return "/crud/administrador/atualizar";
    }

    /**
     * Retorna o endereço para onde despachar em caso de erro.
     */
    @Override
    public String enderecoDeDespacheCasoErro() {
        return "/crud/administrador.jsp";
    }

    /**
     * Recupera os dados do formulário e retorna como string formatada.
     * Formato: "nome;email;senha;repetir-senha"
     */
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