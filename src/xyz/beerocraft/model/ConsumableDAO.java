/**
 * @author Arciesis   https://github.com/arciesis/BeerOCraft/
 */

package xyz.beerocraft.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConsumableDAO {

    /**
     * Method that add a fermentable to the db
     *
     * @param myMalt the fermentable to add to the db
     */
    public void addMaltToDB(Malt myMalt) {

        // Ceci est un try with ressources, la connection implementant
        // autoclosable, losrque l'on sortira du try la connection sera automatiquement fermee
        try (PreparedStatement pstmt = DBConnectionHandler.myConn.prepareStatement("INSERT INTO fermentables( name, ebc, lovibond, potential, type) VALUES (?,?,?,?,?)")) {
            pstmt.setString(1, myMalt.getName());
            pstmt.setFloat(2, myMalt.getEbc());
            pstmt.setFloat(3, myMalt.getLovibond());
            pstmt.setFloat(4, myMalt.getPotential());
            pstmt.setString(5, myMalt.getType());
            pstmt.executeUpdate();

            System.out.println("Malt has been added");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that delete a fermentables from the db
     *
     * @param name the fermentable to delete
     */
    public void deleteFermentableOfDB(String name) {
        try (PreparedStatement pstmt = DBConnectionHandler.myConn.prepareStatement("DELETE FROM fermentables WHERE fermentables.name LIKE ?")) {
            pstmt.setString(1, name);

            pstmt.execute();
            System.out.println("Fermentable named " + name + " has been deleted");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that select a fermentable by name and return the accurate fermentable
     *
     * @param name the name of the fermentable to search for
     * @return the <code>Malt</code> corresponding to the fermentable
     */
    public Malt selectFermentableFromName(String name) {
        String querry = "SELECT fermentables.* FROM fermentables WHERE name LIKE ?";
        try (PreparedStatement pstmt = DBConnectionHandler.myConn.prepareStatement(querry)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();

            rs.next();
            String nameOfTheFermenatble = rs.getString(2);
            float ebc = rs.getFloat(3);
            float lovibond = rs.getFloat(4);
            float potential = rs.getFloat(5);
            String type = rs.getString(6);

            return new Malt(nameOfTheFermenatble, ebc, lovibond, potential, type);


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Method thatreturn the ebc value of a fermentables
     *
     * @param malt the malt to get the ebc
     * @return the ebc of the malt
     */
    public float getEbcFromDB(Malt malt) {
        String query = "SELECT ebc FROM fermentables WHERE name LIKE ?";

        try (PreparedStatement pstmt = DBConnectionHandler.myConn.prepareStatement(query)) {
            pstmt.setString(1, malt.getName());
            ResultSet rs = pstmt.executeQuery();
            rs.next();

            if (rs.isLast() && rs.isFirst()) {
                return rs.getFloat(1);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;

    }

    /**
     * Method thatreturn the Lovibond value of a fermentables
     *
     * @param malt the malt to get the lovibond
     * @return the lovibond of the malt
     */
    public float getLovibondFromDB(Malt malt) {
        String query = "SELECT lovibond FROM fermentables WHERE name LIKE ?";

        try (PreparedStatement pstmt = DBConnectionHandler.myConn.prepareStatement(query)) {
            pstmt.setString(1, malt.getName());
            ResultSet rs = pstmt.executeQuery();
            rs.next();

            if (rs.isLast() && rs.isFirst()) {
                return rs.getFloat(1);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;

    }

    /**
     * Method that add a hops to the db
     *
     * @param myHop the hops to add to the db
     */
    public void addHopsToDB(Hop myHop) {

        try (PreparedStatement pstmt = DBConnectionHandler.myConn.prepareStatement("INSERT INTO hops(name, alphaAcide, type) VALUES (?,?,?)")) {
            pstmt.setString(1, myHop.getName());
            pstmt.setInt(2, myHop.getAlphaAcide());
            pstmt.setString(3, myHop.getType());

            pstmt.executeUpdate();

            System.out.println("Hop have beed added");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that add a yeast to the db
     *
     * @param myYeast the yeasts to add to the db
     */
    public static void addYeastToDB(Yeast myYeast) {
        try (PreparedStatement pstmt = DBConnectionHandler.myConn.prepareStatement("INSERT INTO yeasts(name, tempMin, tempMax, attenuation) VALUES (?,?,?,?)")) {
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


    /**
     * Method that modified an existing malt of the DB
     *
     * @param malt the malt to modify
     */
    public void updateMaltEntry(Malt malt) {

        String query = "UPDATE fermentables SET ebc = ?, lovibond = ?, potential = ?, type = ? WHERE name LIKE ?";

        try (PreparedStatement pstmt = DBConnectionHandler.myConn.prepareStatement(query)) {

            pstmt.setFloat(1, malt.getEbc());
            pstmt.setFloat(2, malt.getLovibond());
            pstmt.setFloat(3, malt.getPotential());
            pstmt.setString(4, malt.getType());
            pstmt.setString(5, malt.getName());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
