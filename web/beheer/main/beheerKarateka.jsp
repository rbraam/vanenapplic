<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@page pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/jsp/taglibs.jsp" %>
<%@ page isELIgnored="false"%>
<tiles:importAttribute/>
<html:javascript formName="beheerKaratekaForm" staticJavascript="false"/>
<c:set var="form" value="${beheerKaratekaForm.map}"/>

<div class="beheerderPaginasContainer">
    <html:form action="/beheerKarateka" onsubmit="return validateBeheerKaratekaForm(this)">
        <h1><fmt:message key="beheer.karateka"/></h1>
        <c:if test="${not empty message}">
            <h3><c:out value="${message}"/></h3>
        </c:if>
        <c:if test="${not empty listKaratekas }">
            <div class="scrolldiv" style="height: 400px;">
                <table cellpadding="4" cellspacing="2" border="0">
                    <tr class="headRow">                        
                        <td><fmt:message key="beheer.karateka.achternaam"/></td>
                        <td><fmt:message key="beheer.karateka.voornaam"/></td>
                        <td><fmt:message key="beheer.karateka.tussenvoegsel"/></td>
                        <td><fmt:message key="beheer.karateka.band"/></td>
                        <td><fmt:message key="beheer.karateka.geslacht"/></td>
                        <td><fmt:message key="beheer.karateka.geboortedatum"/></td>
                        <%--td><fmt:message key="beheer.karateka.totaalpuntenka"/></td>
                        <td><fmt:message key="beheer.karateka.totaalpuntenku"/></td--%>
                    <tr>
                    <c:forEach items="${listKaratekas}" var="k" varStatus="status">
                        <c:url var="link" value="beheerKarateka.do?selectedId=${k.id}"/>
                        <c:set var="rowClass" value="oddRow"/>
                        <c:if test="${status.index%2 == 0}">
                            <c:set var="rowClass" value="evenRow"/>
                        </c:if>
                        <c:if test="${k.id==selectedId}">
                            <c:set var="rowClass" value="selectedRow"/>
                        </c:if>
                        <tr class="${rowClass}" onclick="javascript: window.location.href='${link}';">
                            <td><c:out value="${k.achternaam}"/></td> 
                            <td><c:out value="${k.voornaam}"/></td>
                            <td><c:out value="${k.tussenvoegsel}"/></td>
                            <td><c:out value="${k.band.band}"/></td>
                            <td><c:out value="${k.geslacht}"/></td>
                            <td><c:out value="${k.geboortedatumString}"/></td>
                            <%--td><c:out value="${k.totaalpuntenka}"/></td>
                            <td><c:out value="${k.totaalpuntenku}"/></td--%>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </c:if>
        <c:if test="${not empty selectedId || not empty doNew}">
            <html:hidden property="id"/>
            <table>
                <tr>
                    <td><fmt:message key="beheer.karateka.voornaam"/></td>
                    <td><html:text property="voornaam" size="80" /></td>
                </tr>
                <tr>
                    <td><fmt:message key="beheer.karateka.achternaam"/></td>
                    <td><html:text property="achternaam" size="80"/></td>
                </tr>
                <tr>
                    <td><fmt:message key="beheer.karateka.tussenvoegsel"/></td>
                    <td><html:text property="tussenvoegsel" size="80"/></td>
                </tr>
                <tr>
                    <td><fmt:message key="beheer.karateka.band"/></td>
                    <td>
                        <html:select property="band">
                            <c:forEach items="${banden}" var="b">
                                <html:option value="${b.id}"><c:out value="${b.band}"/></html:option>
                            </c:forEach>
                        </html:select>
                    </td>
                </tr>
                <tr>
                    <td><fmt:message key="beheer.karateka.geslacht"/></td>
                    <td>
                        <html:radio property="geslacht" value="Man">Man</html:radio>
                        <html:radio property="geslacht" value="Vrouw">Vrouw</html:radio>
                    </td>
                </tr>
                <tr>
                    <td><fmt:message key="beheer.karateka.geboortedatum"/></td>
                    <td><html:text property="geboortedatum" size="20"/>(dd-mm-jjjj)</td>
                </tr>
                <tr>
                    <td><fmt:message key="beheer.karateka.gewicht"/>(kg)</td>
                    <td><html:text property="gewicht" size="20"/></td>
                </tr>
                <tr>
                    <td><fmt:message key="beheer.karateka.beginpuntenka"/></td>
                    <td><html:text property="beginpuntenka" size="20"/></td>
                </tr>
                <tr>
                    <td><fmt:message key="beheer.karateka.beginpuntenku"/></td>
                    <td><html:text property="beginpuntenku" size="20"/></td>
                </tr>
                <tr>
                    <td><fmt:message key="beheer.karateka.totaalpuntenka"/></td>
                    <td><c:out value="${form.totaalpuntenka}"/></td>
                </tr>                
                <tr>
                    <td><fmt:message key="beheer.karateka.totaalpuntenku"/></td>
                    <td><c:out value="${form.totaalpuntenku}"/></td>
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
