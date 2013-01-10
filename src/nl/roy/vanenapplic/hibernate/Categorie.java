/*
 * Categorie.java
 *
 * Created on 28 maart 2007, 21:24
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package nl.roy.vanenapplic.hibernate;

/**
 *
 * @author Roy
 */
public class Categorie {
    private Integer id;
    private Band band;
    private Integer leeftijdvan;
    private Integer leeftijdtot;
    /** Creates a new instance of Categorie */
    public Categorie() {
    }

    public Band getBand() {
        return band;
    }

    public void setBand(Band band) {
        this.band = band;
    }

    public Integer getLeeftijdvan() {
        return leeftijdvan;
    }

    public void setLeeftijdvan(Integer leeftijdvan) {
        this.leeftijdvan = leeftijdvan;
    }

    public Integer getLeeftijdtot() {
        return leeftijdtot;
    }

    public void setLeeftijdtot(Integer leeftijdtot) {
        this.leeftijdtot = leeftijdtot;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String toString(){
        StringBuffer sb= new StringBuffer();
        if (getBand()!=null){
            sb.append(getBand().getBand());
            sb.append(" ");            
        }
        if (getLeeftijdvan()!=null && getLeeftijdtot() != null){
            sb.append("van ");
            sb.append(getLeeftijdvan());
            sb.append(" tot ");
            sb.append(getLeeftijdtot());            
        }        
        return sb.toString();        
    }
    
}
