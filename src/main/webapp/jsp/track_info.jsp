<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="content"/>
<html>
<head>
    <title><fmt:message key="track.more"/></title>
</head>
<body>
    ${track.name}
    <%
        String ss =request.getParameter("track");
        System.out.println("my value" + ss);
    %>
</body>
</html>
