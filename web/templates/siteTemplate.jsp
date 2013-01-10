<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@page pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/jsp/taglibs.jsp" %>
<%@ page isELIgnored="false"%>
<tiles:importAttribute/>

<div id="bodyContent">
    <div id="top">
        <tiles:get name='top'/>           
    </div>
    <div id="leftMenu">
        <div id="menuItems">
            <tiles:get name='menu'/>
        </div>
    </div>    
    <div id="content">      
         <tiles:get name='content'/>
     </div>
</div>
