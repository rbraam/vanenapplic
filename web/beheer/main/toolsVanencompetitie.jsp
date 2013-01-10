<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@page pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/jsp/taglibs.jsp" %>
<%@ page isELIgnored="false"%>
<tiles:importAttribute/>

<c:set var="form" value="${toolsVanencompetitieForm.map}"/>
<div class="beheerderPaginasContainer">
    <h1><fmt:message key="beheer.toolsVanencompetitie"/></h1>
    <html:form action="/toolsVanencompetitie">
        Selecteer vanencompetitie:
        <select id="selectbox">
            <c:forEach items="${vanencompetities}" var="v">
                <option value="${v.id}"><c:out value="${v}"/></option>
            </c:forEach>
        </select>
        <br/>
        <div class="kolommen">
            <h2>Lijsten</h2>
            <a href="#" onclick="javascript: openlist('nietIngedeeldeKaratekas','')" onmouseover="javascript: this.style.color='#000000'" onmouseout="javascript: this.style.color=''" >Niet ingedeelde karateka's</a><br/><br/>
            <a href="#" onclick="javascript: openlist('deelnemendeKaratekas','achternaam')" onmouseover="javascript: this.style.color='#000000'" onmouseout="javascript: this.style.color=''" >Deelnemende Karateka's</a><br/><br/>
            <a href="#" onclick="javascript: openlist('deelnemendeKaratekas','band')" onmouseover="javascript: this.style.color='#000000'" onmouseout="javascript: this.style.color=''" >Deelnemende Karateka's gesorteerd op Band</a><br/><br/>
            <a href="#" onclick="javascript: openlist('deelnemendeKaratekas','geboortedatum DESC')" onmouseover="javascript: this.style.color='#000000'" onmouseout="javascript: this.style.color=''" >Deelnemende Karateka's gesorteerd op Leeftijd</a><br/><br/>
            <a href="#" onclick="javascript: openlist('deelnemendeKaratekas','band, i.karateka.geboortedatum DESC')" onmouseover="javascript: this.style.color='#000000'" onmouseout="javascript: this.style.color=''" >Deelnemende Karateka's gesorteerd op Band en Leeftijd</a><br/><br/>
            <a href="#" onclick="javascript: openlist('deelnemendeKaratekas','gewicht')" onmouseover="javascript: this.style.color='#000000'" onmouseout="javascript: this.style.color=''" >Deelnemende Karateka's gesorteerd op Gewicht</a><br/><br/>
            <a href="#" onclick="javascript: openlist('deelnemendeKaratekas','geboortedatum, i.karateka.gewicht DESC')" onmouseover="javascript: this.style.color='#000000'" onmouseout="javascript: this.style.color=''" >Deelnemende Karateka's gesorteerd op Leeftijd en Gewicht</a><br/><br/>
            <a href="#" onclick="javascript: openlist('ingedeeldePoules')" onmouseover="javascript: this.style.color='#000000'" onmouseout="javascript: this.style.color=''" >Overzicht ingedeelde poules</a><br/><br/>
        </div>
        <div class="kolommen">
            <h2>Poule Printen</h2> LET OP!: Er kunnen nog deelnemers zijn die niet ingedeeld zijn. Controleer dit eerst<br/>
            <a href="#" onclick="javascript: printAll()" onmouseover="javascript: this.style.color='#000000'" onmouseout="javascript: this.style.color=''" >Print alle poules</a><br/><br/>
            <a href="#" onclick="javascript: printAllBand(1)" onmouseover="javascript: this.style.color='#000000'" onmouseout="javascript: this.style.color=''" >Print alle poules 'wit'</a><br/><br/>
            <a href="#" onclick="javascript: printAllBand(2)" onmouseover="javascript: this.style.color='#000000'" onmouseout="javascript: this.style.color=''" >Print alle poules 'geel'</a><br/><br/>
            <a href="#" onclick="javascript: printAllBand(3)" onmouseover="javascript: this.style.color='#000000'" onmouseout="javascript: this.style.color=''" >Print alle poules 'oranje'</a><br/><br/>
            <a href="#" onclick="javascript: printAllBand(4)" onmouseover="javascript: this.style.color='#000000'" onmouseout="javascript: this.style.color=''" >Print alle poules 'groen'</a><br/><br/>
            <a href="#" onclick="javascript: printAllBand(5)" onmouseover="javascript: this.style.color='#000000'" onmouseout="javascript: this.style.color=''" >Print alle poules 'blauw'</a><br/><br/>
            <a href="#" onclick="javascript: printAllBand(6)" onmouseover="javascript: this.style.color='#000000'" onmouseout="javascript: this.style.color=''" >Print alle poules 'bruin3'</a><br/><br/>
            <a href="#" onclick="javascript: printAllBand(7)" onmouseover="javascript: this.style.color='#000000'" onmouseout="javascript: this.style.color=''" >Print alle poules 'bruin2'</a><br/><br/>
            <a href="#" onclick="javascript: printAllBand(8)" onmouseover="javascript: this.style.color='#000000'" onmouseout="javascript: this.style.color=''" >Print alle poules 'bruin1'</a><br/><br/>
        </div>
        <div class="kolommen">
            <h2>Oorkonden printen</h2>
            Selecteer een type oorkonde:            
            <select id="selectOorkondes">            
                <option value="0,100">A-klasse 0-100(Wit)</option>
                <option value="100,200">A-klasse 100-200(Wit)</option>
                <option value="200,300">A-klasse 200-300(Geel)</option>
                <option value="300,400">A-klasse 300-400(Oranje)</option>
                <option value="400,500">A-klasse 400-500(Groen)</option>
                <option value="500,600">A-klasse 500-600(Blauw)</option>
                <option value="600,700">A-klasse 600-700(Bruin)</option>
                <option value="700">A-klasse 700(zwart)</option>                
                <option value="700,800">B-klasse 0-100(Wit)</option>
                <option value="800,900">B-klasse 100-200(Wit)</option>
                <option value="900,1000">B-klasse 200-300(Geel)</option>
                <option value="1000,1100">B-klasse 300-400(Oranje)</option>
                <option value="1100,1200">B-klasse 400-500(Groen)</option>
                <option value="1200,1300">B-klasse 500-600(Blauw)</option>
                <option value="1300,1400">B-klasse 600-700(Bruin)</option>                
                <option value="1400">A-klasse 700(zwart)</option>      
                <option value="1400,1500">C-klasse 0-100(Wit)</option>
                <option value="1500,1600">C-klasse 100-200(Wit)</option>
                <option value="1600,1700">C-klasse 200-300(Geel)</option>
                <option value="1700,1800">C-klasse 300-400(Oranje)</option>
                <option value="1800,1900">C-klasse 400-500(Groen)</option>
                <option value="1900,2000">C-klasse 500-600(Blauw)</option>
                <option value="2000,2100">C-klasse 600-700(Bruin)</option>                
                <option value="2100">C-klasse 600-700(Bruin)</option> 
            </select>        
            <a href="#" onclick="javascript: printOorkondes()" onmouseover="javascript: this.style.color='#000000'" onmouseout="javascript: this.style.color=''" >Print de geselecteerde oorkondes.</a>
            <br/>
            <b>LET OP: De oorkondes moeten liggend worden afgedrukt! Dus stel de printer juist in.</b><br/>Dit kan u doen door in het afdruk scherm op de knop'voorkeursinstellingen' te klikken. Vervolgens op het tablad 'Eigenschappen' en daar liggend i.p.v. staand te kiezen. U komt in het afdrukscherm door 'CTRL+p' te toetsen of door naar bestand >> afdrukken te gaan. </br>
        </div>
    </html:form>
    <script language='javascript'>
        function openlist(list,sorteerOp){
            window.open('toolsVanencompetitie.do?'+list+'=t&sorteerOp='+sorteerOp+'&vanencompetitie='+document.getElementById('selectbox').value);
        }
        function printAll(){   
            if (document.getElementById("selectbox") && document.getElementById("selectbox").value.length > 0){
                window.open('printpoules.do?vanencompetitie='+document.getElementById("selectbox").value);
            }
            else{
                alert("Er is geen vanencompetitie geselecteerd");
            }       
        }
        function printAllBand(bandId){   
            if (document.getElementById("selectbox") && document.getElementById("selectbox").value.length > 0){
                window.open('printpoules.do?vanencompetitie='+document.getElementById("selectbox").value+'&band='+bandId);
            }
            else{
                alert("Er is geen vanencompetitie geselecteerd");
            }       
        }
        function printOorkondes(punten){   
            if (document.getElementById("selectbox") && document.getElementById("selectbox").value.length > 0){
                if (document.getElementById("selectOorkondes") && document.getElementById("selectOorkondes").value.length > 0){
                    window.open('printOorkondes.do?vanencompetitie='+document.getElementById("selectbox").value+'&punten='+document.getElementById("selectOorkondes").value);
                }
                else{
                    alert("Er is geen oorkonde groep geselecteerd");
                }
                
            }
            else{
                alert("Er is geen vanencompetitie geselecteerd");
            }       
        }        
    </script>
</div>