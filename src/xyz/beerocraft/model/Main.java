package xyz.beerocraft.model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/MainView.fxml"));
        primaryStage.setTitle("BeerOCraft");
        primaryStage.setScene(new Scene(root, 1080, 720));
        primaryStage.show();
    }


    public static void main(String[] args) {

        Malt m1 = new Malt("testeur 1.0",6,12,70,"grain");
        Hop h1 = new Hop("test", 15, "cones");
        Yeast y1 = new Yeast("testun",22,26,70);
        ConsumableOld c = new ConsumableOld();
        c.addMalts(m1);
        c.addYeast(y1);
        c.addHops(h1);

        Consumable consumable = new Consumable();
        consumable.addMaltToBD(m1);
        consumable.addHopsToDB(h1);
        consumable.addYeastToDB(y1);
        

        launch(args);


    }
}