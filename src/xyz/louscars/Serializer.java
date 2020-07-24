package xyz.louscars;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Serializer {

    public static void serializeMalts(ArrayList<Malts> myMalts){
        try{
            FileOutputStream fosMalts = new FileOutputStream("Malts.bat");
            ObjectOutputStream oos = new ObjectOutputStream(fosMalts);

            oos.writeObject(myMalts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void serializeHops(ArrayList<Hops> myHops){
        try {
            FileOutputStream fosHops = new FileOutputStream("Hops.bat");
            ObjectOutputStream oos = new ObjectOutputStream(fosHops);

            oos.writeObject(myHops);
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    public static void serializeYeast(ArrayList<Yeast> myYeasts){
        try{
            FileOutputStream fosYeasts = new FileOutputStream("Yeasts.bat");
            ObjectOutputStream oos = new ObjectOutputStream(fosYeasts);

            oos.writeObject(myYeasts);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
