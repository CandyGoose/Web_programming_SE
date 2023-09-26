<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<jsp:useBean id="results" scope="application" type="java.util.List"/>
<tr>
    <th>Request time</th>
    <th>Execute time</th>
    <th>Data (x,y,r)</th>
    <th>Result</th>
</tr>

<%@ page import="java.text.SimpleDateFormat" %>
<%
    SimpleDateFormat dateFormatWithTime = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy");
    session.setAttribute("dateFormat", dateFormatWithTime);
%>

<c:forEach var="result" items="${results}">
    <tr data-x="${result.coordinates.x}" data-y="${result.coordinates.y}">
        <td>${dateFormat.format(result.currTime)}</td>
        <td>${result.execTime} ms</td>
        <td>${result.coordinates}</td>
        <td>${result.result}</td>
    </tr>
</c:forEach>
