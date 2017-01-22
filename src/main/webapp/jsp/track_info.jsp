<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="content"/>
<html>
<head>
    <link rel="icon" href="${pageContext.request.contextPath}/images/vinyl.ico">
    <title><fmt:message key="track.more"/></title>
</head>
<body>
<c:set var="page" value="path.page.track_info" scope="session"/>
<%@ include file="menu.jsp" %>
<div class="container" style="padding-top: 80px; padding-bottom: 80px">
    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>
    <div class="jumbotron">
        <div>
            <h3><b><fmt:message key="add.track.name"/></b> ${track.name}</h3>
        </div>
        <div>
            <h3><b><fmt:message key="add.track.artist"/></b> ${track.artist}</h3>
        </div>
        <div>
            <h3><b><fmt:message key="add.track.genre"/></b> ${track.genre}</h3>
        </div>
        <div>
            <h3><b><fmt:message key="add.track.price"/></b> ${track.price}</h3>
        </div>
        <div>
            <button class="btn btn-lg btn-primary" type="button"
                    onClick='location.href="${pageContext.request.contextPath}/controller?command=main"'>
                <fmt:message key="track.back"/>
            </button>
        </div>
    </div>
    <h2 class="sub-header"><fmt:message key="comment"/>
        <p class="text-info">(${fn:length(comments)})</p>
    </h2>
    <hr>
    <div class="col-sm-8" style="padding-bottom: 20px">
        <ctg:isLoggedIn>
            <form method="post" id="comment_form" action="${pageContext.request.contextPath}/controller?track_id=${track.id}">
                <div class="form-group">
                    <label for="comment_area"><fmt:message key="comment.your"/></label>
                    <textarea id="comment_area" name="comment_area" class="form-control" rows="3"
                              data-parsley-required data-parsley-length="[1,65535]"></textarea>
                </div>
                <button type="submit" name="command" value="comment" class="btn btn-default"><fmt:message key="comment.send"/></button>
            </form>
        </ctg:isLoggedIn>
        <div class="panel panel-default">
            <c:forEach var="comment" items="${comments}">
                <div class="panel-heading" style="padding-top: 3px">
                    <strong>${comment.userLogin}</strong> <span class="text-muted">${comment.dateTime}</span>
                </div>
                <div class="panel-body">
                        ${comment.text}
                </div>
            </c:forEach>
        </div>
    </div>
</div>
<%@include file="footer.jsp" %>
</body>
</html>
