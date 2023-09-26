<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="statusCode" scope="request" value="<%=response.getStatus()%>"/>
<html lang="ru">
<c:set var="title" scope="request" value="Error ${statusCode}"/>
<body>
<div class="content background">
    <h2 class="error-label">Error (${statusCode})</h2>
</div>
</body>
</html>
