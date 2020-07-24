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

        Malts m1 = new Malts("testeur",6,12,70,"grain");
        Consumable c = new Consumable();
        c.addMalts(m1);
        Serializer.serializeMalts(c.getMyMalts());

        launch(args);


    }
}