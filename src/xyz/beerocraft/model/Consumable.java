package xyz.beerocraft.model;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Consumable {

    public static void addMaltToDB(Malt myMalt) {

        // Ceci est un try with ressources, la connection implementant
        // autoclosable, losrque l'on sortira du try la connection sera automatiquement fermee
            try (PreparedStatement pstmt = DBHandler.myConn.prepareStatement("INSERT INTO fermentables( name, ebc, lovibond, potential, type) VALUES (?,?,?,?,?)")) {
                pstmt.setString(1, myMalt.getName());
                pstmt.setFloat(2, myMalt.getEbc());
                pstmt.setFloat(3, myMalt.getLovibond());
                pstmt.setFloat(4, myMalt.getPotential());
                pstmt.setString(5, myMalt.getType());
                pstmt.executeUpdate();

                System.out.println("Malt has been added");
            } catch (SQLException e){
                e.printStackTrace();
        }
    }


    public static void addHopsToDB(Hop myHop) {

            try (PreparedStatement pstmt = DBHandler.myConn.prepareStatement("INSERT INTO hops(name, alphaAcide, type) VALUES (?,?,?)")) {
                pstmt.setString(1, myHop.getName());
                pstmt.setInt(2, myHop.getAlphaAcide());
                pstmt.setString(3, myHop.getType());

                pstmt.executeUpdate();

                System.out.println("Hop have beed added");
            } catch (SQLException e){
                e.printStackTrace();
            }
    }

    public static void addYeastToDB(Yeast myYeast) {
            try (PreparedStatement pstmt = DBHandler.myConn.prepareStatement("INSERT INTO yeasts(name, tempMin, tempMax, attenuation) VALUES (?,?,?,?)")) {
                pstmt.setString(1, myYeast.getName());
                pstmt.setInt(2, myYeast.getTempMin());
                pstmt.setInt(3, myYeast.getTempMax());
                pstmt.setInt(4, myYeast.getApparentAttenuation());

                pstmt.executeUpdate();

                System.out.println("Yeast have beed added");
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }
}
