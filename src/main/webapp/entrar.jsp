<%@ page import="simbia.app.crud.infra.servlet.exception.ErrosDeDevolucaoParaClient" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/entrar/style.css">
  <link rel="icon" href="${pageContext.request.contextPath}/assets/elements/logo-bolinha.svg">
  <title>Bem-vindo ao Crud Simbia</title>
</head>
<body id="body-login">
<main>
  <% if (request.getAttribute("erro") == ErrosDeDevolucaoParaClient.EMAIL_OU_SENHA_INCORRETOS){%>
  <div id="div-erro">
    <p>Usuário ou senha inválidos</p>
    <img src="assets/elements/erro-icon.svg" alt="icon-error">
  </div>
  <% } else if (request.getAttribute("erro") == ErrosDeDevolucaoParaClient.ERRO_DE_COMUNICACAO_COM_O_BANCO_DE_DADOS) {%>
  <div id="div-erro">
    <p>Servidor instável, tente de novamente</p>
    <img src="assets/elements/erro-icon.svg" alt="icon-error">
  </div>
  <% } %>
  <img src="assets/elements/simbia-logo.svg" alt="logo">
  <form action="entrar" method="POST">
    <input type="email" name="email" placeholder="Digite seu email" value="<%=(request.getParameter("email") != null ? request.getParameter("email") : "")%>" required>
    <input type="password" name="senha" placeholder="Digite sua senha" required>

    <button type="submit">Entrar</button>
  </form>
</main>
</body>
</html>
