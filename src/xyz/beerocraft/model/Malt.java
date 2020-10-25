package xyz.beerocraft.model;

import java.io.Serializable;
import java.util.Objects;


public class Malt implements Serializable {

    private  static  final  long serialVersionUID = 18273654748474L;
    private String name;
    private int ebc;
    private int lovibond;
    private int potential;
    private String type;
    private static final String[] TYPE_POSSIBLE = {"grain","extrait","sucre","auxiliaire","autre"};

    public Malt(String name, int ebc, int lovibond, int potential, String type){
        if (name.length() < 24) {
            this.name = name;
            this.ebc = ebc;
            this.lovibond = lovibond;
            if (potential > 0 && potential <= 100)
                this.potential = potential;

            for (String item : TYPE_POSSIBLE) {
                if (item.trim().equalsIgnoreCase(type)) {
                    this.type = type;
                }
            }
        } else {
            System.out.println("The name is larger than expected");

        }
    }

    public String getName() {
        return name;
    }

    public int getEbc() {
        return ebc;
    }

    public int getLovibond() {
        return lovibond;
    }

    public int getPotential() {
        return potential;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Malts{" +
                "name='" + name + '\'' +
                ", ebc=" + ebc +
                ", lovibond=" + lovibond +
                ", potentiel=" + potential +
                ", type='" + type + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Malt)) return false;
        Malt malt = (Malt) o;
        return ebc == malt.ebc &&
                lovibond == malt.lovibond &&
                potential == malt.potential &&
                name.equals(malt.name) &&
                type.equals(malt.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, ebc, lovibond, potential, type);
    }
}
