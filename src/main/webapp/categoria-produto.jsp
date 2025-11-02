<%@ page import="simbia.app.crud.util.UtilitariosJSP" %>
<%@ page import="simbia.app.crud.util.ValidacoesDeDados" %>
<%@ page import="simbia.app.crud.model.servlet.RequisicaoResposta" %>
<%@ page import="java.util.List" %>
<%@ page import="simbia.app.crud.infra.servlet.exception.operacao.UsuarioNaoAutenticadoException" %>
<%@ page import="simbia.app.crud.infra.servlet.exception.operacao.RequisicaoSemRegistrosException" %>
<%@ page import="simbia.app.crud.model.dao.CategoriaProduto" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  RequisicaoResposta requisicaoResposta = new RequisicaoResposta(request, response);
  List<CategoriaProduto> registros = new ArrayList<>();

  try {
    ValidacoesDeDados.validarSeAdministradorEstaAtutenticado(requisicaoResposta);
    registros = UtilitariosJSP.recuperarRegistrosDaRequisicao(requisicaoResposta, "categoriaproduto");
  } catch (UsuarioNaoAutenticadoException e) {
    requisicaoResposta.despacharPara("/assets/paginas-de-erro/erro-autenticacao.html");
    return;
  } catch (RequisicaoSemRegistrosException e) {
    requisicaoResposta.despacharPara("/categoria-produto/registros");
    return;
  }
%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.0/css/all.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/tabelas/style.css">
  <link rel="icon" href="${pageContext.request.contextPath}/assets/elements/logo-bolinha.svg">
  <title>Simbia - Categoria Produto</title>
</head>
<body>
<div id="container-geral-popup"></div>
<div id="container-status"></div>

<img src="${pageContext.request.contextPath}/assets/elements/icon-simbia.svg" alt="logo-simbia">
<aside>
  <h1>Tabelas</h1>
  <hr>
  <nav>
    <ul>
      <a href="${pageContext.request.contextPath}/administrador.jsp"><li><i class="fa-solid fa-user-shield"></i><p>Administrador</p></li></a>
      <a href="${pageContext.request.contextPath}/permissao.jsp"><li><i class="fa-solid fa-key"></i><p>Permissão</p></li></a>
      <a href="${pageContext.request.contextPath}/vantagem.jsp"><li><i class="fa-solid fa-hand-sparkles"></i><p>Vantagem</p></li></a>
      <a href="${pageContext.request.contextPath}/plano.jsp"><li><i class="fa-solid fa-dollar-sign"></i><p>Plano</p></li></a>
      <a href="${pageContext.request.contextPath}/vantagem-plano.jsp"><li><i class="fa-solid fa-square-check"></i><p>Vantagem plano</p></li></a>
      <a href="${pageContext.request.contextPath}/tipo-industria.jsp"><li><i class="fa-solid fa-layer-group"></i><p>Tipo Indústria</p></li></a>
      <li class="ativo"><i class="fa-solid fa-box-archive"></i><p>Categoria Produto</p></li>
      <li><i class="fa-solid fa-store"></i><p>Dados BI</p></li>
    </ul>
  </nav>
</aside>

