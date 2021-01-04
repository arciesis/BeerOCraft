/**
 * @author Arciesis https://github.com/arciesis/BeerOCraft/
 */

package xyz.beerocraft.model;

import xyz.beerocraft.controller.InvalidStateObjectException;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBHandler {

    /**
     * The connection to the DB
     */
    public static Connection myConn;

    /**
     * The maximum connection to the db
     */
    private final int maxLaunchedConn = 1;

    /**
     * The actual number of connection to the db
     */
    private static int launchedConn;

    /**
     * Block that initate the connection to the db at the first launch of the class
     */
    static {
        launchedConn = 0;
    }

    /**
     * Constructor of the class
     * @throws InvalidStateObjectException Exception that indicate that the number of connection to the db is higher
     * than necessary
     */
    public DBHandler() throws InvalidStateObjectException{
        System.out.println("Connection to DB ...");

        if (maxLaunchedConn > launchedConn) {
            launchedConn ++;

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

            String url = props.getProperty("jdbc.url");
            String login = props.getProperty("jdbc.login");
            String pswd = props.getProperty("jdbc.pswd");

            try {
                myConn = DriverManager.getConnection(url, login, pswd);
                System.out.println("Connection to DB established !");
            } catch (SQLException e) {
                e.printStackTrace();
            }

            launchedConn ++;
        } else {
            throw new InvalidStateObjectException("You try to connect to the DB twice without closing it !");
        }



    }

    /**
     * Method that count the number of connection to the db
     */
    public static void onClosedRequestDB(){
        launchedConn --;
    }

}
