package xyz.beerocraft;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import xyz.beerocraft.model.DBHandler;

import java.sql.SQLException;


public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("Start of the primary stage");
        Parent root = FXMLLoader.load(getClass().getResource("/MainView.fxml"));
        primaryStage.setTitle("BeerOCraft");
        primaryStage.setScene(new Scene(root, 1080, 720));
        primaryStage.show();

        primaryStage.setOnCloseRequest(windowEvent -> {
            System.out.println("stage is closing");

            try {
                DBHandler.myConn.close();
                System.out.println("DB is closed");
                DBHandler.onClosedRequestDB();
            } catch (SQLException e) {
                e.printStackTrace();
            }


            primaryStage.close();

        });


    }


    public static void main(String[] args) {
        launch(args);
    }
}