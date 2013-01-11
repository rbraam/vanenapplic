<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@page pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/jsp/taglibs.jsp" %>
<%@ page isELIgnored="false"%>
<tiles:importAttribute/>
<html:javascript formName="beheerCategorieForm" staticJavascript="false"/>
<c:set var="form" value="${beheerCategorieForm.map}"/>

<div class="beheerderPaginasContainer">
    <html:form action="/beheerCategorie" onsubmit="return validateBeheerCategorieForm(this)">
        <h1><fmt:message key="beheer.categorie"/></h1>
        <c:if test="${not empty message}">
            <h3><c:out value="${message}"/></h3>
        </c:if>
        <c:if test="${not empty listCategorien }">
            <div class="scrolldiv" style="height: 400px;">
                <table cellpadding="4" cellspacing="2" border="0">
                    <tr class="headRow">
                        <td><fmt:message key="beheer.categorie.band"/></td>
                        <td><fmt:message key="beheer.categorie.leeftijdvan"/></td>
                        <td><fmt:message key="beheer.categorie.leeftijdtot"/></td>
                    <tr>
                    <c:forEach items="${listCategorien}" var="c" varStatus="status">
                        <c:url var="link" value="beheerCategorie.do?selectedId=${c.id}"/>
                        <c:set var="cssClass" value="oddRow"/>
                        <c:if test="${status.index%2 == 0}">
                            <c:set var="cssClass" value="evenRow"/>
                        </c:if>
                        <c:if test="${c.id==selectedId}">
                            <c:set var="cssClass" value="selectedRow"/>
                        </c:if>
                        <tr class="${cssClass}" onclick="javascript: window.location.href='${link}';">
                            <td><c:out value="${c.band.band}"/></td> 
                            <td><c:out value="${c.leeftijdvan}"/></td>
                            <td><c:out value="${c.leeftijdtot}"/></td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </c:if>
        <c:if test="${not empty selectedId || not empty doNew}">
            <html:hidden property="id"/>
            <table>
                <tr>
                    <td><fmt:message key="beheer.categorie.band"/></td>
                    <td><html:select property="band">
                            <c:forEach items="${banden}" var="b">
                                <html:option value="${b.id}"><c:out value="${b.band}"/></html:option>
                            </c:forEach>
                        </html:select>
                    </td>
                </tr>
                <tr>
                    <td><fmt:message key="beheer.categorie.leeftijdvan"/></td>
                    <td><html:text property="leeftijdvan" size="80"/></td>
                </tr>
                <tr>
                    <td><fmt:message key="beheer.categorie.leeftijdtot"/></td>
                    <td><html:text property="leeftijdtot" size="80"/></td>
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
