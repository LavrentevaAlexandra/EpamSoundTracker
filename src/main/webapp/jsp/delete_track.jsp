<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="icon" href="${pageContext.request.contextPath}/images/vinyl.ico">
    <title><fmt:message key="track.delete"/></title>
</head>
<body>
<c:set var="page" value="path.page.delete_track" scope="session"/>
<%@ include file="menu.jsp"%>
<div class="container" style="margin-top: 90px">
    <div class="jumbotron">
        <div>
            <h3><b><fmt:message key="track.delete.confirm"/></b></h3>
            <hr>
            ${track_name}
        </div>
        <div>
            <button class="btn btn-lg btn-primary" type="button"
                    onClick='location.href="${pageContext.request.contextPath}/controller?command=delete&track_id=${track_id}"'>
                <fmt:message key="track.delete"/>
            </button>
        </div>
    </div>
</div>
<%@ include file="footer.jsp"%>
</body>
</html>
