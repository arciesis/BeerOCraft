package xyz.louscars;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Objects;

public class Hop implements Serializable {

    private  static  final  long serialVersionUID = 19874398745985L;
    private String name;
    private int alphaAcide;
    //private int uua;
    private String type;
    private static final String[] TYPE_POSSIBLE = {"cones" , "pellets", "pastilles"};

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


//    private  void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
//        this.name = ois.readUTF();
//        this.alphaAcide = ois.readInt();
//        this.type = ois.readUTF();
//    }


    @Override
    public String toString() {
        return "Hops{" +
                "name='" + name + '\'' +
                ", alphaAcide=" + alphaAcide +
                ", type='" + type + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hop)) return false;
        Hop hop = (Hop) o;
        return alphaAcide == hop.alphaAcide &&
                name.equals(hop.name) &&
                type.equals(hop.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, alphaAcide, type);
    }
}
