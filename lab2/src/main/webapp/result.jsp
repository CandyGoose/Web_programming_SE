<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<c:set var="title" scope="request" value="Lab #2"/>
<%@ include file="/jsp/head.jsp" %>
<body>
    <%@ include file="/jsp/header.jsp" %>
    <div class="content background">
        <div id="history__wrapper" class="content__block">
            <table id="history" class="table">
                <%@ include file="jsp/table.jsp" %>
            </table>
        </div>
    </div>
    <div class="input-group row-flex">
        <input type="button" id="returnButton" class="return-button" value="Return" onclick="window.location.href = 'index.jsp';">
    </div>
</body>
</html>