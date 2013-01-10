/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.roy.vanenapplic.hibernate;

/**
 *
 * @author Roy
 */
public class Role {

    private Integer id;
    private String name;
    private String description;

    public static String SUPERBEHEERDER="superbeheerder";


    /** Creates a new instance of Role */
    public Role() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}

