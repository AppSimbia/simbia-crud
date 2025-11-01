<%@ page import="simbia.app.crud.util.UtilitariosJSP" %>
<%@ page import="simbia.app.crud.util.ValidacoesDeDados" %>
<%@ page import="simbia.app.crud.model.servlet.RequisicaoResposta" %>
<%@ page import="java.util.List" %>
<%@ page import="simbia.app.crud.infra.servlet.exception.operacao.UsuarioNaoAutenticadoException" %>
<%@ page import="simbia.app.crud.infra.servlet.exception.operacao.RequisicaoSemRegistrosException" %>
<%@ page import="simbia.app.crud.model.dao.VantagemPlano" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    RequisicaoResposta requisicaoResposta = new RequisicaoResposta(request, response);
    List<VantagemPlano> registros = new ArrayList<>();
    try{
        ValidacoesDeDados.validarSeAdministradorEstaAtutenticado(requisicaoResposta);

        registros = UtilitariosJSP.recuperarRegistrosDaRequisicao(requisicaoResposta, "vantagemplano");

    } catch (UsuarioNaoAutenticadoException causa){
        requisicaoResposta.despacharPara("/assets/paginas-de-erro/erro-autenticacao.html");
        return;

    } catch (RequisicaoSemRegistrosException causa){
        requisicaoResposta.despacharPara("/vantagem-plano/registros");
        return;
    }
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
    <link rel="icon" href="${pageContext.request.contextPath}/assets/elements/logo-bolinha.svg">
    <title>Simbia - Plano</title>
</head>
<body>
<div id="container-geral-popup"></div>
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
            <li class="ativo">
                <i class="fa-solid fa-square-check"></i>
                <p>Vantagem plano</p>
            </li>

            <a href="${pageContext.request.contextPath}/tipo-industria.jsp">
                <li>
                    <i class="fa-solid fa-layer-group"></i>
                    <p>Tipo Indústria</p>
                </li>
            </a>
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
        <h1>Vantagem Plano</h1>
        <div>
            <a href="<%=request.getContextPath()%>/vantagem-plano/atualizar" class="atualizar">
                <button name="atualizar">
                    <img src="${pageContext.request.contextPath}/assets/elements/icon-atualizar.svg" alt="icon-atualizar">
                    Atualizar
                </button>
            </a>
            <button class="btnAdicionar" id="btnAdicionar"><img src="${pageContext.request.contextPath}/assets/elements/icon-adicionar.svg" alt="icone-adicionar">Adicionar registro</button>
        </div>

    </header>

    <hr>

    <form action="<%=request.getContextPath()%>/vantagem-plano/filtro" class="form-pesquisa">
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
                    <form action="${pageContext.request.contextPath}/vantagem-plano/ordenar" method="GET">
                        <input type="hidden" name="tipoOrdenacao" value="porId">
                        <input type="hidden" name="ordem" value="<%=
              (request.getAttribute("criterioOrdenacao") != null && request.getAttribute("criterioOrdenacao").equals("porNome")
                && request.getAttribute("ordemAtual") != null && request.getAttribute("ordemAtual").equals("asc"))
              ? "desc" : "asc"
            %>">
                        <button type="submit">
                            <i class="fa-solid <%=
                (request.getAttribute("criterioOrdenacao") != null && request.getAttribute("criterioOrdenacao").equals("porNome"))
                  ? (request.getAttribute("ordemAtual").equals("asc") ? "fa-angle-up icone-ativo" : "fa-angle-down icone-ativo")
                  : "fa-angle-down"
              %>"></i>
                        </button>
                    </form>
                </div>

            </th>
            <th>
                <div>
                    <p>ID-PLANO</p>
                    <form action="${pageContext.request.contextPath}/vantagem-plano/ordenar" method="GET">
                        <input type="hidden" name="tipoOrdenacao" value="porIdPlano">
                        <input type="hidden" name="ordem" value="<%=
              (request.getAttribute("criterioOrdenacao") != null && request.getAttribute("criterioOrdenacao").equals("porNome")
                && request.getAttribute("ordemAtual") != null && request.getAttribute("ordemAtual").equals("asc"))
              ? "desc" : "asc"
            %>">
                        <button type="submit">
                            <i class="fa-solid <%=
                (request.getAttribute("criterioOrdenacao") != null && request.getAttribute("criterioOrdenacao").equals("porNome"))
                  ? (request.getAttribute("ordemAtual").equals("asc") ? "fa-angle-up icone-ativo" : "fa-angle-down icone-ativo")
                  : "fa-angle-down"
              %>"></i>
                        </button>
                    </form>
                </div>

            </th>
            <th>
                <div>
                    <p>ID-VANTAGEM</p>
                    <form action="${pageContext.request.contextPath}/vantagem-plano/ordenar" method="GET">
                        <input type="hidden" name="tipoOrdenacao" value="porIdVantagem">
                        <input type="hidden" name="ordem" value="<%=
              (request.getAttribute("criterioOrdenacao") != null && request.getAttribute("criterioOrdenacao").equals("porNome")
                && request.getAttribute("ordemAtual") != null && request.getAttribute("ordemAtual").equals("asc"))
              ? "desc" : "asc"
            %>">
                        <button type="submit">
                            <i class="fa-solid <%=
                (request.getAttribute("criterioOrdenacao") != null && request.getAttribute("criterioOrdenacao").equals("porNome"))
                  ? (request.getAttribute("ordemAtual").equals("asc") ? "fa-angle-up icone-ativo" : "fa-angle-down icone-ativo")
                  : "fa-angle-down"
              %>"></i>
                        </button>
                    </form>
                </div>

            </th>
        </tr>
        </thead>
        <tbody>
        <% for (VantagemPlano registro : registros){%>
        <tr>
            <td class="id"><%=registro.getIdVantagemPlano()%></td>
            <td><%=registro.getIdPlano()%></td>
            <td><%=registro.getIdVantagem()%></td>

            <td class="acoes">
                <div>
                    <button name="editar" value="<%=registro.getIdVantagemPlano()%>;<%=registro.getIdVantagem()%>;<%=registro.getIdPlano()%>">
                        <img src="${pageContext.request.contextPath}/assets/elements/editar.svg">
                    </button>
                    <button type="submit" name="apagar">
                        <img src="${pageContext.request.contextPath}/assets/elements/apagar.svg">
                    </button>
                </div>
            </td>
        </tr>
        <%
        }
        %>
        </tbody>
    </table>
</main>
</body>
<script src="/crud/assets/js/script.js">
</script>
<script>
    configPopUpAdicionar('/crud/assets/modals/popup-adicionar-vantagemPlano.html', '/crud/vantagem-plano/adicionar');
</script>
</html>