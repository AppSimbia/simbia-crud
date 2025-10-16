<%@ page import="simbia.app.crud.util.ValidacoesDeDados" %>
<%@ page import="simbia.app.crud.infra.servlet.exception.UsuarioNaoAutenticadoException" %>
<%@ page import="simbia.app.crud.model.servlet.RequisicaoResposta" %>
<%@ page import="simbia.app.crud.model.dao.Administrador" %>
<%@ page import="java.util.List" %>
<%@ page import="simbia.app.crud.infra.servlet.exception.RequisicaoSemRegistrosException" %>
<%@ page import="simbia.app.crud.util.UtilitariosJSP" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  RequisicaoResposta requisicaoResposta = new RequisicaoResposta(request, response);
  try{
    ValidacoesDeDados.validarSeAdministradorEstaAtutenticado(requisicaoResposta);

    List<Administrador> registros = UtilitariosJSP.recuperarRegistrosDaRequisicao(requisicaoResposta, "administrador");
%>
<html lang="pt-br">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Simbia - Administrador</title>
  <link rel="shortcut icon" type="image/x-icon" href="/images/Logo Simbia.svg">
  <link rel="stylesheet" href="/css/style.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.0/css/all.min.css" integrity="sha512-DxV+EoADOkOygM4IR9yXP8Sb2qwgidEmeqAEmDKIOfPRQZOWbXCzLC6vjbZyy0vPisbH2SyW27+ddLVCN+OMzQ==" crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>
<body>
<div id="popup-container"></div>

<!-- ASIDE + MAIN -->
<div id="container">

  <!-- ASIDE -->
  <aside id="aside">

    <div id="sidebar">
      <nav id="sidebar-menu">
        <!-- MENU TÍTULO -->
        <div id="sidebar-title">
          <h2>Tabelas</h2>
          <i class="fa-solid fa-angle-down"></i>
        </div>

        <!-- MENU -->
        <ul id="menu">
          <li class="sidebar-item">
            <a href="/tables-crud/catIndustria.html">
              <i class="fa-solid fa-layer-group"></i>
              <span>Categoria indústria</span>
            </a>
          </li>
          <li class="sidebar-item">
            <a href="/tables-crud/catProduto.html">
              <i class="fa-solid fa-box-archive"></i>
              <span>Categoria produto</span>
            </a>
          </li>
          <li class="sidebar-item active">
            <a href="/tables-crud/adm.html">
              <i class="fa-solid fa-user-shield"></i>
              <span>Administrador</span>
            </a>
          </li>
          <li class="sidebar-item">
            <a href="/tables-crud/permissao.html">
              <i class="fa-solid fa-key"></i>
              <span>Permissão</span>
            </a>
          </li>
          <li class="sidebar-item">
            <a href="/tables-crud/vantagem.html">
              <i class="fa-solid fa-hand-sparkles"></i>
              <span>Vantagem</span>
            </a>
          </li>
          <li class="sidebar-item">
            <a href="/tables-crud/vantPlano.html">
              <i class="fa-solid fa-square-check"></i>
              <span>Vantagem do plano</span>
            </a>
          </li>
          <li class="sidebar-item">
            <a href="/tables-crud/plano.html">
              <i class="fa-solid fa-dollar-sign"></i>
              <span>Plano</span>
            </a>
          </li>
          <li class="sidebar-item">
            <a href="">
              <i class="fa-solid fa-arrow-up-right-from-square"></i>
              <span>Dados BI</span>
            </a>
          </li>
        </ul>

      </nav>
      <!-- LOGO SIMBIA -->
      <div id="sidebar-logo-simbia">
        <img src="/images/Logo Simbia.svg" alt="Logo Indústria" height="110px">
      </div>

      <!-- USER -->
      <div id="sidebar-user-avatar">
        <button id="botao-user"><i class="fa-solid fa-circle-user"></i></button>
      </div>
    </div>
  </aside>

  <!-- MAIN -->
  <main id="main">
    <!-- HEADER DA MAIN -->
    <div id="main-header">
      <h1>Administrador</h1>
      <div id="btn-add-industria">
        <i class="fa-solid fa-plus"></i>
        <span>Adicionar administrador</span>
      </div>
    </div>

    <div id="main-principal">
      <!-- PESQUISAR -->
      <div id="search-industria">
        <input type="text" placeholder="Pesquisar" class="input-search-industria">
        <i class="fa-solid fa-magnifying-glass"></i>
      </div>

      <!-- TABELA DE REGISTROS -->
      <table class="table">
        <thead>
        <tr>
          <th>ID <button type="submit"><i class="fa-solid fa-caret-down"></i></th>
          <th></th>
          <th>Usuário <button type="submit"><i class="fa-solid fa-caret-down"></i></th>
          <th>Nome <button type="submit"><i class="fa-solid fa-caret-down"></i></th>
          <th>Senha</th>
          <th></th>
          <th><input type="checkbox" name="checkbox-th"></th>
        </tr>
        </thead>
        <tbody>
        <tr>
          <td>1</td>
          <td></td>
          <td>snac@natura.net</td>
          <td>Natura&Co</td>
          <td>Ar36428o7t...</td>
          <td><i class="fa-solid fa-pen btn-edit-industria"></i></td>
          <td><input type="checkbox"></td>
        </tr>
        </tbody>
      </table>
    </div>

  </main>
</div>

</body>
<script src="/js/script.js"></script>
<script>
  chamarPopUpDeAdd("/modals/popUpAddAdm.html");
</script>
</html>
<%
  } catch (UsuarioNaoAutenticadoException causa){
%>
<html>
  <head>

  </head>
  <body>
    <h1>Permissão negada, <a href="entrar.jsp">autentique-se</a></h1>
  </body>
</html>
<%
  } catch (RequisicaoSemRegistrosException causa){
    requisicaoResposta.adicionarAtributoNaRequisicao("tabelaAlvo", "administrador");
    requisicaoResposta.despacharPara("administrador/registros");
  }
%>