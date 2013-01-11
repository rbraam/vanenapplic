<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@page pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/jsp/taglibs.jsp" %>
<%@ page isELIgnored="false"%>
<tiles:importAttribute/>
<html:javascript formName="beheerVanencompetitieForm" staticJavascript="false"/>
<c:set var="form" value="${beheerVanencompetitieForm.map}"/>

<div class="beheerderPaginasContainer">
    <html:form action="/beheerVanencompetitie" onsubmit="return validateBeheerVanencompetitieForm(this)">
        <h1><fmt:message key="beheer.vanencompetitie"/></h1>
        <c:if test="${not empty message}">
            <h3><c:out value="${message}"/></h3>
        </c:if>
        <c:if test="${not empty listVanencompetities }">
            <div class="scrolldiv" style="height: 200px;">
                <table cellpadding="4" cellspacing="2" border="0">
                    <tr class="headRow">
                        <td><fmt:message key="beheer.vanencompetitie.lokatie"/></td>
                        <td><fmt:message key="beheer.vanencompetitie.datum"/></td>                        
                        <td><fmt:message key="beheer.vanencompetitie.type"/></td>
                    <tr>
                    <c:forEach items="${listVanencompetities}" var="v" varStatus="status">
                        <c:url var="link" value="beheerVanencompetitie.do?selectedId=${v.id}"/>
                        <c:set var="cssClass" value="oddRow"/>
                        <c:if test="${status.index%2 == 0}">
                            <c:set var="cssClass" value="evenRow"/>
                        </c:if>
                        <c:if test="${v.id==selectedId}">
                            <c:set var="cssClass" value="selectedRow"/>
                        </c:if>
                        <tr class="${cssClass}" onclick="javascript: window.location.href='${link}';">
                            <td><c:out value="${v.lokatie}"/></td>
                            <td><c:out value="${v.datumString}"/></td>                             
                            <td><c:out value="${v.type}"/></td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </c:if>
        <c:if test="${not empty selectedId || not empty doNew}">
            <html:hidden property="id"/>
            <table>
                <tr>
                    <td><fmt:message key="beheer.vanencompetitie.lokatie"/></td>
                    <td><html:text property="lokatie" size="80" /></td>
                </tr>
                <tr>
                    <td><fmt:message key="beheer.vanencompetitie.datum"/></td>
                    <td><html:text property="datum" size="80"/>dd-mm-jjjj)</td>
                </tr>
                <tr>
                    <td><fmt:message key="beheer.vanencompetitie.type"/></td>
                    <td>
                        <html:select property="type">
                            <html:option value="kata">Kata</html:option>
                            <html:option value="kumite">Kumite</html:option>
                        </html:select>
                    </td>
                </tr>
                <tr>
                    <td><fmt:message key="beheer.vanencompetitie.karatekas"/></td>
                    <td>
                        <div class="scrolldiv" style="width: 400px; height: 400px">
                            <c:forEach var="karateka" items="${listKaratekas}">
                                <html:multibox property="karatekas" value="${karateka.id}"></html:multibox><c:out value="${karateka.achternaam}"/>, <c:out value="${karateka.voornaam}"/>(<c:out value="${karateka.leeftijd}"/>)<br>
                            </c:forEach>
                        </div>
                    </td>
                </tr>
                
            </table>
            
        </c:if>
        <c:if test="${empty selectedId && empty doNew}">
            <html:submit property="create" styleClass="knop" onclick="bCancel=true">
                <fmt:message key="button.new"/>
            </html:submit>
        </c:if>
        <c:if test="${not empty doNew || not empty selectedId}">
            <html:submit property="save" styleClass="knop">
                <fmt:message key="button.save"/>
            </html:submit>
        </c:if>
        <c:if test="${empty doNew && not empty selectedId}">
            <html:submit property="delete" styleClass="knop">
                <fmt:message key="button.remove"/>
            </html:submit>
        </c:if>
        <c:if test="${not empty doNew || not empty selectedId}">
            <html:submit property="cancel" styleClass="knop" onclick="bCancel=true">
                <fmt:message key="button.cancel"/>
            </html:submit>
        </c:if>
    </html:form>
</div>
