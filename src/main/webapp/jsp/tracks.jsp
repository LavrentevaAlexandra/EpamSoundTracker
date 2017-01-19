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
    <table class="table table-striped">
        <thead>
        <tr>
            <th>Artist</th>
            <th></th>
            <th>Track</th>
            <th></th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <tbody id="myTable">
        <c:forEach var="track" items="${track_list}">
            <tr>
                <td>${track.artist}</td>
                <td>—</td>
                <td>${track.name}</td>
                <td>
                    <button class="btn btn-link"
                            onClick='location.href="${pageContext.request.contextPath}/controller?command=track_info&track_id=${track.id}"'>
                        <fmt:message key="track.more"/>
                    </button>
                </td>
                <td>
                    <ctg:isAdmin>
                        <button class="btn btn-info">
                            <i class="glyphicon glyphicon-edit"></i>
                            <fmt:message key="track.edit"/>
                        </button>
                    </ctg:isAdmin>
                </td>
                <td>
                    <ctg:isAdmin>
                        <button class="btn btn-info">
                            <i class="glyphicon glyphicon-trash"></i>
                            <fmt:message key="track.delete"/>
                        </button>
                    </ctg:isAdmin>
                </td>
                <td>
                    <ctg:isLoggedIn>
                        <button class="btn btn-primary">
                            <i class="glyphicon glyphicon-credit-card"></i>
                            <fmt:message key="track.buy"/>
                        </button>
                    </ctg:isLoggedIn>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

</body>
</html>
