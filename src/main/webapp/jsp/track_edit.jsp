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
<c:set var="page" value="path.page.track_edit" scope="session"/>
<%@ include file="menu.jsp" %>
<div class="container" style="margin-top: 90px">
    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>
    <form method="post" class="form-horizontal" id="edit" name="edit"
          action="${pageContext.request.contextPath}/controller">
        <div class="form-group">
            <label class="col-md-4 control-label" for="name"><fmt:message key="add.track.name"/> *</label>
            <div class="col-md-6">
                <input id="name" name="name" type="text" class="form-control input-md"
                       data-parsley-required data-parsley-length="[1,100]" value="${track.name}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-md-4 control-label" for="artist"><fmt:message key="add.track.artist"/> *</label>
            <div class="col-md-6">
                <input id="artist" name="artist" type="text" class="form-control input-md"
                       data-parsley-required data-parsley-length="[1,255]" value="${track.artist}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-md-4 control-label" for="price"><fmt:message key="add.track.price"/> *</label>
            <div class="col-md-2">
                <input id="price" name="price" type="number" class="form-control input-md"
                       data-parsley-required data-parsley-type="number" step="0.01" data-parsley-min="0.50"
                       data-parsley-max="9.99" value="${track.price}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-md-4 control-label" for="genre"><fmt:message key="add.track.genre"/> *</label>
            <div class="col-md-4">
                <input id="genre" name="genre" type="text" class="form-control input-md"
                       data-parsley-required value="${track.genre}">
            </div>
        </div>
        <div class="form-group">
            <div class="col-md-4 col-md-offset-4">
                <button type="submit" name="command" value="edit" class="btn btn-primary">
                    <fmt:message key="track.edit"/>
                </button>
                <button type="button" class="btn btn-primary "
                        onClick='location.href="${pageContext.request.contextPath}/jsp/track_info.jsp"'>
                    <fmt:message key="form.back"/>
                </button>
            </div>
        </div>
    </form>
</div>
<%@include file="footer.jsp" %>

<script src="${pageContext.request.contextPath}/js/parsley.min.js"></script>
<script src="${pageContext.request.contextPath}/js/i18n/ruu.js"></script>
<script src="${pageContext.request.contextPath}/js/i18n/enn.js"></script>
<script>
    $(document).ready(function () {
        $('#edit').parsley();
        window.Parsley.setLocale($("#locale").val());
    });
</script>

</body>
</html>
