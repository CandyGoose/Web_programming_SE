<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="statusCode" scope="request" value="<%=response.getStatus()%>"/>
<html lang="ru">
<c:set var="title" scope="request" value="Error ${statusCode}"/>
<%@ include file="/jsp/head.jsp" %>
<body>
<%@ include file="header.jsp" %>
<div class="content light-theme background">

    <c:set var="localizedError" value="error.${statusCode}"/>
    <h2 class="error-label">Error (${statusCode})</h2>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utils.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>
