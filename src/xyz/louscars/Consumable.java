package xyz.louscars;

import java.io.*;
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

    public ArrayList deserialize(String fileName) throws IllegalArgumentException{
        File file = new File(fileName);
        if (!file.exists())
            throw new IllegalArgumentException("The file name isn't correct");

        try{
            FileInputStream fis = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);

            return (ArrayList) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
