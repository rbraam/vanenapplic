<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@page pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/jsp/taglibs.jsp" %>
<%@ page isELIgnored="false"%>
<tiles:importAttribute/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<html:html>
    <head>
        <title><fmt:message key="index.title"/></title>
        <link href="<html:rewrite page='/styles/main.css' module='' />" rel="stylesheet" type="text/css">
        <script language="JavaScript" type="text/JavaScript" src="<html:rewrite page='/js/validation.jsp' module=''/>"></script>
    </head>
    <body>
        <tiles:insert page="${template}" flush="true">
            <tiles:put name="top"     value="${top}"/>
            <tiles:put name="topmenu" value="${topmenu}"/>
            <tiles:put name="menu"     value="${menu}"/>
            <tiles:put name="content"  value="${content}"/>
        </tiles:insert>
    </body>
</html:html>