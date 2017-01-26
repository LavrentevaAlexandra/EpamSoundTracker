<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="content" />
<html>
<head>
    <link rel="icon" href="${pageContext.request.contextPath}/images/vinyl.ico">

    <title><fmt:message key="sidebar.about"/></title>

    <!-- Custom styles for this template -->
    <link href="${pageContext.request.contextPath}/css/stdashboard.css" rel="stylesheet">
</head>
<body>
<%@ include file="menu.jsp"%>
<div class="container-fluid">
    <%@include file="sidebar.jsp"%>
    <div class="row col-md-offset-2  col-sm-offset-3">
       <h3><fmt:message key="about"/></h3>
        <hr>
        <h3>+375447850866</h3>
    </div>
</div>
<%@include file="footer.jsp"%>
</body>
</html>