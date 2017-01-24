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

    <!-- Bootstrap core CSS -->
    <title><fmt:message key="signup.title"/></title>
    <!-- Custom styles for this template -->

</head>
<body>
<c:set var="page" value="path.page.signup" scope="session"/>
<%@ include file="menu.jsp" %>
<form method="post" class="form-horizontal" style="margin-top: 10%" id="regform" name="signup"
      action="${pageContext.request.contextPath}/controller">
    <c:if test="${not empty error}">
        <div class="alert alert-danger ">${error}</div>
    </c:if>
    <div class="form-group">
        <label class="col-sm-3 control-label" for="login"><fmt:message key="form.login"/> *</label>
        <div class="col-sm-6">
            <input type="text" class="form-control" id="login" name="login" data-parsley-required
                   data-parsley-pattern="[a-zA-Z0-9]{6,10}"  value="${login}" title="<fmt:message key="form.login.pattern"/>"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label" for="pass"><fmt:message key="form.pass"/> *</label>
        <div class="col-sm-3">
            <input type="password" id="pass" name="password" class="form-control" data-parsley-required
                   data-parsley-length="[6, 10]" data-parsley-trigger="keyup" value="${password}">
        </div>
        <div class="col-sm-3">
            <input type="password" class="form-control" name="password2" data-parsley-required
                   data-parsley-equalto="#pass"
                   data-parsley-trigger="keyup" value="${password2}" title=<fmt:message key="signup.confpass"/>>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label" for="email"><fmt:message key="signup.email"/> *</label>
        <div class="col-sm-6">
            <input type="email" class="form-control" data-parsley-required data-parsley-type="email"
                   data-parsley-trigger="keyup" name="email" id="email" value=${email}>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label" for="card"><fmt:message key="signup.card"/> *</label>
        <div class="col-sm-6">
            <input type="text" class="form-control" data-parsley-required data-parsley-type="number"
                   data-parsley-length="[13,18]" name="card" id="card" value="${card}"/>
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-offset-3 col-sm-9 m-t-15">
            <button type="submit" name="command" value="signup" class="btn btn-primary"><fmt:message
                    key="signup.submit"/></button>
            <button type="reset" class="btn btn-default m-l-5"><fmt:message key="form.cancel"/></button>
            <button type="button" class="btn btn-primary "
                    onClick='location.href="${pageContext.request.contextPath}/controller?command=main"'>
                <fmt:message key="form.back"/>
            </button>
        </div>
    </div>
</form>
<%@include file="footer.jsp" %>

<script src="${pageContext.request.contextPath}/js/parsley.min.js"></script>
<script src="${pageContext.request.contextPath}/js/i18n/enn.js"></script>
<script src="${pageContext.request.contextPath}/js/i18n/ruu.js"></script>
<script>
    $(document).ready(function () {
        $('#regform').parsley();
        window.Parsley.setLocale($("#locale").val());
    });
</script>
</body>
</html>
