package xyz.louscars;

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

        Malt m1 = new Malt("testeur",6,12,70,"grain");
        Hop h1 = new Hop("test", 15, "cones");
        Yeast y1 = new Yeast("testun",22,26,70);
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

        Properties props = new Properties();

        try (FileInputStream fis = new FileInputStream("/home/arciesis/dev/java/BeerOCraft/src/xyz/louscars/conf.properties")){
            props.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{

            Class.forName(props.getProperty("jdbc.class"));

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String url = props.getProperty("jdbc.url");
        String login = props.getProperty("jdbc.login");
        String pswd = props.getProperty("jdbc.pswd");


        // Ceci est un try with ressources, la connection implementant
        // autoclosable, losrque l'on sortira du try la connection sera automatiquement fermee
        try (Connection myConn = DriverManager.getConnection(url,login,pswd)) {
//            String requete = "INSERT INTO fermetescibles (id, name, ebc, potential, type)" +
//                    "VALUES (0, " + 'c.getMyMalts().get(0).getName()' + ", " + 'c.getMyMalts().get(0).getEbc()'' + ", " +
//                    'c.getMyMalts().get(0).getPotential()' + ", " + 'c.getMyMalts().get(0).getType()' + ")";




            // idem que juste en haut
            try (PreparedStatement pstmt = myConn.prepareStatement("INSERT INTO fermetescibles(id, name, ebc, potential, type) VALUES (?,?,?,?,?)") ){
                pstmt.setInt(1, 1);
                pstmt.setString(2,c.getMyMalts().get(0).getName());
                pstmt.setInt(3,c.getMyMalts().get(0).getEbc());
                pstmt.setInt(4,c.getMyMalts().get(0).getPotential());
                pstmt.setString(5,c.getMyMalts().get(0).getType());

                pstmt.executeUpdate();

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


//        try{
//            Class.forName("com.mysql.cj.jdbc.Driver");
//
//            String urlDB = "jdbc:mysql://localhost:3306/BeerOCraftDB?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
//            String userDB = "BeerOCraft";
//            String pswdDB = "Tribunalipadtheserelaxe1*";
//
//
//            Connection myConn = DriverManager.getConnection(urlDB,userDB,pswdDB);
//            System.out.println("Database connection established");
//
//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//        }


        launch(args);


    }
}