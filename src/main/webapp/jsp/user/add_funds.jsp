<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="content" />
<html>
<head>
    <link rel="icon" href="${pageContext.request.contextPath}/images/vinyl.ico">
    <title><fmt:message key="profile.title.cash"/></title>
</head>
<body>
  <c:set var="page" value="path.page.add.funds" scope="session"/>
  <%@ include file="../menu.jsp"%>
  <form method="post" class="form-horizontal" style="margin-top: 100px" id="add_funds" name="addFunds" action="${pageContext.request.contextPath}/controller">
    <c:if test="${not empty error}">
      <div class="alert alert-danger"style="margin-top: 90px">${error}</div>
    </c:if>
    <div class="form-group">
      <label for="card" class="col-sm-3 control-label"><fmt:message key="signup.card"/> *</label>
      <div class="col-sm-6">
          <input type="text" class="form-control" data-parsley-required data-parsley-type="number"
                 data-parsley-length="[13,18]" name="card" id="card" value="${user.cardNumber}" />
      </div>
    </div>
    <div class="form-group">
      <label for="cash" class="col-sm-3 control-label"><fmt:message key="profile.money"/> *</label>
      <div class="col-sm-6">
        <input type="number" class="form-control" name="cash" id="cash" data-parsley-required data-parsley-type="number"
               step="0.01" data-parsley-min="0.10" data-parsley-max="99.99"/>
      </div>
    </div>
    <div class="form-group">
      <div class="col-sm-offset-3 col-sm-9 m-t-15">
        <button type="submit" name="command" value="add_funds" class="btn btn-primary">
          <fmt:message key="profile.title.cash"/>
        </button>
        <button type="button" class="btn btn-primary " onClick='location.href="profile.jsp"'>
          <fmt:message key="form.back"/>
        </button>
      </div>
    </div>
  </form>
  <div class="form-group">
  <%@include file="../footer.jsp"%>
    <script src="${pageContext.request.contextPath}/js/parsley.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/i18n/ruu.js"></script>
    <script src="${pageContext.request.contextPath}/js/i18n/enn.js"></script>
    <script>
      $(document).ready(function(){
        $('#add_funds').parsley();
        window.Parsley.setLocale($("#locale").val());
      });
    </script>
</body>
</html>
