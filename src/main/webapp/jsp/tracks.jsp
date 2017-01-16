<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.simplePagination.js"></script>
    <link href="${pageContext.request.contextPath}/css/simplePagination.css" rel="stylesheet">
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
                    <button class="btn btn-link">
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
                    <button class="btn btn-primary">
                        <i class="glyphicon glyphicon-credit-card"></i>
                        <fmt:message key="track.buy"/>
                    </button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div id="light-pagination" class="pagination">
    <script type='text/javascript'>
        $(window).load(function(){
            $('#light-pagination').pagination({
                items: 2,
                itemsOnPage: 1,
                prevText: 'Начало',
                nextText: 'Конец',
                cssStyle: 'light-theme'
            });
        });
    </script>
</div>

</body>
</html>
