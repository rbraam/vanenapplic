<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@page pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/jsp/taglibs.jsp" %>
<%@ page isELIgnored="false"%>
<tiles:importAttribute/>
<html:javascript formName="beheerPouleForm" staticJavascript="false"/>
<c:set var="form" value="${beheerPouleForm.map}"/>

<div class="beheerderPaginasContainer">
    <html:form action="/beheerPoule" onsubmit="return validateBeheerPouleForm(this)">
        <h1><fmt:message key="beheer.poule"/></h1>
        <c:if test="${not empty message}">
            <h3><c:out value="${message}"/></h3>
        </c:if>
        <c:if test="${not empty listVanencompetities}">
            <c:choose>
                <c:when test="${empty selectedId && empty doNew}">
                    <html:select styleId="searchVanencompetitie" property="searchVanencompetitie" onchange="document.forms[0].submit()">
                        <html:option value="">Kies vanencompetitie</html:option>
                        <c:forEach items="${listVanencompetities}" var="v">
                            <html:option value="${v.id}"><c:out value="${v}"/></html:option>
                        </c:forEach>
                    </html:select>
                </c:when>
                <c:otherwise>
                    <html:hidden property="searchVanencompetitie"/>
                    <c:forEach items="${listVanencompetities}" var="v">
                        <c:if test="${v.id == form.searchVanencompetitie}">
                            Vanencompetitie: <c:out value="${v}"/>
                        </c:if>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </c:if>
        <c:if test="${not empty listCategorien}">            
            <c:choose>
                <c:when test="${empty selectedId && empty doNew}">
                    <html:select property="searchCategorie" onchange="document.forms[0].submit()">
                        <html:option value="">Alle categorieen</html:option>
                        <c:forEach items="${listCategorien}" var="c">
                            <html:option value="${c.id}"><c:out value="${c.band.band}"/>, <c:out value="${c.leeftijdvan}"/> t/m <c:out value="${c.leeftijdtot}"/></html:option>
                        </c:forEach>
                    </html:select>
                </c:when>
                <c:otherwise>
                    <html:hidden property="searchCategorie"/>
                    <c:forEach items="${listCategorien}" var="c">
                        <c:if test="${c.id == form.searchCategorie}">
                            Categorie: <c:out value="${c.band.band}"/>, <c:out value="${c.leeftijdvan}"/> t/m <c:out value="${c.leeftijdtot}"/>
                        </c:if>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </c:if>
        <c:if test="${not empty listPoules }">
            <div class="scrolldiv" style="height: 435px; width: 400px; float:left; clear: none;">
                <table cellpadding="4" cellspacing="2" border="0">
                    <tr class="headRow">
                    <td><fmt:message key="beheer.poule.naam"/></td>
                    <td><fmt:message key="beheer.poule.categorie"/></td>
                    <tr>
                    <c:forEach items="${listPoules}" var="p" varStatus="status">
                        <c:url var="link" value="beheerPoule.do?selectedId=${p.id}"/>
                        <c:set var="class" value="oddRow"/>
                        <c:if test="${status.index%2 == 0}">
                            <c:set var="class" value="evenRow"/>
                        </c:if>
                        <c:if test="${p.id==selectedId}">
                            <c:set var="class" value="selectedRow"/>
                        </c:if>
                        <tr class="${class}" onclick="javascript: window.location.href='${link}';">
                            <td><c:out value="${p.naam}"/></td>
                            <td><c:out value="${p.categorie.band.band}"/>, <c:out value="${p.categorie.leeftijdvan}"/> t/m <c:out value="${p.categorie.leeftijdtot}"/></td>                  
                        </tr>
                    </c:forEach>
                </table>
            </div>            
        </c:if>
        <c:if test="${not empty listNietIngedeelde }">
            <div style="float: right;  clear: right;"><h2>Niet ingedeeld: </h2>
                <div class="scrolldiv" style="height: 400px; width:300px;">
                    <c:forEach items="${listNietIngedeelde}" var="k" varStatus="status">
                        <c:out value="${k}"/><br>
                    </c:forEach>
                </div>
            </div>
        </c:if>
        <c:if test="${not empty selectedId || not empty doNew}">
            <html:hidden property="id"/>
            <table>
                <tr>
                    <td><fmt:message key="beheer.poule.naam"/></td>
                    <td><html:text property="naam" size="80" /></td>
                </tr>
                <c:if test="${not empty selectedCategorie}">
                    <tr>
                        <td><fmt:message key="beheer.poule.categorie"/></td>
                        <td><html:hidden property="categorie" value="${selectedCategorie.id}"/><c:out value="${selectedCategorie.band.band}"/>, <c:out value="${selectedCategorie.leeftijdvan}"/> t/m <c:out value="${selectedCategorie.leeftijdtot}"/></td>
                    </tr>
                </c:if>
                <tr>
                    <td><fmt:message key="beheer.poule.karatekas"/></td>
                    <td>
                        <div class="scrolldiv" style="width: 400px;">
                            <c:forEach var="karateka" items="${listKaratekas}">
                                <html:multibox property="karatekas" value="${karateka.id}"></html:multibox><c:out value="${karateka.karateka.achternaam}"/>, <c:out value="${karateka.karateka.voornaam}"/>(<c:out value="${karateka.karateka.leeftijd}"/>)<br>
                            </c:forEach>
                        </div>
                    </td>
                </tr>
            </table>
            <c:if test="${not empty selectedId}"><div><a href="#" onclick="javascript: printCurrent()">Print huidige poule</a></div></c:if>           
        </c:if><br/>
        <div><a href="#" onclick="javascript: printAll()">Print alle poules van deze vanencompetitie</a></div>
        <c:if test="${empty selectedId  && empty doNew}">
            <html:submit property="create" styleClass="knop" onclick="bCancel=true">
                <fmt:message key="button.new"/>
            </html:submit>
        </c:if>
        <c:if test="${not empty doNew || not empty selectedId }">
            <html:submit property="save" styleClass="knop">
                <fmt:message key="button.save"/>
            </html:submit>
        </c:if>
        <c:if test="${empty doNew && not empty selectedId }">
            <html:submit property="delete" styleClass="knop">
                <fmt:message key="button.remove"/>
            </html:submit>
        </c:if>
        <c:if test="${not empty doNew || not empty selectedId }">
            <html:submit property="cancel" styleClass="knop" onclick="bCancel=true">
                <fmt:message key="button.cancel"/>
            </html:submit>
        </c:if>
    </html:form>
</div>

<script>
    function printAll(){     
            if (document.getElementById("searchVanencompetitie") && document.getElementById("searchVanencompetitie").value.length > 0){
                window.open('printpoules.do?vanencompetitie='+document.getElementById("searchVanencompetitie").value);
            }
            else{
                alert("Er is geen vanencompetitie geselecteerd");
            }       
    }
    function printCurrent(){
        <c:if test="${not empty form.searchVanencompetitie}">
            window.open('printpoules.do?poules=${form.id}');
        </c:if>            
    }
</script>