<main>
  <header>
    <h1>Categoria Produto</h1>
    <div>
      <a href="<%=request.getContextPath()%>/categoria-produto/atualizar" class="atualizar">
        <button name="atualizar"><img src="${pageContext.request.contextPath}/assets/elements/icon-atualizar.svg" alt="icon-atualizar">Atualizar</button>
      </a>
      <button class="btnAdicionar" id="btnAdicionar">
        <img src="${pageContext.request.contextPath}/assets/elements/icon-adicionar.svg" alt="icone-adicionar">Adicionar registro
      </button>
    </div>
  </header>

  <hr>

  <form action="<%=request.getContextPath()%>/categoria-produto/filtro" class="form-pesquisa" method="GET">
    <input name="filtro" type="text" placeholder="Pesquisar">
    <button type="submit"><i class="fa-solid fa-magnifying-glass"></i></button>
  </form>

  <table>
    <thead>
    <tr>
      <th class="id">
        <div>
          <p>ID</p>
          <form action="${pageContext.request.contextPath}/categoria-produto/ordenar" method="GET">
            <input type="hidden" name="tipoOrdenacao" value="porId">
            <input type="hidden" name="ordem" value="<%= (request.getAttribute("criterioOrdenacao") != null && request.getAttribute("criterioOrdenacao").equals("porId") && request.getAttribute("ordemAtual") != null && request.getAttribute("ordemAtual").equals("asc")) ? "desc" : "asc" %>">
            <button type="submit"><i class="fa-solid <%= (request.getAttribute("criterioOrdenacao") != null && request.getAttribute("criterioOrdenacao").equals("porId")) ? (request.getAttribute("ordemAtual").equals("asc") ? "fa-angle-up icone-ativo" : "fa-angle-down icone-ativo") : "fa-angle-down" %>"></i></button>
          </form>
        </div>
      </th>
      <th>
        <div>
          <p>NOME</p>
          <form action="${pageContext.request.contextPath}/categoria-produto/ordenar" method="GET">
            <input type="hidden" name="tipoOrdenacao" value="porNome">
            <input type="hidden" name="ordem" value="<%= (request.getAttribute("criterioOrdenacao") != null && request.getAttribute("criterioOrdenacao").equals("porNome") && request.getAttribute("ordemAtual") != null && request.getAttribute("ordemAtual").equals("asc")) ? "desc" : "asc" %>">
            <button type="submit"><i class="fa-solid <%= (request.getAttribute("criterioOrdenacao") != null && request.getAttribute("criterioOrdenacao").equals("porNome")) ? (request.getAttribute("ordemAtual").equals("asc") ? "fa-angle-up icone-ativo" : "fa-angle-down icone-ativo") : "fa-angle-down" %>"></i></button>
          </form>
        </div>
      </th>
      <th>
        <p>DESCRIÇÃO</p>
      </th>
      <th><p>AÇÕES</p></th>
    </tr>
    </thead>
    <tbody>
    <% for (CategoriaProduto registro : registros) { %>
    <tr>
      <td class="id"><%= registro.getIdCategoriaProduto() %></td>
      <td><%= registro.getNomeCategoria() %></td>
      <td><%= registro.getDescricao() %></td>
      <td class="acoes">
        <div>
          <button name="editar" value="<%=registro.getIdCategoriaProduto()%>;<%=registro.getNomeCategoria()%>;<%=registro.getDescricao()%>">
            <img src="${pageContext.request.contextPath}/assets/elements/editar.svg">
          </button>
          <button name="apagar" value="<%= registro.getIdCategoriaProduto() %>">
            <img src="${pageContext.request.contextPath}/assets/elements/apagar.svg" alt="Apagar">
          </button>
        </div>
      </td>
    </tr>
    <% } %>
    </tbody>
  </table>
</main>

<script src="${pageContext.request.contextPath}/assets/js/script.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/validacao-popup.js"></script>

<script>
  <%
    String erros = (String) requisicaoResposta.recuperarAtributoDaSessao("erros");
    String dados = (String) requisicaoResposta.recuperarAtributoDaSessao("dados");
    String popupAberto = (String) requisicaoResposta.recuperarAtributoDaSessao("popupAberto");

    if ("true".equals(popupAberto)) {
        requisicaoResposta.removerAtributoNaSessao("popupAberto");
        requisicaoResposta.removerAtributoNaSessao("erros");
        requisicaoResposta.removerAtributoNaSessao("dados");
  %>
  window.addEventListener('DOMContentLoaded', function() {
    chamarPopUpAdicionar(
            '${pageContext.request.contextPath}/assets/modals/popup-adicionar-categoriaProduto.html',
            '${pageContext.request.contextPath}/categoria-produto/inserir'
    ).then(() => {
      <% if (erros != null) { %>
      setTimeout(() => exibirErrosValidacao('<%= erros.replace("'", "\\'") %>'), 150);
      <% } %>

      <% if (dados != null) { %>
      // Converte a string JSON em objeto
      const dadosFormulario = JSON.parse('<%= dados.replace("\\", "\\\\").replace("'", "\\'") %>');
      setTimeout(() => preencherCamposFormulario(dadosFormulario), 150);
      <% } %>
    });
  });
  <% } %>

  configPopUpAdicionar(
          '${pageContext.request.contextPath}/assets/modals/popup-adicionar-categoriaProduto.html',
          '${pageContext.request.contextPath}/categoria-produto/inserir'
  );
  configPopUpEditar(
          '${pageContext.request.contextPath}/assets/modals/popup-alterar-categoriaProduto.html',
          '${pageContext.request.contextPath}/categoria-produto/alterar'
  );
  configPopUpDeletar('${pageContext.request.contextPath}/categoria-produto/deletar');

  <%
    Boolean status = (Boolean) session.getAttribute("status");
    if (status != null) {
        requisicaoResposta.removerAtributoNaSessao("status");
  %>
  window.onload = function() {
    mostrarStatus('<%= status ? "sucesso" : "erro" %>');
  }
  <% } %>
</script>

</body>
</html>
