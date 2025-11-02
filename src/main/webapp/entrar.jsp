<%@ page import="simbia.app.crud.model.servlet.RequisicaoResposta" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  RequisicaoResposta requisicaoResposta = new RequisicaoResposta(request, response);
%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/entrar/style.css?v=1.3">
  <link rel="icon" href="${pageContext.request.contextPath}/assets/elements/logo-bolinha.svg">
  <title>Bem-vindo ao Crud Simbia</title>
</head>
<body id="body-login">
<main>
  <div id="container-erro"></div>

  <img src="assets/elements/simbia-logo.svg" alt="logo">
  <form action="entrar" method="POST">
    <input type="email" name="email" placeholder="Digite seu email" value="<%=(request.getParameter("email") != null ? request.getParameter("email") : "")%>" required>
    <input type="password" name="senha" placeholder="Digite sua senha" required>

    <button type="submit">Entrar</button>
  </form>
</main>
</body>
<%
   if (requisicaoResposta.existeAtributoNaRequisicao("erro")){
%>
<script src="/crud/assets/js/script.js"></script>
<script>
  chamarErro('<%= (String) requisicaoResposta.recuperarAtributoDaRequisicao("erro") %>')
</script>
<%
    requisicaoResposta.removerAtributoNaSessao("erro");
    }
%>
</html>
