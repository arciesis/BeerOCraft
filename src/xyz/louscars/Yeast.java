package xyz.louscars;

import java.io.Serializable;
import java.util.Objects;

public class Yeast implements Serializable {

    private  static  final  long serialVersionUID = 129874987598745L;
    private int tempMin;
    private int tempMax;
    private int apparentAttenuation;

    public Yeast(int tempMin, int tempMax, int apparentAttenuation){
        if (tempMin < tempMax){
            this.tempMin = tempMin;
            this.tempMax = tempMax;
        }

        if (apparentAttenuation > 0 && apparentAttenuation <= 100)
                this.apparentAttenuation = apparentAttenuation;
    }

    @Override
    public String toString() {
        return "Yeast{" +
                "tempMin=" + tempMin +
                ", tempMax=" + tempMax +
                ", attenuationApparente=" + apparentAttenuation +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Yeast)) return false;
        Yeast yeast = (Yeast) o;
        return tempMin == yeast.tempMin &&
                tempMax == yeast.tempMax &&
                apparentAttenuation == yeast.apparentAttenuation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tempMin, tempMax, apparentAttenuation);
    }
}
