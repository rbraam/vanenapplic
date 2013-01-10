<%@page pageEncoding="UTF-8"%>
<%@page contentType="text/javascript" %>
<%@page session="false"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<%-- Zorg ervoor dat deze pagina wordt gecached door de browser door een expires 
     header te zetten voor 24 uur na de huidige tijd. 
--%>
<% response.setDateHeader("Expires", System.currentTimeMillis() + (1000 * 60 * 60 * 24)); %>

<html:javascript dynamicJavascript="false" />