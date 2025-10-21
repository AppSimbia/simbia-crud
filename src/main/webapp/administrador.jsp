<%@ page import="simbia.app.crud.util.UtilitariosJSP" %>
<%@ page import="simbia.app.crud.util.ValidacoesDeDados" %>
<%@ page import="simbia.app.crud.model.servlet.RequisicaoResposta" %>
<%@ page import="java.util.List" %>
<%@ page import="simbia.app.crud.model.dao.Administrador" %>
<%@ page import="simbia.app.crud.infra.servlet.exception.UsuarioNaoAutenticadoException" %>
<%@ page import="simbia.app.crud.infra.servlet.exception.RequisicaoSemRegistrosException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  RequisicaoResposta requisicaoResposta = new RequisicaoResposta(request, response);

  try{
    ValidacoesDeDados.validarSeAdministradorEstaAtutenticado(requisicaoResposta);

    List<Administrador> registros = UtilitariosJSP.recuperarRegistrosDaRequisicao(requisicaoResposta, "administrador");

%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.0/css/all.min.css"
        integrity="sha512-DxV+EoADOkOygM4IR9yXP8Sb2qwgidEmeqAEmDKIOfPRQZOWbXCzLC6vjbZyy0vPisbH2SyW27+ddLVCN+OMzQ=="
        crossorigin="anonymous" referrerpolicy="no-referrer"/>
  <link rel="stylesheet" href="assets/style/tabelas/style.css">
  <title>Simbia - Administrador</title>
</head>
<body>
<!-- MENU LATERAL -->
<img src="assets/elements/icon-simbia.svg" alt="logo-simbia">
<aside>
  <h1>Tabelas</h1>

  <hr>
  <nav>
    <ul>
      <li class="ativo">
        <i class="fa-solid fa-user-shield"></i>
        <p>Administrador</p>
      </li>
      <li>
        <a href="">
          <i class="fa-solid fa-key"></i>
          <p>Permissão</p>
        </a>
      </li>
      <li>
        <a href="">
          <i class="fa-solid fa-hand-sparkles"></i>
          <p>Vantagem</p>
        </a>
      </li>
      <li>
        <a href="">
          <i class="fa-solid fa-dollar-sign"></i>
          <p>Plano</p>
        </a>
      </li>
      <li>
        <a href="">
          <i class="fa-solid fa-square-check"></i>
          <p>Vantagem plano</p>
        </a>
      </li>
      <li>
        <a href="">
          <i class="fa-solid fa-layer-group"></i>
          <p>Categoria Indústria</p>
        </a>
      </li>
      <li>
        <a href="">
          <i class="fa-solid fa-box-archive"></i>
          <p>Categoria Produto</p>
        </a>
      </li>
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
    <h1>Administrador</h1>
    <div>
      <a href="" class="atualizar">
        <button name="atualizar">
          <img src="assets/elements/icon-atualizar.svg" alt="icon-atualizar">
          Atualizar
        </button>
      </a>
      <button><img src="assets/elements/icon-adicionar.svg" alt="icone-adicionar">Adicionar registro</button>
    </div>

  </header>

  <hr>

  <form action="" class="form-pesquisa">
    <input type="text" placeholder="Pesquisar">
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
          <p>EMAIL</p>
          <form action="">
            <button type="submit" value="porEmail"><i class="fa-solid fa-angle-down"></i></button>
          </form>
        </div>

      </th>
      <th>
        <p>SENHA</p>
      </th>
    </tr>
    </thead>
    <tbody>
    <% for (Administrador registro : registros){%>
    <tr>
      <td class="id"><%=registro.getIdAdministrador()%></td>
      <td><%=registro.getNome()%></td>
      <td><%=registro.getEmail()%></td>
      <td><%=registro.getSenha()%></td>

      <td class="acoes">
        <div>
          <button name="editar">
            <img src="assets/elements/editar.svg" alt="">
          </button>
          <button type="submit" name="apagar">
            <img src="assets/elements/apagar.svg" alt="">
          </button>
        </div>
      </td>
    </tr>
    <% } %>
    </tbody>
  </table>
</main>
</body>
</html>
<%
  } catch (UsuarioNaoAutenticadoException causa){
%>
<html>
  <head>

  </head>
  <body>
    <h1>Acesso não autenticado</h1>
    <a href="entrar.jsp">Autenticar</a>
  </body>
</html>
<%
  } catch (RequisicaoSemRegistrosException causa){
    requisicaoResposta.despacharPara("/administrador/registros");
  }
%>