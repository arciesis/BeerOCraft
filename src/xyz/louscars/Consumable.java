package xyz.louscars;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Consumable {

    private ArrayList<Malt> myMalts;
    private ArrayList<Hop> myHops;
    private ArrayList<Yeast> myYeast;

    public Consumable() {
        myMalts = new ArrayList<>();
        myHops = new ArrayList<>();
        myYeast = new ArrayList<>();
    }

    public ArrayList<Malt> getMyMalts() {
        return myMalts;
    }

    public ArrayList<Hop> getMyHops() {
        return myHops;
    }

    public ArrayList<Yeast> getMyYeast() {
        return myYeast;
    }

    public void addHops(Hop hops) {
        myHops.add(hops);
    }

    public void addMalts(Malt malts) {
        myMalts.add(malts);
    }

    public void addMalts(Yeast yeast) {
        myYeast.add(yeast);
    }

    public void serialize(ArrayList myConsumable, String type) throws IllegalArgumentException {

        if (type.equals("Malts.ser") || type.equals("Hops.ser") || type.equals("Yeasts.ser")) {
            // do nothing
        } else {
            throw new IllegalArgumentException("the type isn't correct");
        }

        try {
            FileOutputStream fos = new FileOutputStream(type);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(myConsumable);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
