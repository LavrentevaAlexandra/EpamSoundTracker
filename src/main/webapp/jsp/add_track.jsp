<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="content"/>
<html>
<head>
    <link rel="icon" href="${pageContext.request.contextPath}/images/vinyl.ico">
    <title><fmt:message key="add.track"/></title>
</head>
<body>
<c:set var="page" value="path.page.add.track" scope="session"/>
<%@ include file="menu.jsp" %>
<div class="container" style="margin-top: 90px">
  <c:if test="${not empty error}">
    <div class="alert alert-danger">${error}</div>
  </c:if>
  <c:if test="${not empty success}">
        <div class="alert alert-danger">${success}</div>
  </c:if>
  <form class="form-horizontal" method="post" id="addTrack" name="addTrack"
        action="${pageContext.request.contextPath}/controller">
    <fieldset>
        <!-- Form Name -->
        <legend><fmt:message key="add.track.title"/> </legend>

        <div class="form-group">
            <label class="col-md-4 control-label" for="name"><fmt:message key="add.track.name"/></label>
            <div class="col-md-6">
                <input id="name" name="name" type="text" class="form-control input-md" data-parsley-required >
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-4 control-label" for="artist"><fmt:message key="add.track.artist"/></label>
            <div class="col-md-6">
                <input id="artist" name="artist" type="text"  class="form-control input-md" data-parsley-required>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-4 control-label" for="price"><fmt:message key="add.track.price"/></label>
            <div class="col-md-2">
                <input id="price" name="price" type="number"  class="form-control input-md"
                       data-parsley-required data-parsley-type="number" step="0.10" data-parsley-min="0.50">
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-4 control-label" for="genre"><fmt:message key="add.track.genre"/></label>
            <div class="col-md-4">
                <input id="genre" name="genre" type="text"  class="form-control input-md"
                       data-parsley-required>
         </div>
        </div>

        <div class="form-group">
            <div class="col-md-4 col-md-offset-4">
                <button type="submit" name="command" value="add_track" class="btn btn-primary">
                    <fmt:message key="add.track"/>
                </button>
                <button type="button" class="btn btn-primary "
                        onClick='location.href="${pageContext.request.contextPath}/jsp/main.jsp"'>
                    <fmt:message key="form.back"/>
                </button>
            </div>
        </div>
    </fieldset>
  </form>
</div>
<%@include file="footer.jsp" %>

<script src="${pageContext.request.contextPath}/js/parsley.min.js"></script>
<script src="${pageContext.request.contextPath}/js/i18n/ruu.js"></script>
<script src="${pageContext.request.contextPath}/js/i18n/enn.js"></script>
<script>
    $(document).ready(function () {
        $('#addTrack').parsley();
        window.Parsley.setLocale($("#locale").val());
    });
</script>
</body>
</html>
