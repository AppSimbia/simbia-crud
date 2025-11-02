package simbia.app.crud.servlet.editar;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import simbia.app.crud.dao.PlanoDao;
import simbia.app.crud.infra.dao.abstractclasses.DaoException;
import simbia.app.crud.infra.dao.exception.errosDoBancoDeDados.*;
import simbia.app.crud.infra.servlet.abstractclasses.EditarServlet;
import simbia.app.crud.infra.servlet.abstractclasses.OperacoesException;
import simbia.app.crud.model.dao.Plano;
import simbia.app.crud.model.servlet.RequisicaoResposta;
import simbia.app.crud.util.ValidacoesDeDados;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/plano/alterar")
public class PlanoEditarServlet extends EditarServlet<Plano> {

    /**
     * Processa a requisição POST para inserir um plano.
     * Valida os dados, insere no banco e trata possíveis erros.
     */
    /**
     * Processa a requisição POST para inserir um plano.
     * Valida os dados, insere no banco e trata possíveis erros.
     */
    @Override
    protected void doPost(HttpServletRequest requisicao, HttpServletResponse resposta)
            throws ServletException, IOException {
        RequisicaoResposta requisicaoResposta = new RequisicaoResposta(requisicao, resposta);

        try {
            // 1. Recupera dados do formulário
            String nome = requisicaoResposta.recuperarParametroDaRequisicao("nome");
            String valorStr = requisicaoResposta.recuperarParametroDaRequisicao("valor");
            String status = requisicaoResposta.recuperarParametroDaRequisicao("status");

            // 2. VALIDAÇÃO usando ValidacoesDeDados
            ValidacoesDeDados.ResultadoValidacao resultado =
                    ValidacoesDeDados.validarPlano(nome, valorStr, status);

            // 3. Se houver erros de validação, retorna para o popup
            if (resultado.temErros()) {
                String errosJSON = resultado.toJSON();
                requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("erros", errosJSON);
                requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("dados", nome + ";" + valorStr);
                requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("popupAberto", "true");
                requisicaoResposta.redirecionarPara("/plano.jsp");
                return;
            }

            // 4. Se passou nas validações, cria o objeto e insere no banco
            BigDecimal valor = new BigDecimal(valorStr);
            boolean ativo = "ativo".equals(status);
            Plano plano = new Plano(valor, ativo, nome);

            PlanoDao dao = new PlanoDao();
            dao.inserir(plano);

            // 5. Sucesso - redireciona com mensagem de sucesso
            requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("status", true);
            requisicaoResposta.redirecionarPara("/plano/atualizar");

        } catch (ViolacaoDeUnicidadeException causa) {
            // Tratamento específico para nome duplicado
            String errosJSON = "{\"nome\":\"Já existe um plano com este nome\"}";
            requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("erros", errosJSON);
            requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("popupAberto", "true");
            requisicaoResposta.redirecionarPara("/plano.jsp");

        } catch (ViolacaoDeObrigatoriedadeException causa) {
            String errosJSON = "{\"geral\":\"Campo obrigatório não preenchido\"}";
            requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("erros", errosJSON);
            requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("popupAberto", "true");
            requisicaoResposta.redirecionarPara("/plano.jsp");

        } catch (ViolacaoDeTamanhoException causa) {
            String errosJSON = "{\"nome\":\"Nome muito longo para o banco de dados\"}";
            requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("erros", errosJSON);
            requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("popupAberto", "true");
            requisicaoResposta.redirecionarPara("/plano.jsp");

        } catch (FalhaDeConexaoDriverInadequadoException | FalhaDeConexaoGeralException |
                 FalhaDeConexaoBancoDeDadosInexistenteException | FalhaDeConexaoQuedaRepentina |
                 FalhaDeConexaoSenhaIncorretaException causa) {
            // Erros de conexão com banco
            causa.printStackTrace();
            requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("status", false);
            requisicaoResposta.redirecionarPara("/plano.jsp");

        } catch (DaoException causa) {
            // Outros erros do banco
            causa.printStackTrace();
            requisicaoResposta.adicionarAtributoNaSessaoDaRequisicao("status", false);
            requisicaoResposta.redirecionarPara("/plano.jsp");
        }
    }
    /**
     * Insere o plano no banco de dados usando o DAO.
     */
    @Override
    public void editarRegistroNoBanco(Plano entidade) throws DaoException, OperacoesException {
        PlanoDao dao = new PlanoDao();
        dao.atualizar(entidade);
    }

    /**
     * Recupera os dados do formulário de cadastro.
     * Retorna um objeto Plano pronto para ser inserido.
     */
    @Override
    public Plano recuperarRegistroEmEdicaoNaRequisicao(RequisicaoResposta requisicaoResposta)
            throws NumberFormatException {

        String nome = requisicaoResposta.recuperarParametroDaRequisicao("nome");
        String valorStr = requisicaoResposta.recuperarParametroDaRequisicao("valor");
        String statusStr = requisicaoResposta.recuperarParametroDaRequisicao("status");


        BigDecimal valor = null;

        if (valorStr != null && !valorStr.trim().isEmpty()) {
            valor = new BigDecimal(valorStr.trim().replace(",", "."));
        }

        boolean ativo = "ativo".equals(statusStr);

        return new Plano(valor, ativo, nome);
    }

    /**
     * Retorna o endereço para onde redirecionar após inserção bem-sucedida.
     */
    @Override
    public String enderecoDeRedirecionamento() {
        return "/plano/atualizar";
    }

    /**
     * Retorna o endereço para onde despachar em caso de erro.
     */
    @Override
    public String enderecoDeRedirecionamentoCasoErro() {
        return "/plano.jsp";
    }
}
