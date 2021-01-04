package xyz.beerocraft.controller;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import xyz.beerocraft.model.DBHandler;
import xyz.beerocraft.model.Malt;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import static xyz.beerocraft.model.Malt.*;

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
    private Button maltModifyButton;

    @FXML
    private Button maltAddMaltButton;

    @FXML
    private ComboBox<String> maltTypeComboBox;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialisation of the main controller");

        DBHandler dbHandler = new DBHandler();

        loadMaltsToFermentablesTabListView();
        this.listOfFermentablesTab.setItems(malts);
        this.textfieldSearchMalts.setPromptText("Weyermann");

        maltsMouseClicked();
        loadFermentableToComboBox();

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

            searchingMalts = null;
            searchingMalts = FXCollections.observableArrayList();

            while (rs.next()) {
                searchingMalts.add(rs.getString(1));
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
                malts.add(rs.getString(1));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void loadFermentableToComboBox() {
        if (maltTypeComboBox.getItems().isEmpty()) {

            maltTypeChoices.addAll(Malt.TYPE_POSSIBLE);
            maltTypeComboBox.getItems().addAll(maltTypeChoices);

        }
    }


    @FXML
    void handleAddMaltButton(ActionEvent event) {

        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/addAFermentable.fxml"));
            Stage addAFermentablePopUp = new Stage();
            addAFermentablePopUp.setTitle("Add a fermentable");
            addAFermentablePopUp.setScene(new Scene(root, 800, 450));
            addAFermentablePopUp.show();
            // Hide this current window (if this is what you want)
            //((Node)(event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*@FXML
    void handleAddMaltButton(ActionEvent event) {
        System.out.println("Add fermentable Button clicked");

        try {
            String querry = "SELECT name FROM fermentables";
            Statement st = DBHandler.myConn.createStatement();
            ResultSet rs = st.executeQuery(querry);

            String nameOfNewFermenatble = maltNameTextField.getText();
            //System.out.println(nameOfNewFermenatble);

            while (rs.next()) {
                if (rs.getString(1).trim().equalsIgnoreCase(nameOfNewFermenatble)){
                    System.out.println("Fermentable already exists");
                    break;
                }
            }

            boolean isEBCSelected = maltEBCToggleButton.isSelected();

            float ebc = stringToFloatParser(maltEBCTextField.getText());
            float lovibond = stringToFloatParser(maltLovibondTextField.getText());
            float potential = stringToFloatParser(maltPotentialTextField.getText());
            String type = maltTypeComboBox.getValue();

            if (isEBCSelected) lovibond = ((ebc + 1.2f) / 2.65f);
            else ebc = (lovibond * 2.65f) - 1.2f;

            Malt m = new Malt(nameOfNewFermenatble,ebc,lovibond,potential,type);
            Consumable.addMaltToDB(m);

            malts.add(m.getName());
            malts.sorted()


        }catch (SQLException e){
            e.printStackTrace();
        }
    }*/

    @FXML
    void handleModifyMaltButton(ActionEvent event) {
        System.out.println("Modify fermentable Button clicked");

    }

    /**
     * Test if a String only contains digit
     *
     * @param str The String to test
     * @return True if the String only contains digit
     */
    private boolean isIntInput(String str) {
        if (str == null || str.trim().equalsIgnoreCase(""))
            return false;

        char c[] = str.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] < '0' || c[i] > '9')
                return false;
        }
        return true;
    }


    private int stringToIntParser(String str) {
        if (isIntInput(str)) {
            return Integer.parseInt(str);
        } else return -1;
    }
}
