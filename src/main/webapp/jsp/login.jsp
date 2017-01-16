<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="content"/>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <link rel="icon" href="${pageContext.request.contextPath}/images/vinyl.ico">

    <title><fmt:message key="login.title"/></title>

    <link href="${pageContext.request.contextPath}/css/loginST.css" rel="stylesheet">

</head>
<body>
  <c:set var="page" value="path.page.login" scope="session"/>
  <%@ include file="menu.jsp"%>
  <c:if test="${not empty error}">
      <div class="alert alert-danger" style="margin-top: 3%">${error}</div>
  </c:if>
  <div class="container">
    <form class="form-signin" id="logform" method="post" name="login" action="${pageContext.request.contextPath}/controller">
        <label for="inputLogin"><fmt:message key="form.login"/></label>
        <input type="text" id="inputLogin" name="login" class="form-control"  data-parsley-pattern="[a-zA-Z0-9]{6,10}"
               data-parsley-required autofocus  value=${login}>
        <label for="inputPassword"><fmt:message key="form.pass"/></label>
        <input type="password" id="inputPassword" name="password" class="form-control"
               data-parsley-required data-parsley-length="[6, 10]" data-parsley-trigger="keyup" value=${password}>

        <button class="btn btn-lg btn-primary btn-block" type="submit" name="command" value="login"><fmt:message key="login.submit"/></button>
        <button class="btn btn-lg btn-primary btn-block" type="button"
                onClick='location.href="${pageContext.request.contextPath}/controller?command=main"'>
            <fmt:message key="form.back"/>
        </button>
    </form>
  </div>
<%@include file="footer.jsp"%>
  <script src="${pageContext.request.contextPath}/js/parsley.min.js"></script>
  <script src="${pageContext.request.contextPath}/js/i18n/ruu.js"></script>
  <script src="${pageContext.request.contextPath}/js/i18n/enn.js"></script>
  <script>
      $(document).ready(function(){
          $('#logform').parsley();
          window.Parsley.setLocale($("#locale").val());
      });
  </script>
</body>
</html>
