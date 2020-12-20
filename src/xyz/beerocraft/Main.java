package xyz.beerocraft;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        System.out.println("Start of the primary stage");
        Parent root = FXMLLoader.load(getClass().getResource("/MainView.fxml"));
        primaryStage.setTitle("BeerOCraft");
        primaryStage.setScene(new Scene(root, 1080, 720));
        primaryStage.show();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                System.out.println("stage is closing");
                primaryStage.close();

            }
        });


    }


    public static void main(String[] args) {


//        Malt m1 = new Malt("test",6,12,70,"grain");
//        Hop h1 = new Hop("testun", 15, "cones");
//        Yeast y1 = new Yeast("testeur",22,26,70);
//
//        Consumable consumable = new Consumable();
//        consumable.addMaltToBD(m1);
//        consumable.addHopsToDB(h1);
//        consumable.addYeastToDB(y1);

        launch(args);





    }
}