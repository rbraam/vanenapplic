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
        <a href="#" onclick="javascript: window.print()">Print</a>
        <c:forEach varStatus="stat" items="${poules}" var="p">
            <c:if test="${stat.index!=0}">
                <div class="pageBreak">&nbsp;</div>
            </c:if>
            <table class="topTable">
                <tr>
                    <td>Datum: <c:out value="${p.vanencompetitie}"/></td>
                    <td><fmt:message key="printpoule.pouleformulier"/></td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td><fmt:message key="printpoule.sportschool"/></td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td>Poule: <c:out value="${p.poule}"/></td>
                    <td><fmt:message key="printpoule.organisator"/></td>
                    <td>SHIAO: </td>
                </tr>
            </table>
            <c:set var="aantal" value="${fn:length(p.karatekas)}"/>
             <table class="pouleTable" cellpadding="2" border="1">
                <c:forEach items="${p.karatekas}" var="k" varStatus="status">
                    <tr class="pouleRow">
                        <td class="karatekanaam">
                            <c:out value="${k}"/>
                        </td>
                        <!-- poule van 3-->
                        <c:if test="${aantal == 3}"> 
                            <c:if test="${status.index == 0}">
                                <td class="pouleRooster">
                                    <div class="roosterVak">1-2</div>
                                </td>
                                <td class="pouleRooster">
                                    <div class="roosterVak">1-3</div>
                                </td>
                                <td class="pouleClosed">
                                    &nbsp;
                                </td>
                                <td class="pouleRooster">
                                    <div class="roosterVak">1-2</div>
                                </td>
                                <td class="pouleRooster">
                                    <div class="roosterVak">1-3</div>
                                </td>
                                <td class="pouleClosed">
                                    &nbsp;
                                </td>
                                <td class="puntenTotaal">
                                    <div class="puntentotaalVak"><fmt:message key="printpoule.totaalpunten"/></div>
                                </td>
                            </c:if>
                            <c:if test="${status.index == 1}">
                                <td class="pouleRooster">
                                    <div class="roosterVak">2-1</div>
                                </td>
                                <td class="pouleClosed">
                                    &nbsp;
                                </td>
                                <td class="pouleRooster">
                                    <div class="roosterVak">2-3</div>
                                </td>
                                <td class="pouleRooster">
                                    <div class="roosterVak">2-1</div>
                                </td>
                                <td class="pouleClosed">
                                    &nbsp;
                                </td>
                                <td class="pouleRooster">
                                    <div class="roosterVak">2-3</div>
                                </td>
                                <td class="puntenTotaal">
                                    <div class="puntentotaalVak"><fmt:message key="printpoule.totaalpunten"/></div>
                                </td>
                                    
                            </c:if>
                            <c:if test="${status.index == 2}">
                                <td class="pouleClosed">
                                    &nbsp;
                                </td>
                                <td class="pouleRooster">
                                    <div class="roosterVak">3-1</div>
                                </td>
                                <td class="pouleRooster">
                                    <div class="roosterVak">3-2</div>
                                </td>
                                <td class="pouleClosed">
                                    &nbsp;
                                </td>
                                <td class="pouleRooster">
                                    <div class="roosterVak">3-1</div>
                                </td>
                                <td class="pouleRooster">
                                    <div class="roosterVak">3-2</div>
                                </td>                                
                                <td class="puntenTotaal">
                                    <div class="puntentotaalVak"><fmt:message key="printpoule.totaalpunten"/></div>
                                </td>
                            </c:if>                            
                        </c:if>
                        <!-- poule van 4-->
                        <c:if test="${aantal==4}">
                            <c:if test="${status.index == 0}">
                                <td class="pouleRooster">
                                    <div class="roosterVak">1-4</div>
                                </td>
                                <td class="pouleClosed">
                                    &nbsp;
                                </td>
                                <td class="pouleRooster">
                                    <div class="roosterVak">1-3</div>
                                </td>
                                <td class="pouleClosed">
                                    &nbsp;
                                </td>
                                <td class="pouleRooster">
                                    <div class="roosterVak">1-2</div>
                                </td>
                                <td class="pouleClosed">
                                    &nbsp;
                                </td>
                                <td class="extraPunten">
                                    <div class="roosterVak"><fmt:message key="printpoule.extrapunten"/></div>
                                    <div>20</div>
                                </td>
                                <td class="puntenTotaal">
                                    <div class="puntentotaalVak"><fmt:message key="printpoule.totaalpunten"/></div>
                                </td>
                            </c:if>
                            <c:if test="${status.index == 1}">
                                <td class="pouleClosed">
                                    &nbsp;
                                </td>
                                <td class="pouleRooster">
                                    <div class="roosterVak">2-3</div>
                                </td>
                                <td class="pouleClosed">
                                    &nbsp;
                                </td>
                                <td class="pouleRooster">
                                    <div class="roosterVak">2-4</div>
                                </td>
                                <td class="pouleRooster">
                                    <div class="roosterVak">2-1</div>
                                </td>
                                <td class="pouleClosed">
                                    &nbsp;
                                </td>
                                <td class="extraPunten">
                                    <div class="roosterVak"><fmt:message key="printpoule.extrapunten"/></div>
                                    <div>20</div>
                                </td>
                                <td class="puntenTotaal">
                                    <div class="puntentotaalVak"><fmt:message key="printpoule.totaalpunten"/></div>
                                </td>
                            </c:if>
                            <c:if test="${status.index == 2}">
                                <td class="pouleClosed">
                                    &nbsp;
                                </td>
                                <td class="pouleRooster">
                                    <div class="roosterVak">3-2</div>
                                </td>
                                <td class="pouleRooster">
                                    <div class="roosterVak">3-1</div>
                                </td>
                                <td class="pouleClosed">
                                    &nbsp;
                                </td>
                                <td class="pouleClosed">
                                    &nbsp;
                                </td>
                                <td class="pouleRooster">
                                    <div class="roosterVak">3-4</div>
                                </td>
                                <td class="extraPunten">
                                    <div class="roosterVak"><fmt:message key="printpoule.extrapunten"/></div>
                                    <div>20</div>
                                </td>
                                <td class="puntenTotaal">
                                    <div class="puntentotaalVak"><fmt:message key="printpoule.totaalpunten"/></div>
                                </td>
                            </c:if>
                            <c:if test="${status.index == 3}">
                                <td class="pouleRooster">
                                    <div class="roosterVak">4-1</div>
                                </td>
                                <td class="pouleClosed">
                                    &nbsp;
                                </td>   
                                <td class="pouleClosed">
                                    &nbsp;
                                </td>                             
                                <td class="pouleRooster">
                                    <div class="roosterVak">4-2</div>
                                </td>
                                <td class="pouleClosed">
                                    &nbsp;
                                </td>
                                <td class="pouleRooster">
                                    <div class="roosterVak">4-3</div>
                                </td>
                                <td class="extraPunten">
                                    <div class="roosterVak"><fmt:message key="printpoule.extrapunten"/></div>
                                    <div>20</div>
                                </td>
                                <td class="puntenTotaal">
                                    <div class="puntentotaalVak"><fmt:message key="printpoule.totaalpunten"/></div>
                                </td>
                            </c:if>
                        </c:if>                        
                        <!-- poule van 5-->
                        <c:if test="${aantal==5}">
                            <c:if test="${status.index == 0}">
                                <td class="pouleClosed">
                                    &nbsp;
                                </td>
                                <td class="pouleRooster">
                                    <div class="roosterVak">1-5</div>
                                </td>
                                <td class="pouleRooster">
                                    <div class="roosterVak">1-4</div>
                                </td>
                                <td class="pouleRooster">
                                    <div class="roosterVak">1-3</div>
                                </td>
                                <td class="pouleRooster">
                                    <div class="roosterVak">1-2</div>
                                </td>
                                <td class="pouleClosed">
                                    &nbsp;
                                </td> 
                                <td class="puntenTotaal">
                                    <div class="puntentotaalVak"><fmt:message key="printpoule.totaalpunten"/></div>
                                </td>

                            </c:if>
                            <c:if test="${status.index == 1}">
                                <td class="pouleRooster">
                                    <div class="roosterVak">2-5</div>
                                </td>
                                <td class="pouleRooster">
                                    <div class="roosterVak">2-4</div>
                                </td>
                                <td class="pouleRooster">
                                    <div class="roosterVak">2-3</div>
                                </td>
                                <td class="pouleClosed">
                                    &nbsp;
                                </td>
                                <td class="pouleRooster">
                                    <div class="roosterVak">2-1</div>
                                </td>
                                <td class="pouleClosed">
                                    &nbsp;
                                </td> 
                                <td class="puntenTotaal">
                                    <div class="puntentotaalVak"><fmt:message key="printpoule.totaalpunten"/></div>
                                </td>
                            </c:if>
                            <c:if test="${status.index == 2}">
                                <td class="pouleRooster">
                                    <div class="roosterVak">3-4</div>
                                </td>
                                <td class="pouleClosed">
                                    &nbsp;
                                </td>
                                <td class="pouleRooster">
                                    <div class="roosterVak">3-2</div>
                                </td>
                                <td class="pouleRooster">
                                    <div class="roosterVak">3-1</div>
                                </td>
                                <td class="pouleClosed">
                                    &nbsp;
                                </td> 
                                <td class="pouleRooster">
                                    <div class="roosterVak">3-5</div>
                                </td>
                                <td class="puntenTotaal">
                                    <div class="puntentotaalVak"><fmt:message key="printpoule.totaalpunten"/></div>
                                </td>
                            </c:if>
                            <c:if test="${status.index == 3}">
                                <td class="pouleRooster">
                                    <div class="roosterVak">4-3</div>
                                </td>
                                <td class="pouleRooster">
                                    <div class="roosterVak">4-2</div>
                                </td>
                                <td class="pouleRooster">
                                    <div class="roosterVak">4-1</div>
                                </td>
                                <td class="pouleClosed">
                                    &nbsp;
                                </td>
                                <td class="pouleRooster">
                                    <div class="roosterVak">4-5</div>
                                </td>
                                <td class="pouleClosed">
                                    &nbsp;
                                </td> 
                                <td class="puntenTotaal">
                                    <div class="puntentotaalVak"><fmt:message key="printpoule.totaalpunten"/></div>
                                </td>
                            </c:if>
                            <c:if test="${status.index == 4}">
                                <td class="pouleRooster">
                                    <div class="roosterVak">5-2</div>
                                </td>
                                <td class="pouleRooster">
                                    <div class="roosterVak">5-1</div>
                                </td>
                                <td class="pouleClosed">
                                    &nbsp;
                                </td>
                                <td class="pouleClosed">
                                    &nbsp;
                                </td>
                                <td class="pouleRooster">
                                    <div class="roosterVak">5-4</div>
                                </td>
                                <td class="pouleRooster">
                                    <div class="roosterVak">5-3</div>
                                </td>
                                <td class="puntenTotaal">
                                    <div class="puntentotaalVak"><fmt:message key="printpoule.totaalpunten"/></div>
                                </td>
                            </c:if>                            
                        </c:if>
                        <!--poule van 6 -->
                        <c:if test="${aantal==6}">
                            <c:if test="${status.index == 0}">
                                <td class="pouleRooster">
                                    <div class="roosterVak">1-2</div>
                                </td>
                                <td class="pouleRooster">
                                    <div class="roosterVak">1-3</div>
                                </td>
                                <td class="pouleRooster">
                                    <div class="roosterVak">1-5</div>
                                </td>
                                <td class="pouleClosed">
                                    &nbsp;
                                </td>
                                <td class="pouleRooster">
                                    <div class="roosterVak">1-6</div>
                                </td>
                                <td class="pouleRooster">
                                    <div class="roosterVak">1-4</div>
                                </td>
                                <td class="pouleClosed">
                                    &nbsp;
                                </td>
                                <td class="puntenTotaal">
                                    <div class="puntentotaalVak"><fmt:message key="printpoule.totaalpunten"/></div>
                                </td>
                            </c:if>
                            <c:if test="${status.index == 1}">
                                <td class="pouleRooster">
                                    <div class="roosterVak">2-1</div>
                                </td>
                                <td class="pouleRooster">
                                    <div class="roosterVak">2-5</div>
                                </td>
                                <td class="pouleClosed">
                                    &nbsp;
                                </td>
                                <td class="pouleRooster">
                                    <div class="roosterVak">2-4</div>
                                </td>
                                <td class="pouleClosed">
                                    &nbsp;
                                </td>
                                <td class="pouleRooster">
                                    <div class="roosterVak">2-3</div>
                                </td>
                                <td class="pouleRooster">
                                    <div class="roosterVak">2-6</div>
                                </td>
                                <td class="puntenTotaal">
                                    <div class="puntentotaalVak"><fmt:message key="printpoule.totaalpunten"/></div>
                                </td>
                            </c:if>
                            <c:if test="${status.index == 2}">
                                <td class="pouleRooster">
                                    <div class="roosterVak">3-4</div>
                                </td>
                                <td class="pouleRooster">
                                    <div class="roosterVak">3-1</div>
                                </td>
                                <td class="pouleClosed">
                                    &nbsp;
                                </td>
                                <td class="pouleRooster">
                                    <div class="roosterVak">3-6</div>
                                </td>
                                <td class="pouleClosed">
                                    &nbsp;
                                </td>
                                <td class="pouleRooster">
                                    <div class="roosterVak">3-2</div>
                                </td>
                                <td class="pouleRooster">
                                    <div class="roosterVak">3-5</div>
                                </td>
                                <td class="puntenTotaal">
                                    <div class="puntentotaalVak"><fmt:message key="printpoule.totaalpunten"/></div>
                                </td>
                            </c:if>
                            <c:if test="${status.index == 3}">
                                <td class="pouleRooster">
                                    <div class="roosterVak">4-3</div>
                                </td>
                                <td class="pouleClosed">
                                    &nbsp;
                                </td>
                                <td class="pouleRooster">
                                    <div class="roosterVak">4-6</div>
                                </td>
                                <td class="pouleRooster">
                                    <div class="roosterVak">4-2</div>
                                </td>
                                <td class="pouleRooster">
                                    <div class="roosterVak">4-5</div>
                                </td>
                                <td class="pouleRooster">
                                    <div class="roosterVak">4-1</div>
                                </td>
                                <td class="pouleClosed">
                                    &nbsp;
                                </td>
                                <td class="puntenTotaal">
                                    <div class="puntentotaalVak"><fmt:message key="printpoule.totaalpunten"/></div>
                                </td>
                            </c:if>
                            <c:if test="${status.index == 4}">
                                <td class="pouleRooster">
                                    <div class="roosterVak">5-6</div>
                                </td>
                                <td class="pouleRooster">
                                    <div class="roosterVak">5-2</div>
                                </td>
                                <td class="pouleRooster">
                                    <div class="roosterVak">5-1</div>
                                </td>
                                <td class="pouleClosed">
                                    &nbsp;
                                </td>
                                <td class="pouleRooster">
                                    <div class="roosterVak">5-4</div>
                                </td>
                                <td class="pouleClosed">
                                    &nbsp;
                                </td>
                                <td class="pouleRooster">
                                    <div class="roosterVak">5-3</div>
                                </td>
                                <td class="puntenTotaal">
                                    <div class="puntentotaalVak"><fmt:message key="printpoule.totaalpunten"/></div>
                                </td>
                            </c:if>
                            <c:if test="${status.index == 5}">
                                <td class="pouleRooster">
                                    <div class="roosterVak">6-5</div>
                                </td>
                                <td class="pouleClosed">
                                    &nbsp;
                                </td>
                                <td class="pouleRooster">
                                    <div class="roosterVak">6-4</div>
                                </td>
                                <td class="pouleRooster">
                                    <div class="roosterVak">6-3</div>
                                </td>
                                <td class="pouleRooster">
                                    <div class="roosterVak">6-1</div>
                                </td>
                                <td class="pouleClosed">
                                    &nbsp;
                                </td>
                                <td class="pouleRooster">
                                    <div class="roosterVak">6-2</div>
                                </td>
                                <td class="puntenTotaal">
                                    <div class="puntentotaalVak"><fmt:message key="printpoule.totaalpunten"/></div>
                                </td>
                            </c:if>
                        </c:if>
                    </tr>
                </c:forEach>
            </table>
            <br/>
            <div class="buttomText">
                Gewonnen wedstrijd = 20 punten.<br>
                Verloren wedstrijd = 10 punten.<br>
                <c:if test="${aantal==3}">
                    <b>Let op:</b> Bij drie deelnemers krijgen alle deelnemers 20 punten extra (de vierde partij).
                </c:if>
                <c:if test="${aantal==6}">
                    <b>Let op:</b> Bij zes deelnemers worden de vier beste resultaten geteld (het slechste resultaat vervalt).
                </c:if>
            </div>
            
        </c:forEach>
        
    </body>
</html>
