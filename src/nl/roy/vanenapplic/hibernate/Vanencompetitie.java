/*
 * Vanencompetitie.java
 *
 * Created on 3 april 2007, 20:44
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package nl.roy.vanenapplic.hibernate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Roy
 */
public class Vanencompetitie {
    private Integer id;
    private Date datum;
    private String lokatie;
    private String type;
    public static String TYPE_KUMITE="kumite";
    public static String TYPE_KATA="kata";
    /** Creates a new instance of Vanencompetitie */
    public Vanencompetitie() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public String getLokatie() {
        return lokatie;
    }

    public void setLokatie(String lokatie) {
        this.lokatie = lokatie;
    }
     public String getDatumString(){
        Date date=this.getDatum();
        if (date==null){
            return null;
        }
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String datum=df.format(date);
        return datum;
    }
    public String toString(){
        StringBuffer sb= new StringBuffer();
        if (getDatumString()!=null){
            sb.append(getDatumString());
            sb.append(" ");
        }
        if (getLokatie()!=null){
            sb.append(getLokatie());            
        }
        if (getType()!=null){
            sb.append(" ("+getType()+")");
        }
       return sb.toString();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
