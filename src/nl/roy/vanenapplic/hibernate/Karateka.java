/*
 * Karateka.java
 *
 * Created on 25 maart 2007, 15:43
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package nl.roy.vanenapplic.hibernate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Roy
 */
public class Karateka{
    
    //Atributen
    private String voornaam;
    private String achternaam;
    private String tussenvoegsel;
    private Band band;
    private String geslacht;
    private Date geboortedatum;
    private double gewicht;
    private Integer id;
    private Integer beginpuntenku;
    private Integer beginpuntenka;
    private Integer totaalpuntenku;
    private Integer totaalpuntenka;
    //temp attribute voor het berekenen van de leeftijd
    
    private Date vanenDatum;
    /** Creates a new instance of Karateka */
    public Karateka() {
    }
    
    public String getVolledigeNaam(){
        StringBuffer sb=new StringBuffer();
        if (voornaam!=null){
            sb.append(voornaam);
        }
        if (tussenvoegsel!=null){
            sb.append(" "+tussenvoegsel);
        }
        if (achternaam !=null){
            sb.append(" "+achternaam);
        }
        return sb.toString();
    }
    
    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public Band getBand() {
        return band;
    }

    public void setBand(Band band) {
        this.band = band;
    }

    public String getGeslacht() {
        return geslacht;
    }

    public void setGeslacht(String geslacht) {
        this.geslacht = geslacht;
    }

    public Date getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(Date geboortedatum) {
        this.geboortedatum = geboortedatum;
    }
    
    public String getGeboortedatumString(){
        Date date=this.getGeboortedatum();
        if (date==null){
            return null;
        }
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String geboortedatum=df.format(date);
        return geboortedatum;
    }
    public double getGewicht() {
        return gewicht;
    }

    public void setGewicht(double gewicht) {
        this.gewicht = gewicht;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getVanenDatum() {
        return vanenDatum;
    }

    public void setVanenDatum(Date vanenDatum) {
        this.vanenDatum = vanenDatum;
    }
    public int getLeeftijd(){
        if (getVanenDatum()!=null){
            GregorianCalendar vanen= new GregorianCalendar();
            vanen.setTime(getVanenDatum());
            GregorianCalendar geb= new GregorianCalendar();
            geb.setTime(getGeboortedatum());
            int leeftijd= vanen.get(GregorianCalendar.YEAR)-geb.get(GregorianCalendar.YEAR);
            if (vanen.get(GregorianCalendar.MONTH)<geb.get(GregorianCalendar.MONTH)){
                leeftijd=leeftijd-1;
            }
            else if (vanen.get(GregorianCalendar.MONTH)==geb.get(GregorianCalendar.MONTH)){
                if (vanen.get(GregorianCalendar.DAY_OF_MONTH)<geb.get(GregorianCalendar.DAY_OF_MONTH)){
                    leeftijd=leeftijd-1;
                }
            }            
            return leeftijd;
        }else{
            return 0;
        }
    }
    
    public String toString(){
        //<c:out value="${k.voornaam}"/> <c:out value="${k.tussenvoegsel}"/> <c:out value="${k.achternaam}"/>. Band: <c:out value="${k.band.band}"/> (<c:out value="${k.leeftijd}"/>)<br>
        StringBuffer sb = new StringBuffer();
        sb.append(getAchternaam());
        sb.append(", ");
        if (getTussenvoegsel()!=null){
            sb.append(getTussenvoegsel());
            sb.append(" ");
        }
        sb.append(getVoornaam());
        sb.append(". Band: ");
        sb.append(getBand().getBand());
        sb.append(" ( ");
        sb.append(getLeeftijd());
        sb.append(")");
        if (getGewicht()>0){
            sb.append(" ("+getGewicht()+" kg).");
        }else{
            sb.append(".");
        }
        return sb.toString();
    }

    public Integer getBeginpuntenku() {
        return beginpuntenku;
    }

    public void setBeginpuntenku(Integer beginPunten) {
        this.beginpuntenku = beginPunten;
    }

    public Integer getTotaalpuntenku() {
        return totaalpuntenku;
    }

    public void setTotaalpuntenku(Integer totaalpuntenku) {
        this.totaalpuntenku = totaalpuntenku;
    }

    public Integer getTotaalpuntenka() {
        return totaalpuntenka;
    }

    public void setTotaalpuntenka(Integer totaalpuntenka) {
        this.totaalpuntenka = totaalpuntenka;
    }

    public Integer getBeginpuntenka() {
        return beginpuntenka;
    }

    public void setBeginpuntenka(Integer beginpuntenka) {
        this.beginpuntenka = beginpuntenka;
    }
    
    
}
