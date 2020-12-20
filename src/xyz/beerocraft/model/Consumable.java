package xyz.beerocraft.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class Consumable {

    public Properties connectToJDBC() {
        Properties props = new Properties();


        try (FileInputStream fis = new FileInputStream("/home/arciesis/dev/java/BeerOCraft/src/xyz/beerocraft/conf.properties")) {
            props.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Class.forName(props.getProperty("jdbc.class"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return props;
    }

    public void addMaltToDB(Malt myMalt) {
        //connection to the DB
        Properties props = connectToJDBC();

        String url = props.getProperty("jdbc.url");
        String login = props.getProperty("jdbc.login");
        String pswd = props.getProperty("jdbc.pswd");

        // Ceci est un try with ressources, la connection implementant
        // autoclosable, losrque l'on sortira du try la connection sera automatiquement fermee
        try (Connection myConn = DriverManager.getConnection(url, login, pswd)) {

            // idem que juste en haut
            try (PreparedStatement pstmt = myConn.prepareStatement("INSERT INTO fermentables( name, ebc, lovibond, potential, type) VALUES (?,?,?,?,?)")) {
                pstmt.setString(1, myMalt.getName());
                pstmt.setInt(2, myMalt.getEbc());
                pstmt.setInt(3, myMalt.getLovibond());
                pstmt.setInt(4, myMalt.getPotential());
                pstmt.setString(5, myMalt.getType());
                pstmt.executeUpdate();

                System.out.println("Malt has been added");

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public void addHopsToDB(Hop myHop) {
        Properties props = connectToJDBC();

        String url = props.getProperty("jdbc.url");
        String login = props.getProperty("jdbc.login");
        String pswd = props.getProperty("jdbc.pswd");

        try (Connection myConn = DriverManager.getConnection(url, login, pswd)) {
            try (PreparedStatement pstmt = myConn.prepareStatement("INSERT INTO hops(name, alphaAcide, type) VALUES (?,?,?)")) {
                pstmt.setString(1, myHop.getName());
                pstmt.setInt(2, myHop.getAlphaAcide());
                pstmt.setString(3, myHop.getType());

                pstmt.executeUpdate();

                System.out.println("Hop have beed added");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addYeastToDB(Yeast myYeast) {
        Properties props = connectToJDBC();

        String url = props.getProperty("jdbc.url");
        String login = props.getProperty("jdbc.login");
        String pswd = props.getProperty("jdbc.pswd");

        try (Connection myConn = DriverManager.getConnection(url, login, pswd)) {
            try (PreparedStatement pstmt = myConn.prepareStatement("INSERT INTO yeasts(name, tempMin, tempMax, attenuation) VALUES (?,?,?,?)")) {
                pstmt.setString(1, myYeast.getName());
                pstmt.setInt(2, myYeast.getTempMin());
                pstmt.setInt(3, myYeast.getTempMax());
                pstmt.setInt(4, myYeast.getApparentAttenuation());

                pstmt.executeUpdate();

                System.out.println("Yeast have beed added");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
