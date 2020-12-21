package xyz.beerocraft.model;

import xyz.beerocraft.controller.InvalidStateObjectException;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBHandler {

    public static Connection myConn;

    private final int maxLaunchedConn = 1;

    private static int launchedConn;

    static {
        launchedConn = 0;
    }


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

    public static void onClosedRequestDB(){
        launchedConn --;
    }

}
