package xyz.louscars;

import java.io.Serializable;
import java.util.ArrayList;

public class Consumable implements Serializable {

    private ArrayList<Malts> myMalts;
    private ArrayList<Hops> myHops;
    private ArrayList<Yeast> myYeast;

    public Consumable(){
        myMalts = new ArrayList<>();
        myHops = new ArrayList<>();
        myYeast = new ArrayList<>();
    }

    public ArrayList<Malts> getMyMalts() {
        return myMalts;
    }

    public ArrayList<Hops> getMyHops() {
        return myHops;
    }

    public ArrayList<Yeast> getMyYeast() {
        return myYeast;
    }

    public void addHops(Hops hops){
        myHops.add(hops);
    }

    public void addMalts(Malts malts){
        myMalts.add(malts);
    }

    public void addMalts(Yeast yeast){
        myYeast.add(yeast);
    }
}
