package xyz.louscars;

import java.io.Serializable;

public class Yeast {
    private int tempMin;
    private int tempMax;
    private int attenuationApparente;

    public Yeast(int tempMin, int tempMax, int attenuationApparente){
        if (tempMin < tempMax){
            this.tempMin = tempMin;
            this.tempMax = tempMax;
        }

        if (attenuationApparente > 0 && attenuationApparente <= 100)
                this.attenuationApparente = attenuationApparente;
    }

    @Override
    public String toString() {
        return "Yeast{" +
                "tempMin=" + tempMin +
                ", tempMax=" + tempMax +
                ", attenuationApparente=" + attenuationApparente +
                '}';
    }
}
