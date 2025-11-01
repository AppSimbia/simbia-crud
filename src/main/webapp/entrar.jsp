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
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/entrar/style.css">
  <link rel="icon" href="${pageContext.request.contextPath}/assets/elements/logo-bolinha.svg">
  <title>Bem-vindo ao Crud Simbia</title>
</head>
<body id="body-login">
<main>
  <div id="container-erro">
    bababa
  </div>
  <img src="assets/elements/simbia-logo.svg" alt="logo">
  <form action="entrar" method="POST">
    <input type="email" name="email" placeholder="Digite seu email" value="<%=(request.getParameter("email") != null ? request.getParameter("email") : "")%>" required>
    <input type="password" name="senha" placeholder="Digite sua senha" required>

    <button type="submit">Entrar</button>
  </form>
</main>
</body>
<script src="/crud/assets/js/script.js"></script>
<script>
  <%
   if (requisicaoResposta.existeAtributoNaRequisicao("erro")){

    %>chamarErro('<%= (String) requisicaoResposta.recuperarAtributoDaRequisicao("erro") %>')<%
    requisicaoResposta.removerAtributoNaSessao("erro");

    }
  %>
</script>
</html>
