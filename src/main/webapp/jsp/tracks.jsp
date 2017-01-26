<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="content"/>
<html>
<head>
    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
</head>
<body>
<%--begin="${num_page * 10}" end="${num_page * 10 + 9}"--%>
<div class="table-responsive">
    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>
    <c:if test="${not empty success}">
        <div class="alert alert-success">${success}</div>
    </c:if>
    <c:choose>
        <c:when test="${fn:length(track_list)eq 0}">
            <h2><fmt:message key="track.empty"/></h2>
        </c:when>
        <c:otherwise>
            <c:set var="number_of_pages" value="${fn:length(track_list)/5 +1}" scope="request"/>
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><fmt:message key="add.track.artist"/></th>
                    <th></th>
                    <th><fmt:message key="add.track.name"/></th>
                    <th><fmt:message key="add.track.price"/></th>
                </tr>
                </thead>
                <tbody id="myTable">
                <c:forEach var="track" items="${track_list}" begin="${num_page * 5}" end="${num_page * 5 + 4}">
                    <tr>
                        <td>${track.artist}</td>
                        <td>—</td>
                        <td>${track.name}</td>
                        <td>${track.price}</td>
                        <ctg:isDeleted>
                            <td>
                                <button class="btn btn-info"
                                        onClick='location.href="${pageContext.request.contextPath}/controller?command=recover&track_id=${track.id}"'>
                                    <fmt:message key="track.recover"/>
                                </button>
                            </td>
                        </ctg:isDeleted>
                        <ctg:notDeleted>
                            <td>
                                <button class="btn btn-link"
                                        onClick='location.href="${pageContext.request.contextPath}/controller?command=track_info&track_id=${track.id}"'>
                                    <fmt:message key="track.more"/>
                                </button>
                            </td>
                            <td>
                                <ctg:isAdmin>
                                    <button class="btn btn-info"
                                            onclick='location.href="${pageContext.request.contextPath}/controller?command=delete&track_id=${track.id}"'>
                                        <i class="glyphicon glyphicon-trash"></i>
                                        <fmt:message key="track.delete"/>
                                    </button>
                                </ctg:isAdmin>
                            </td>
                            <td>
                                <ctg:isLoggedIn>
                                    <c:choose>
                                        <c:when test="${not empty is_my_orders}">
                                            <button class="btn btn-primary"
                                                    onclick='location.href="${pageContext.request.contextPath}/controller?command=download&track_id=${track.id}"'>
                                                <i class="glyphicon glyphicon-credit-card"></i>
                                                <fmt:message key="track.download"/>
                                            </button>
                                        </c:when>
                                        <c:otherwise>
                                            <button class="btn btn-primary"
                                                    onclick='location.href="${pageContext.request.contextPath}/controller?command=buy&track_id=${track.id}&price=${track.price}"'>
                                                <i class="glyphicon glyphicon-credit-card"></i>
                                                <fmt:message key="track.buy"/>
                                            </button>
                                        </c:otherwise>
                                    </c:choose>
                                </ctg:isLoggedIn>
                            </td>
                        </ctg:notDeleted>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <c:forEach begin="1" end="${number_of_pages}" var="i">
                <td>
                    <button class="btn btn-default"
                            onclick='location.href="${pageContext.request.contextPath}/controller?command=change_page&page=${i-1}"'>${i}</button>
                </td>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
