package xyz.beerocraft.model;

import java.io.*;
import java.util.ArrayList;

public class ConsumableOld {

    private final ArrayList<Malt> myMalts;
    private final ArrayList<Hop> myHops;
    private final ArrayList<Yeast> myYeast;

    public ConsumableOld() {

        File malts = new File("../Malts.ser");
        File hops = new File("../Hops.ser");
        File yeasts = new File("../Yeasts.ser");


        if (malts.exists())
            this.myMalts = deserialize("../Malts.ser");
        else
            this.myMalts = new ArrayList<>();

        if (hops.exists())
            this.myHops = deserialize("../Hops.ser");
        else
            this.myHops = new ArrayList<>();

        if (yeasts.exists())
            this.myYeast = deserialize("../Yeasts.ser");
        else
            this.myYeast = new ArrayList<>();
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

    public void addYeast(Yeast yeast) {
        myYeast.add(yeast);
    }

    public void serialize(ArrayList myConsumable, String fileName) throws IllegalArgumentException {

        if (!(fileName.equals("Malts.ser") || fileName.equals("Hops.ser") || fileName.equals("Yeasts.ser")))
            throw new IllegalArgumentException("the fileName isn't correct");

        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(myConsumable);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList deserialize(String fileName) throws IllegalArgumentException {


        try {
            FileInputStream fis = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);

            ArrayList al = (ArrayList) ois.readObject();
            ois.close();

            return al;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
