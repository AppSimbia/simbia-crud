package simbia.app.crud.servlet.editar;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import simbia.app.crud.dao.AdministradorDao;
import simbia.app.crud.infra.dao.abstractclasses.DaoException;
import simbia.app.crud.infra.dao.exception.errosDoBancoDeDados.ViolacaoDeUnicidadeException;
import simbia.app.crud.infra.servlet.abstractclasses.EditarServlet;
import simbia.app.crud.infra.servlet.abstractclasses.OperacoesException;
import simbia.app.crud.infra.servlet.exception.operacao.SenhasDiferentesException;
import simbia.app.crud.infra.servlet.exception.validacaoDeDados.PadraoEmailErradoException;
import simbia.app.crud.infra.servlet.exception.validacaoDeDados.PadraoNomeErradoException;
import simbia.app.crud.infra.servlet.exception.validacaoDeDados.PadraoSenhaErradoException;
import simbia.app.crud.model.dao.Administrador;
import simbia.app.crud.model.servlet.RequisicaoResposta;
import simbia.app.crud.util.ValidacoesDeDados;

import java.io.IOException;

@WebServlet("/administrador/alterar")
public class AdministradorEditarServlet extends EditarServlet<Administrador> {

    /**
     * Processa a requisição POST para inserir um administrador.
     * Valida os dados, insere no banco e trata possíveis erros.
     */
    @Override
    protected void doPost(HttpServletRequest requisicao, HttpServletResponse resposta)
            throws ServletException, IOException {
        RequisicaoResposta requisicaoResposta = new RequisicaoResposta(requisicao, resposta);

        try {
            // Recupera dados do formulário
            String nome = requisicaoResposta.recuperarParametroDaRequisicao("nome");
            String email = requisicaoResposta.recuperarParametroDaRequisicao("email");
            String senha = requisicaoResposta.recuperarParametroDaRequisicao("senha");
            String repetirSenha = requisicaoResposta.recuperarParametroDaRequisicao("repetir-senha");

            // VALIDAÇÃO VERIFICA SE REGISTRO TERA SENHA EDITADA OU NAO
            if (!(senha == null) && !(repetirSenha == null)){
                if (!senha.trim().isEmpty() && !repetirSenha.trim().isEmpty()){
                    ValidacoesDeDados.ResultadoValidacao resultado =
                            ValidacoesDeDados.validarAdministrador(nome, email, senha, repetirSenha);

                    // Se houver erros, retorna para o popup
                    if (resultado.temErros()) {
                        String errosJSON = resultado.toJSON();
                        requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("erros", errosJSON);
                        requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("dados",
                                nome + ";" + email + ";" + senha + ";" + repetirSenha);
                        requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("popupAberto", "true");
                        requisicaoResposta.redirecionarPara("/administrador.jsp");
                        return;
                    }
                }
            }else{
                ValidacoesDeDados.ResultadoValidacao resultado = ValidacoesDeDados.validarAdministradorSemSenha(nome, email);

                // Se houver erros, retorna para o popup
                if (resultado.temErros()) {
                    String errosJSON = resultado.toJSON();
                    requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("erros", errosJSON);
                    requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("dados",
                            nome + ";" + email + ";" + senha + ";" + repetirSenha);
                    requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("popupAberto", "true");
                    requisicaoResposta.redirecionarPara("/administrador.jsp");
                    return;
                }
            }

            // Se passou nas validações, insere no banco
            editarRegistroNoBanco(recuperarRegistroEmEdicaoNaRequisicao(requisicaoResposta));

            requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("status", true);
            requisicaoResposta.redirecionarPara("/administrador/atualizar");

        } catch (ViolacaoDeUnicidadeException causa) {
            // Trata erro de email duplicado
            String errosJSON = "{\"email\":\"Este email já está cadastrado\"}";
            requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("erros", errosJSON);
            requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("popupAberto", "true");
            requisicaoResposta.redirecionarPara("/administrador.jsp");

        } catch (DaoException causa) {
            causa.printStackTrace();
            requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("status", false);
            requisicaoResposta.redirecionarPara("/administrador.jsp");
        }
    }
    /**
     * edita o administrador no banco de dados usando o DAO.
     */
    @Override
    public void editarRegistroNoBanco(Administrador entidade) throws DaoException, OperacoesException {
        AdministradorDao dao = new AdministradorDao();
        if (entidade.getSenha() == null){
            dao.atualizarSemSenha(entidade);
        }else{
            dao.atualizarSerializandoSenha(entidade);
        }
    }

    /**
     * Recupera e valida os dados do formulário de cadastro.
     * Retorna um objeto Administrador pronto para ser editado.
     */
    @Override
    public Administrador recuperarRegistroEmEdicaoNaRequisicao(RequisicaoResposta requisicaoResposta)
            throws PadraoNomeErradoException, PadraoEmailErradoException,
            PadraoSenhaErradoException, SenhasDiferentesException {
        String nome = requisicaoResposta.recuperarParametroDaRequisicao("nome");
        String email = requisicaoResposta.recuperarParametroDaRequisicao("email");
        String senha = requisicaoResposta.recuperarParametroDaRequisicao("senha");
        String repetirSenha = requisicaoResposta.recuperarParametroDaRequisicao("repetir-senha");
        String idStr = requisicaoResposta.recuperarParametroDaRequisicao("id");

        System.out.println(idStr);

        long id = Long.parseLong(idStr);


        if (!(senha == null) && !(repetirSenha == null)){
            if (!senha.trim().isEmpty() && !repetirSenha.trim().isEmpty()){
                return new Administrador(id, email, senha, nome);
            }
        }
        return new Administrador(email, nome, id);
    }

    /**
     * Retorna o endereço para onde redirecionar após inserção bem-sucedida.
     */
    @Override
    public String enderecoDeRedirecionamento() {
        return "/administrador/atualizar";
    }

    /**
     * Retorna o endereço para onde despachar em caso de erro.
     */
    @Override
    public String enderecoDeRedirecionamentoCasoErro() {
        return "/administrador.jsp";
    }
}
