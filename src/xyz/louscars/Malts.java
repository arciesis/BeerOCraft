package xyz.louscars;

public class Malts {
    private String name;
    private int ebc;
    private int lovibond;
    private int potentiel;
    private String type;
    private static final String[] TYPE_POSSIBLE = {"grain","extrait","sucre","auxiliaire","autre"};

    public Malts(String name, int ebc, int lovibond, int potentiel, String type){
        this.name = name;
        this.ebc = ebc;
        this.lovibond = lovibond;
        this.potentiel = potentiel;

        for (String item : TYPE_POSSIBLE) {
            if (item.trim().equalsIgnoreCase(type)) {
                this.type = type;
            }
        }
    }

}
