<%--
  Created by IntelliJ IDEA.
  User: bezdj
  Date: 19/01/2017
  Time: 20:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>$Title$</title>
</head>
<body>
<c:choose>
    <c:when test="${name == 'kak'}">
        kake tun ger!
    </c:when>
    <c:otherwise>
        The name is: ${name}
    </c:otherwise>
</c:choose>

</body>
</html>
