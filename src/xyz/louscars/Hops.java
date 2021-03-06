package xyz.louscars;

public class Hops {

    private String name;
    private int alphaAcide;
    //private int uua;
    private String type;
    private static final String[] TYPE_POSSIBLE = {"cones" , "pellets", "pastilles"};

    public Hops(String name, int alphaAcide, String type){
        this.name = name;
        this.alphaAcide = alphaAcide;

        for (String item :
                TYPE_POSSIBLE) {
            if (item.trim().equalsIgnoreCase(type)){
                this.type = type;
            }
        }
    }

}
