/*
 * Band.java
 *
 * Created on 26 maart 2007, 19:48
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package nl.roy.vanenapplic.hibernate;

/**
 *
 * @author Roy
 */
public class Band {
    
    private Integer id;
    private String band;
    /** Creates a new instance of Band */
    public Band() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBand() {
        return band;
    }

    public void setBand(String band) {
        this.band = band;
    }
    
}
