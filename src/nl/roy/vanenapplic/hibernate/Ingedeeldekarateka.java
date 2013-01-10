/*
 * Ingedeeldekarateka.java
 *
 * Created on 3 april 2007, 20:45
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package nl.roy.vanenapplic.hibernate;

/**
 *
 * @author Roy
 */
public class Ingedeeldekarateka {
    private Karateka karateka;
    private Vanencompetitie vanencompetitie;
    private Poule poule;
    private Integer id;
    private Integer punten;
    private boolean betrouwbarepunten;
    
    /** Creates a new instance of Ingedeeldekarateka */
    public Ingedeeldekarateka() {
    }

    public Karateka getKarateka() {
        return karateka;
    }

    public void setKarateka(Karateka karateka) {
        this.karateka = karateka;
    }

    public Vanencompetitie getVanencompetitie() {
        return vanencompetitie;
    }

    public void setVanencompetitie(Vanencompetitie vanencompetitie) {
        this.vanencompetitie = vanencompetitie;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Poule getPoule() {
        return poule;
    }

    public void setPoule(Poule poule) {
        this.poule = poule;
    }
    
    public String toString(){
        StringBuffer sb= new StringBuffer();
        sb.append(getKarateka().toString());
        if (getPoule()!=null)
            sb.append(getPoule().toString());
        return sb.toString();
    
    }

    public Integer getPunten() {
        return punten;
    }

    public void setPunten(Integer punten) {
        this.punten = punten;
    }

    public boolean isBetrouwbarepunten() {
        return betrouwbarepunten;
    }

    public void setBetrouwbarepunten(boolean betrouwbarepunten) {
        this.betrouwbarepunten = betrouwbarepunten;
    }
}
