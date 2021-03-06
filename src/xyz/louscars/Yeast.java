package xyz.louscars;

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
}
