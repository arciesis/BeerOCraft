package xyz.beerocraft.controller;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import xyz.beerocraft.model.DBHandler;
import xyz.beerocraft.model.Malt;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    @FXML
    private TextField maltNameTextField;

    @FXML
    private TextField maltEBCTextField;

    @FXML
    private TextField maltLovibondTextField;

    @FXML
    private TextField maltPotentialTextField;

    @FXML
    private ComboBox<String> maltTypeComboBox;

    private ObservableList<String> malts = FXCollections.observableArrayList();

    private ObservableList<String> searchingMalts = FXCollections.observableArrayList();

    ObservableList<String> maltTypeChoices = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialisation of the main controller");

        DBHandler dbHandler = new DBHandler();

        loadMaltsToFermentablesTabListView();
        this.listOfFermentablesTab.setItems(malts);
        this.textfieldSearchMalts.setPromptText("Weyermann");

        maltsMouseClicked();
    }


    public MainCtrl() {
        System.out.println("Initialisation of the window ");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/data/arciesis/dev/java/BeerOCraft/src/resources/MainView.fxml"));
        loader.setController(this);
    }

    @FXML
    void maltsMouseClicked() {
        //String maltName = listOfFermentablesTab.getSelectionModel().getSelectedItems().get(0);
        //PreparedStatement pstmt = DBHandler.myConn.prepareStatement("SELECT")
        listOfFermentablesTab.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                System.out.println("Selectioned malt : " + newValue);

                try {

                    PreparedStatement pstmt = DBHandler.myConn.prepareStatement("SELECT * FROM fermentables WHERE name LIKE ?");
                    pstmt.setString(1, newValue);

                    ResultSet rs = pstmt.executeQuery();


                    if (maltTypeComboBox.getItems().isEmpty()) {

                        maltTypeChoices.addAll(Malt.TYPE_POSSIBLE);
                        maltTypeComboBox.getItems().addAll(maltTypeChoices);

                    }

                    while (rs.next()) {

                        maltNameTextField.setText(rs.getString(2));
                        maltEBCTextField.setText(rs.getString(3));
                        maltLovibondTextField.setText(rs.getString(4));
                        maltPotentialTextField.setText(rs.getString(5));
                        maltTypeComboBox.setPromptText(rs.getString(6));

                    }


                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    @FXML
    public void maltsKeyHasBeenReleased(KeyEvent event) {

        String letters = textfieldSearchMalts.getText();

        try (PreparedStatement pstmt = DBHandler.myConn.prepareStatement("SELECT name FROM fermentables WHERE name LIKE ?")) {
            pstmt.setString(1, letters + "%");
            ResultSet rs = pstmt.executeQuery();

            this.searchingMalts = null;
            this.searchingMalts = FXCollections.observableArrayList();

            while (rs.next()) {
                this.searchingMalts.add(rs.getString(1));
                System.out.println("Malts added to listView : " + rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (this.textfieldSearchMalts.getText().equalsIgnoreCase("")) {
            this.listOfFermentablesTab.setItems(malts);
        } else {
            this.listOfFermentablesTab.setItems(searchingMalts);

        }
    }


    public void loadMaltsToFermentablesTabListView() {

        try {
            String query = "SELECT name FROM fermentables";
            Statement st = DBHandler.myConn.createStatement();
            ResultSet rs = st.executeQuery(query);


            while (rs.next()) {

                System.out.println(rs.getString(1));
                this.malts.add(rs.getString(1));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
