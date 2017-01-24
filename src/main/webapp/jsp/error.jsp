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

    <title><fmt:message key="error.title"/></title>

    <!-- Bootstrap core CSS -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<c:set var="page" value="path.page.error" scope="session"/>
<%@ include file="menu.jsp" %>
<div class="container theme-showcase" style="margin:90px 5%">
    <div class="row">
        <label class="label-danger"><fmt:message key="error.message"/></label>
    </div>
    <div class="row">
        <label class="control-label">${error}</label>
    </div>

    <div class="row">
        <label class="control-label"><fmt:message key="error.request"/> ${pageContext.errorData.requestURI}
            <fmt:message key="error.fail"/></label>
    </div>
    <div class="row alert alert-danger">
        <label class="control-label"><fmt:message key="error.status"/>${pageContext.errorData.statusCode}</label>
    </div>
    <div class="row">
        <label class="control-label"><fmt:message key="error.exception"/> ${pageContext.errorData.throwable}</label>
    </div>
    <button class="btn btn-lg btn-primary "
            onClick='location.href="${pageContext.request.contextPath}/controller?command=main"'>
        <fmt:message key="form.back"/></button>
</div>
</body>
</html>
