package xyz.louscars;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/MainView.fxml"));
        primaryStage.setTitle("BeerOCraft");
        primaryStage.setScene(new Scene(root, 1080, 720));
        primaryStage.show();
    }


    public static void main(String[] args) {

        Malt m1 = new Malt("testeur",6,12,70,"grain");
        Hop h1 = new Hop("test", 15, "cones");
        Yeast y1 = new Yeast(22,26,70);
        Consumable c = new Consumable();
        c.addMalts(m1);
        c.addYeast(y1);
        c.addHops(h1);


        c.serialize(c.getMyMalts(), "Malts.ser");
        c.serialize(c.getMyHops(), "Hops.ser");
        c.serialize(c.getMyYeast(), "Yeasts.ser");

        System.out.println(c.deserialize("Malts.ser"));
        System.out.println(c.deserialize("Hops.ser"));
        System.out.println(c.deserialize("Yeasts.ser"));


        launch(args);


    }
}