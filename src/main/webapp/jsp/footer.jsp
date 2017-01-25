<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="content" />
<html>
<head>
  <meta charset="UTF-8">
  <link href="${pageContext.request.contextPath}/css/footer_ST.css" rel="stylesheet">
</head>
<body>
  <footer class="footer">
    <div class="container">
      <div class="pull-right">
        <div class="dropup" style="margin-top: 20%">
          <button class="btn btn-default dropdown-toggle" type="button" data-toggle="dropdown">
            <fmt:message key="footer.lang"/><span class="caret"></span>
          </button>
          <ul class="dropdown-menu">
            <li><a href="${pageContext.request.contextPath}/controller?command=language&lang=ru_RU">Русский</a></li>
            <li><a href="${pageContext.request.contextPath}/controller?command=language&lang=en_US">English(US)</a></li>
          </ul>
        </div>
      </div>
      <p class="text-muted"><fmt:message key="footer.rights"/>&copy2017</p>
      <input type="hidden" id="locale" value="${locale}">
    </div>
  </footer>

  <!-- Include all compiled plugins (below), or include individual files as needed -->
  <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
  <script>window.jQuery || document.write('<script src="../js/vendor/jquery.min.js"><\/script>')</script>
  <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>
