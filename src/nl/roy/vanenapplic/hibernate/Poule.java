/*
 * Poule.java
 *
 * Created on 5 april 2007, 21:29
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package nl.roy.vanenapplic.hibernate;

/**
 *
 * @author Roy
 */
public class Poule {
    private Integer id;
    private String naam;
    private Categorie categorie;
    /** Creates a new instance of Poule */
    public Poule() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }
    
    public String toString(){
        if (getCategorie()!=null)
            return getCategorie().toString()+" "+getNaam();
        else 
            return "onbekend";
    }
    
}
