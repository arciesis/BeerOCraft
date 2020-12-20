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
        launchedConn ++;

        if (maxLaunchedConn < launchedConn) {

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
            String login = props.getProperty("jddbc.login");
            String pswd = props.getProperty("jdvc.pswd");

            try {
                myConn = DriverManager.getConnection(url, login, pswd);
                System.out.println("Connection to DB established !");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            throw new InvalidStateObjectException("");
        }

    }
































}
