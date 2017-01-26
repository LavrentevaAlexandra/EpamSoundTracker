<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="content"/>
<html>
<head>
    <link rel="icon" href="${pageContext.request.contextPath}/images/vinyl.ico">
    <title><fmt:message key="sidebar.bonus"/></title>
</head>
<body>
<c:set var="page" value="path.page.show_users" scope="session"/>
<%@ include file="../menu.jsp" %>
<div class="container" style="margin-top: 90px">
    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>
    <c:if test="${not empty success}">
        <div class="alert alert-success">${success}</div>
    </c:if>
    <form class="form-inline" name="search" action="${pageContext.request.contextPath}/controller">
        <div class="form-group">
            <div class="form-inline">
                <button class="btn btn-primary" type="button"
                        onClick='location.href="${pageContext.request.contextPath}/controller?command=main"'>
                    <fmt:message key="form.back"/>
                </button>
                <input type="text" name="find" class="form-control" placeholder=<fmt:message key="menu.search"/>>
                <button type="submit" class="btn btn-link" name="command"
                        value="search_users"><span class="glyphicon glyphicon-search"></span></button>
            </div>
        </div>
    </form>
    <div class="table-responsive">
        <table class="table table-striped">
            <thead>
            <tr>
                <th><fmt:message key="profile.login"/></th>
                <th><fmt:message key="profile.orders"/></th>
                <th><fmt:message key="profile.discount"/></th>
            </tr>
            </thead>
            <tbody id="myTable">
            <c:forEach var="client" items="${users}">
                <tr>
                    <td>${client.login}</td>
                    <td>${client.orderCount}</td>
                    <td>
                        <form class="form-inline" method="post" id="set_bonus" name="set_bonus"
                              action="${pageContext.request.contextPath}/controller?user_id=${client.id}">
                            <div class="form-group">
                                <div>
                                    <button type="submit" name="command" value="set_bonus" class="btn btn-primary pull-right">
                                        <fmt:message key="profile.set.bonus"/>
                                    </button>
                                    <input id="bonus${client.id}" style="margin-right: 3px"
                                           name="bonus${client.id}" type="number" class="form-control input-md"
                                           step="1" min="0" max="100" title="0-100 %" value="${client.discount}">
                                </div>
                            </div>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>


</div>
<%@include file="../footer.jsp" %>
</body>
</html>
