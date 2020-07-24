package xyz.louscars;

import java.io.FileNotFoundException;
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
