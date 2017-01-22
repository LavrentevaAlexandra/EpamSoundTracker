<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="content" />
<html>
<head>
    <meta charset="UTF-8">
    <meta name="description" content="Load soundtracks site">
    <meta name="author" content="Lavrenteva">
    <link rel="icon" href="${pageContext.request.contextPath}/images/vinyl.ico">

    <title>SoundTracker</title>

    <!-- Custom styles for this template -->
    <link href="${pageContext.request.contextPath}/css/stdashboard.css" rel="stylesheet">
</head>
<body>
  <c:set var="page" value="path.page.home" scope="session"/>
    <%@ include file="menu.jsp"%>
    <div class="container-fluid">
      <div class="row col-md-offset-2  col-sm-offset-3">
          <%@include file="sidebar.jsp"%>
          <h2 class="sub-header"><fmt:message key="track.last"/></h2>
          <%@include file="tracks.jsp"%>
      </div>
    </div>
    <%@include file="footer.jsp"%>
</body>
</html>