<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@page pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/jsp/taglibs.jsp" %>
<%@ page isELIgnored="false"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head> 
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lijst</title>
        <link href="<html:rewrite page='/styles/printpoule.css' module='' />" rel="stylesheet" type="text/css">
    </head>
    <body>
        <h1><c:out value="${vanencompetitie}"/></h1>
        <c:forEach items="${list}" var="l">
            ${l}<br/>
        </c:forEach>
        
    </body>
</html>
