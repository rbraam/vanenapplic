<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@page pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/jsp/taglibs.jsp" %>
<%@ page isELIgnored="false"%>
<tiles:importAttribute/>




<ul id="menu">
    <li>
        <div><a href="http://www.jvdhoofdakker.nl">www.jvdhoofdakker.nl</a></div>
    </li>
    <li>
        <div><a href="index.do"><fmt:message key="menu.home"/></a></div>
    </li>    
    <li>
        <div><a href="beheer/beheer.do"><fmt:message key="menu.beheer"/></a></div>
    </li>
</ul>
