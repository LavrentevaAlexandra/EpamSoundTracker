<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="content" />
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <link rel="icon" href="${pageContext.request.contextPath}/images/vinyl.ico">

    <!-- Bootstrap core CSS -->
    <title><fmt:message key="profile.title"/></title>

    <!-- Custom styles for this template -->
</head>
<body>
  <c:set var="page" value="path.page.profile" scope="session"/>
  <%@ include file="menu.jsp"%>
  <div class="container" style="margin-top: 90px">
      <c:if test="${not empty success}">
          <div class="alert alert-success">${success}</div>
      </c:if>
      <div class="jumbotron">
          <div>
              <button class="btn-lg btn-primary pull-right" onClick='location.href="${pageContext.request.contextPath}/jsp/change.jsp"'>
                  <fmt:message key="profile.change"/><span class="glyphicon glyphicon-edit"></span>
              </button>
              <h3><fmt:message key="profile.login"/> ${user.login}</h3>
          </div>
          <div>
              <h3><fmt:message key="profile.cash"/> ${user.cash}</h3>
          </div>
          <div>
              <h3 title=<fmt:message key="profile.title.discount"/>><fmt:message key="profile.discount"/> ${user.discount}</h3>
          </div>
          <div>
              <h3><fmt:message key="profile.card"/> ${user.cardNumber}</h3>
          </div>
          <div>
              <h3><fmt:message key="profile.email"/> ${user.email}</h3>
          </div>
          <div>
              <button class="btn-lg btn-primary" onClick='location.href="${pageContext.request.contextPath}/jsp/password.jsp"'>
                <fmt:message key="profile.title.pass"/><span class="glyphicon glyphicon-edit"></span>
              </button>
              <button class="btn-lg btn-primary" onClick='location.href="${pageContext.request.contextPath}/jsp/add_funds.jsp"'>
                <fmt:message key="profile.title.cash"/><span class="glyphicon glyphicon-edit"></span>
              </button>
              <button class="btn btn-lg btn-primary" type="button"
                      onClick='location.href="${pageContext.request.contextPath}/controller?command=main"'>
                  <fmt:message key="form.back"/>
              </button>

          </div>
      </div>
  </div>
  <%@ include file="footer.jsp"%>
</body>
</html>
