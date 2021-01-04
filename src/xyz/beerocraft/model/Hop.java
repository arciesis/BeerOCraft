/**
 * @author Arciesis https://github.com/arciesis/BeerOCraft/
 */

package xyz.beerocraft.model;

import java.io.Serializable;
import java.util.Objects;


public class Hop implements Serializable {

    /**
     * UUID for serialisation
     * @deprecated
     */
    private  static  final  long serialVersionUID = 19874398745985L;

    /**
     * the name of the Hop
     */
    private String name;

    /**
     * The amount of alpha acid of the hop
     */
    private int alphaAcide;

    //private int uua;

    /**
     * The type of hop
     */
    private String type;

    /**
     * The possible type of a hop
     */
    private static final String[] TYPE_POSSIBLE = {"cones" , "pellets", "pastilles"};


    /**
     * Constructor of the class
     * @param name the name of the hop
     * @param alphaAcide the amount of alpha acid of the hop
     * @param type the type of the hop
     */
    public Hop(String name, int alphaAcide, String type){
        this.name = name;
        this.alphaAcide = alphaAcide;

        for (String item :
                TYPE_POSSIBLE) {
            if (item.trim().equalsIgnoreCase(type)){
                this.type = type;
            }
        }
    }

    /**
     * @return the name of the hop
     */
    public String getName() {
        return name;
    }

    /**
     * @return the amountof alpha acid of the hop
     */
    public int getAlphaAcide() {
        return alphaAcide;
    }


    /**
     * @return the type of the hop
     */
    public String getType() {
        return type;
    }

    /**
     * @return the string that contains all the information of the Hop
     */
    @Override
    public String toString() {
        return "Hops{" +
                "name='" + name + '\'' +
                ", alphaAcide=" + alphaAcide +
                ", type='" + type + '\'' +
                '}';
    }

    /**
     * Indicate if the object in parameter is equals to the hop wich is called by
     * @param o the object to test
     * @return true if the object is equal to the hop else return false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hop)) return false;
        Hop hop = (Hop) o;
        return alphaAcide == hop.alphaAcide &&
                name.equals(hop.name) &&
                type.equals(hop.type);
    }

    /**
     * @return the hashcode of the hop
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, alphaAcide, type);
    }
}
