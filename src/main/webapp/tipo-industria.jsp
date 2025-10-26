<%@ page import="simbia.app.crud.util.UtilitariosJSP" %>
<%@ page import="simbia.app.crud.util.ValidacoesDeDados" %>
<%@ page import="simbia.app.crud.model.servlet.RequisicaoResposta" %>
<%@ page import="java.util.List" %>
<%@ page import="simbia.app.crud.infra.servlet.exception.UsuarioNaoAutenticadoException" %>
<%@ page import="simbia.app.crud.infra.servlet.exception.RequisicaoSemRegistrosException" %>
<%@ page import="simbia.app.crud.model.dao.TipoIndustria" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    RequisicaoResposta requisicaoResposta = new RequisicaoResposta(request, response);

    try{
        ValidacoesDeDados.validarSeAdministradorEstaAtutenticado(requisicaoResposta);

        List<TipoIndustria> registros = UtilitariosJSP.recuperarRegistrosDaRequisicao(requisicaoResposta, "tipoindustria");

%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.0/css/all.min.css"
          integrity="sha512-DxV+EoADOkOygM4IR9yXP8Sb2qwgidEmeqAEmDKIOfPRQZOWbXCzLC6vjbZyy0vPisbH2SyW27+ddLVCN+OMzQ=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/tabelas/style.css">
    <title>Simbia - Tipo Indústria</title>
</head>
<body>
<div id="popup-container">

</div>
<!-- MENU LATERAL -->
<img src="${pageContext.request.contextPath}/assets/elements/icon-simbia.svg" alt="logo-simbia">
<aside>
    <h1>Tabelas</h1>

    <hr>
    <nav>
        <ul>
            <a href="${pageContext.request.contextPath}/administrador.jsp">
                <li>
                    <i class="fa-solid fa-user-shield"></i>
                    <p>Administrador</p>
                </li>
            </a>
            <a href="${pageContext.request.contextPath}/permissao.jsp">
                <li>
                    <i class="fa-solid fa-key"></i>
                    <p>Permissão</p>
                </li>
            </a>
            <a href="${pageContext.request.contextPath}/vantagem.jsp">
                <li>
                    <i class="fa-solid fa-hand-sparkles"></i>
                    <p>Vantagem</p>
                </li>
            </a>
            <a href="${pageContext.request.contextPath}/plano.jsp">
                <li>
                    <i class="fa-solid fa-dollar-sign"></i>
                    <p>Plano</p>
                </li>
            </a>
            <a href="${pageContext.request.contextPath}/vantagem-plano.jsp">
                <li>
                    <i class="fa-solid fa-square-check"></i>
                    <p>Vantagem plano</p>
                </li>
            </a>
            <li class="ativo">
                <i class="fa-solid fa-layer-group"></i>
                <p>Tipo Indústria</p>
            </li>
            <a href="${pageContext.request.contextPath}/categoria-produto.jsp">
                <li>
                    <i class="fa-solid fa-box-archive"></i>
                    <p>Categoria Produto</p>
                </li>
            </a>
            <li>
                <i class="fa-solid fa-store"></i>
                <p>Dados BI</p>
            </li>
        </ul>
    </nav>
</aside>
<main>
    <!-- TOPO DA PÁGINA -->
    <header>
        <h1>Tipo Indústria</h1>
        <div>
            <a href="" class="atualizar">
                <button name="atualizar">
                    <img src="${pageContext.request.contextPath}/assets/elements/icon-atualizar.svg" alt="icon-atualizar">
                    Atualizar
                </button>
            </a>
            <button class="btnAdicionar" id="btnAdicionar"><img src="${pageContext.request.contextPath}/assets/elements/icon-adicionar.svg" alt="icone-adicionar">Adicionar registro</button>
        </div>

    </header>

    <hr>

    <form action="<%=request.getContextPath()%>/tipo-industria/filtro" class="form-pesquisa">
        <input type="text" placeholder="Pesquisar" name="filtro">
        <button type="submit"><i class="fa-solid fa-magnifying-glass"></i></button>
    </form>

    <!-- TABELA -->
    <table>
        <thead>
        <tr>
            <th class="id">
                <div>
                    <p>ID</p>
                    <form action="">
                        <button type="submit" value="porId"><i class="fa-solid fa-angle-down"></i></button>
                    </form>
                </div>

            </th>
            <th>
                <div>
                    <p>NOME</p>
                    <form action="">
                        <button type="submit" value="porNome"><i class="fa-solid fa-angle-down"></i></button>
                    </form>
                </div>

            </th>
            <th>
                <div>
                    <p>DESCRIÇÃO</p>
                </div>

            </th>
        </tr>
        </thead>
        <tbody>
        <% for (TipoIndustria registro : registros){%>
        <tr>
            <td class="id"><%=registro.getIdTipoIndustria()%></td>
            <td><%=registro.getNomeTipoIndustria()%></td>
            <td><%=registro.getDescricao()%></td>

            <td class="acoes">
                <div>
                    <button name="editar">
                        <img src="${pageContext.request.contextPath}/assets/elements/editar.svg" alt="">
                    </button>
                    <button type="submit" name="apagar">
                        <img src="${pageContext.request.contextPath}/assets/elements/apagar.svg" alt="">
                    </button>
                </div>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
</main>
</body>
<script src="${pageContext.request.contextPath}/assets/js/script.js"></script>
<script>
    chamarPopUpDeAdd("/crud/assets/modals/popUpAddIndustria.html")
</script>
</html>
<%
} catch (UsuarioNaoAutenticadoException causa){
%>
<html>
<head>

</head>
<body>
<h1>Acesso não autenticado</h1>
<a href="/crud/entrar.jsp">Autenticar</a>
</body>
</html>
<%
    } catch (RequisicaoSemRegistrosException causa){
        requisicaoResposta.despacharPara("/tipo-industria/registros");
    }
%>