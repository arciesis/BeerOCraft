/**
 * @author Arciesis   https://github.com/arciesis/BeerOCraft/
 */

package xyz.beerocraft.model;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Consumable {

    /**
     * Method that add a fermentable to the db
     * @param myMalt the fermentable to add to the db
     */
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


    /**
     * Method that add a hops to the db
     * @param myHop the hops to add to the db
     */
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

    /**
     * Method that add a yeast to the db
     * @param myYeast the yeasts to add to the db
     */
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
