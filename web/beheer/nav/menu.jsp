<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@page pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/jsp/taglibs.jsp" %>
<%@ page isELIgnored="false"%>
<tiles:importAttribute/>

<ul id="menu">
   <li>
       <div><a href="../index.do">Home</a></div>
   </li>
   <li>
       <div><a href="beheerKarateka.do"><fmt:message key="beheer.karateka"/></a></div>
   </li>
   <li>
       <div><a href="beheerCategorie.do"><fmt:message key="beheer.categorie"/></a></div>
   </li>
  <li>
       <div><a href="beheerVanencompetitie.do"><fmt:message key="beheer.vanencompetitie"/></a></div>
   </li>
   <li>
       <div><a href="beheerPoule.do"><fmt:message key="beheer.poule"/></a></div>
   </li>
   <li>
       <div><a href="beheerPunten.do"><fmt:message key="beheer.punten"/></a></div>
   </li>
   <li>
       <div><a href="toolsVanencompetitie.do"><fmt:message key="beheer.toolsVanencompetitie"/></a></div>
   </li>
</ul>