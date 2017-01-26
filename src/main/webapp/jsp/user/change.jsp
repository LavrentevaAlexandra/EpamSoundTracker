<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="content"/>
<html>
<head>
    <link rel="icon" href="${pageContext.request.contextPath}/images/vinyl.ico">
    <title><fmt:message key="profile.change"/></title>
</head>
<body>
  <c:set var="page" value="path.page.change" scope="session"/>
  <%@ include file="../menu.jsp" %>

  <div class="container" style="margin-top: 90px">
    <c:if test="${not empty error}">
      <div class="alert alert-danger">${error}</div>
    </c:if>
    <form method="post" class="form-horizontal" id="change" name="change"
            action="${pageContext.request.contextPath}/controller">
      <div class="form-group">
        <label class="col-sm-3 control-label" for="login"><fmt:message key="form.login"/> *</label>
        <div class="col-sm-6">
            <input type="text" class="form-control" data-parsley-required data-parsley-pattern="[a-zA-Z0-9]{6,10}"
                   data-parsley-trigger="keyup" id="login" name="login"
                   value="${user.login}" title="<fmt:message key="form.login.pattern"/>"/>
        </div>
      </div>
      <div class="form-group">
          <label class="col-sm-3 control-label"><fmt:message key="signup.email"/> *</label>
          <div class="col-sm-6">
              <input type="email" class="form-control" data-parsley-required data-parsley-type="email"
                       data-parsley-trigger="keyup" name="email" value="${user.email}">
          </div>
      </div>
      <div class="form-group">
          <label class="col-sm-3 control-label"><fmt:message key="signup.card"/> *</label>
          <div class="col-sm-6">
              <input type="text" class="form-control" data-parsley-required data-parsley-type="number"
                   data-parsley-length="[13,18]" title="Enter new bankcard number" name="card"
                       value="${user.cardNumber}"/>
          </div>
      </div>
      <div class="form-group">
          <div class="col-sm-offset-3 col-sm-9 m-t-15">
              <button type="submit" name="command" value="change" class="btn btn-primary">
                  <fmt:message key="profile.change"/>
              </button>
              <button type="reset" class="btn btn-default m-l-5"><fmt:message key="form.cancel"/></button>
              <button type="button" class="btn btn-primary"
                      onClick='location.href="profile.jsp"'>
                    <fmt:message key="form.back"/>
              </button>
          </div>
      </div>
    </form>
</div>
<%@include file="../footer.jsp" %>

<script src="${pageContext.request.contextPath}/js/parsley.min.js"></script>
<script src="${pageContext.request.contextPath}/js/i18n/ruu.js"></script>
<script src="${pageContext.request.contextPath}/js/i18n/enn.js"></script>
<script>
    $(document).ready(function () {
        $('#change').parsley();
        window.Parsley.setLocale($("#locale").val());
    });
</script>
</body>
</html>
