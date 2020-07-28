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
        Consumable c = new Consumable();
        c.addMalts(m1);

        c.serialize(c.getMyMalts(), "Malts.ser");
        System.out.println(c.deserialize("Malts.ser"));

        launch(args);


    }
}