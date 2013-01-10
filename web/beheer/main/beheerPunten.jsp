<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@page pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/jsp/taglibs.jsp" %>
<%@ page isELIgnored="false"%>
<tiles:importAttribute/>
<html:javascript formName="beheerPuntenForm" staticJavascript="false"/>
<c:set var="form" value="${beheerPuntenForm.map}"/>

        <div class="beheerderPaginasContainer">
            <html:form action="/beheerPunten" onsubmit="return validateBeheerPuntenForm(this)">
            <h1><fmt:message key="beheer.punten"/></h1>
            <table>
                <tr>
                    <td><fmt:message key="beheer.punten.vanen"/></td>
                    <td>
                        <html:select property="vanencompetitie">                            
                            <c:forEach var="v" items="${vanenList}">
                                <html:option value="${v.id}">${v}</html:option>                            
                            </c:forEach>
                        </html:select>
                    </td>
                    <td>
                        <html:submit property="unspecified" styleClass="knop">
                            Kies
                        </html:submit>
                    </td>
                </tr>
            </table>
            <div class="scrolldiv" style="height: 600px;">
            <table>
                <c:set var="huidigePoule" value=""/>
                <c:forEach var="i" items="${ingedeeldekaratekaList}">                
                    <c:if test="${huidigePoule!=i.poule}">
                        <c:choose>
                            <c:when test="${i.poule==''}">
                                Niet ingedeelde:
                            </c:when>
                            <c:otherwise>
                                <tr><td><b><c:out value="${i.poule}"/></b></td></tr>
                            </c:otherwise>
                        </c:choose>                   
                        
                        <c:set var="huidigePoule" value="${i.poule}"/>
                    </c:if>
                    <tr>                    
                        <td>
                            <c:out value="${i.karateka}"/>
                        </td>
                        <td>
                            <input type="text" size="10" onchange="javascript: setNewPoints('${i.id}',this.value)" value="${i.punten}"></input>
                            <html:hidden styleId="ingedeeldekarateka${i.id}" value="" property="punten"/>
                        </td>
                        <td>
                            <c:if test="${!i.betrouwbarepunten}">
                                Waarschijnlijk zijn de punten bijgestelt omdat de karateka anders boven het maximale aantal punten komt van de klasse (700).
                            </c:if>
                        </td>
                    </tr>
                    
                </c:forEach>
            </table>
        </div>
        <html:submit property="save" styleClass="knop">
            <fmt:message key="button.save"/>
        </html:submit>
    </html:form>
</div>
<script>
    function setNewPoints(id,value){
        document.getElementById("ingedeeldekarateka"+id).value=id+","+value;
    }
</script>