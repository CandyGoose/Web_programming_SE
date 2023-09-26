<%@ page contentType="text/html;charset=UTF-8"%>
<head>
    <meta charset="utf-8">
    <meta name="author" content="Kasianenko Vera">
    <meta name="description" content="Second lab of Web-Programming">
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <title>${requestScope['title']}</title>

    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/favicon.ico" type="image/x-icon">
    <link rel="icon" href="${pageContext.request.contextPath}/img/favicon.ico" type="image/x-icon">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script>
        const contextPath = "${pageContext.request.contextPath}";
    </script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/utils.js"></script>
</head>