<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@page pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/jsp/taglibs.jsp" %>
<%@ page isELIgnored="false"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head> 
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Poules</title>
        <link href="<html:rewrite page='/styles/printpoule.css' module='' />" rel="stylesheet" type="text/css">
    </head>
    <body>
        <html:messages id="error" message="true">
            <div class="errormessages">
                <c:out value="${error}" escapeXml="false"/>
            </div>
        </html:messages>
        <c:if test="${fn:length(oorkondeList)==0}">
            Geen karateka's gevonden voor dit type oorkonde.
        </c:if>
        <c:forEach varStatus="stat" items="${oorkondeList}" var="k">
            <c:if test="${stat.index!=0}">
                <div class="pageBreak">&nbsp;</div>
            </c:if>
            <div class="oorkondeWhiteSpace">
                &nbsp;
            </div>
            <div class="oorkondeNaam">
                <table width="100%" height="100%">
                    <tr><td>
                        <c:out value="${k.volledigeNaam}"/>
                    </td></tr>
                </table>
            </div>            
            <div class="oorkondePunten">
                <c:if test="${vanen.type=='kumite'}">
                    <c:out value="${k.totaalpuntenku}"/>
                </c:if>
                <c:if test="${vanen.type=='kata'}">
                    <c:out value="${k.totaalpuntenka}"/>
                </c:if>
            </div>     
            <div class="oorkondeDatum">
                <c:out value="${vanen.datumString}"/>
            </div>
        </c:forEach>
    </body>
</html>
