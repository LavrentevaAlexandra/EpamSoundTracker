<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="content"/>
<html>
<head>
    <link rel="icon" href="${pageContext.request.contextPath}/images/vinyl.ico">
    <title><fmt:message key="profile.title.pass"/></title>
</head>
<body>
<c:set var="page" value="path.page.pass" scope="session"/>
<%@ include file="../menu.jsp" %>
<div class="container">
    <c:if test="${not empty error}">
        <div class="alert alert-danger" style="margin-top: 90px">${error}</div>
    </c:if>
    <form method="post" class="form-horizontal" style="margin-top: 100px" id="change_pass" name="change_pass"
          action="${pageContext.request.contextPath}/controller">
        <div class="form-group">
            <label class="col-sm-3 control-label" for="password"><fmt:message key="form.pass"/> *</label>
            <div class="col-sm-6">
                <input type="password" class="form-control" name="password" id="password" data-parsley-required
                       data-parsley-length="[6, 10]" data-parsley-trigger="keyup" value="${password}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label" for="new_pass"><fmt:message key="profile.newpass"/> *</label>
            <div class="col-sm-3">
                <input type="password" id="new_pass" name="new_pass" class="form-control" data-parsley-required
                       data-parsley-length="[6, 10]" data-parsley-trigger="keyup" value="${newPass}">
            </div>
            <div class="col-sm-3">
                <input type="password" class="form-control" name="password2" data-parsley-required
                       data-parsley-equalto="#new_pass"
                       data-parsley-trigger="keyup" title=<fmt:message key="signup.confpass"/>>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-3 col-sm-9 m-t-15">
                <button type="submit" name="command" value="change_pass" class="btn btn-primary"><fmt:message
                        key="profile.title.pass"/></button>
                <button type="reset" class="btn btn-default m-l-5"><fmt:message key="form.cancel"/></button>
                <button type="button" class="btn btn-primary "
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
        $('#change_pass').parsley();
        window.Parsley.setLocale($("#locale").val());
    });
</script>
</body>
</html>
