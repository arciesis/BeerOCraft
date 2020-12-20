package xyz.beerocraft.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Properties;
import java.util.ResourceBundle;

public class MainCtrl implements Initializable {

    @FXML
    private Pane firstPane;

    @FXML
    private TabPane firstTabPane;

    @FXML
    private Tab fermentablesTab;

    @FXML
    private ListView<String> listOfFermentablesTab;

    @FXML
    private TextField textfieldSearchMalts;

    @FXML
    private Tab hopsTab;

    @FXML
    private Tab yeastsTab;

    @FXML
    private AnchorPane fermentablesTabAnchorPane;

    @FXML
    private AnchorPane hopsTabAnchorPane;

    @FXML
    private AnchorPane yeastsTabPane;

    private ObservableList<String> malts = FXCollections.observableArrayList();

    private ObservableList<String> searchingMalts = FXCollections.observableArrayList();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialisation of the main controller");

        loadMaltsToFermentablesTabListView();
        this.listOfFermentablesTab.setItems(malts);
        this.textfieldSearchMalts.setPromptText("Weyermann");
    }


    public MainCtrl() {
        System.out.println("Initialisation of the window ");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/data/arciesis/dev/java/BeerOCraft/src/resources/MainView.fxml"));
        loader.setController(this);
    }

    @FXML
    void maltsMouseClicked(MouseEvent event) {

    }



    @FXML
    public void maltsKeyHasBeenReleased(KeyEvent event) {


        String letters = textfieldSearchMalts.getText();
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

        try (Connection myConn = DriverManager.getConnection(url, login, pswd)) {
            try (PreparedStatement pstmt = myConn.prepareStatement("SELECT name FROM fermentables WHERE name LIKE ?")){
                pstmt.setString(1, letters + "%");
                ResultSet rs = pstmt.executeQuery();

                this.searchingMalts = null;
                this.searchingMalts = FXCollections.observableArrayList();

                while (rs.next()) {
                    this.searchingMalts.add(rs.getString(1));
                    System.out.println("Malts added to listView : " + rs.getString(1));
                }
            } catch (SQLException e){
                e.printStackTrace();
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

        if (this.textfieldSearchMalts.getText().equalsIgnoreCase("")){
            this.listOfFermentablesTab.setItems(malts);
        } else {
            this.listOfFermentablesTab.setItems(searchingMalts);

        }
    }



    public void loadMaltsToFermentablesTabListView(){
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
        

        try (Connection myConn = DriverManager.getConnection(url, login, pswd)) {
            String query = "SELECT name FROM fermentables";
            Statement st = myConn.createStatement();
            ResultSet rs = st.executeQuery(query);


            while(rs.next()){

                 System.out.println(rs.getString(1));
                 this.malts.add(rs.getString(1));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
