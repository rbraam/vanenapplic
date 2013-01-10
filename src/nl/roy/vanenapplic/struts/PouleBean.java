/*
 * PouleBean.java
 *
 * Created on 17 april 2007, 21:10
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package nl.roy.vanenapplic.struts;

import java.util.ArrayList;
import java.util.List;
import nl.roy.vanenapplic.hibernate.Karateka;
import nl.roy.vanenapplic.hibernate.Poule;
import nl.roy.vanenapplic.hibernate.Vanencompetitie;

/**
 *
 * @author Roy
 */
public class PouleBean {
    private String vanencompetitie;
    private String poule;
    private ArrayList karatekas=null;
    /** Creates a new instance of PouleBean */
    public PouleBean() {
    }

    public String getVanencompetitie() {
        return vanencompetitie;
    }

    public void setVanencompetitie(Vanencompetitie v) {
        StringBuffer sb= new StringBuffer();
        sb.append(v.getLokatie()+" ");
        sb.append(v.getDatumString()+" ");
        vanencompetitie=sb.toString();
    }

    public String getPoule() {
        return poule;
    }

    public void setPoule(Poule p) {
        StringBuffer sb= new StringBuffer();
        sb.append(p.getCategorie().getBand().getBand()+" ");
        sb.append(p.getCategorie().getLeeftijdvan()+" jaar t/m "+p.getCategorie().getLeeftijdtot()+" jaar ");
        sb.append(p.getNaam());
        poule=sb.toString();
    }

    public ArrayList getKaratekas() {
        return karatekas;
    }

    public void setKaratekas(ArrayList karatekas) {
        this.karatekas = karatekas;
    }
    
    public void addKarateka(Karateka k){
        String naam="";
        naam+=k.getVoornaam()+" ";
        if (k.getTussenvoegsel()!=null){
            naam+=k.getTussenvoegsel()+" ";
        }
        naam+=k.getAchternaam();
        if (karatekas==null){
            karatekas=new ArrayList();
        }
        karatekas.add(naam);
    }
           
}
