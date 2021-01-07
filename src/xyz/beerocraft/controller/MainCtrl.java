/**
 * @author Arciesis https://guthub.com/arciesis/BeerOCraft/
 */

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
import xyz.beerocraft.model.ConsumableDAO;
import xyz.beerocraft.model.DBConnectionHandler;
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


    /**
     * the tab Pane of the main window
     */
    @FXML
    private Pane firstPane;

    /**
     * The tab Pane of the main window
     */
    @FXML
    private TabPane firstTabPane;

    /**
     * The fermentable tab of the main window
     */
    @FXML
    private Tab fermentablesTab;

    /**
     * The list view that contains oll the fermentables of the db of the main window
     */
    @FXML
    private ListView<String> listOfFermentablesTab;

    /**
     * The Search malt text fieldof the main window
     */
    @FXML
    private TextField textfieldSearchMalts;

    /**
     * The Hops Tabof the main window
     */
    @FXML
    private Tab hopsTab;

    /**
     * The Yeasts tabof the main window
     */
    @FXML
    private Tab yeastsTab;

    /**
     * The Hops anchr tab paneof the main window
     */
    @FXML
    private AnchorPane fermentablesTabAnchorPane;

    /**
     * the Hops anchor paneof the main window
     */
    @FXML
    private AnchorPane hopsTabAnchorPane;

    /**
     * th yeasts tab paneof the main window
     */
    @FXML
    private AnchorPane yeastsTabPane;

    /**
     * The Name text fieldof the main window
     */
    @FXML
    private TextField maltNameTextField;

    /**
     * The EBC text fieldof the main window
     */
    @FXML
    private TextField maltEBCTextField;

    /**
     * The Lovibond text fieldof the main window
     */
    @FXML
    private TextField maltLovibondTextField;

    /**
     * The potential text fieldof the main window
     */
    @FXML
    private TextField maltPotentialTextField;

    /**
     * The modify buttonof the main window
     */
    @FXML
    private Button maltModifyButton;

    /**
     * The add button fermentableof the main window
     */
    @FXML
    private Button maltAddMaltButton;

    /**
     * The delete button of the main window
     */
    @FXML
    private Button maltDeleteButton;

    /**
     * the type combo box of the main window
     */
    @FXML
    private ComboBox<String> maltTypeComboBox;

    /**
     * The Object wich handle interaction with the DB
     */
    private ConsumableDAO dao;


    /**
     * Methd that Initialize the main controller
     *
     * @param url            not used
     * @param resourceBundle not used
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialisation of the main controller");

        DBConnectionHandler dbConnectionHandler = new DBConnectionHandler();
        dao = new ConsumableDAO();

        loadMaltsToFermentablesTabListView();
        this.listOfFermentablesTab.setItems(malts);
        this.textfieldSearchMalts.setPromptText("Weyermann");

        maltsMouseClicked();
        loadFermentableToComboBox();

        maltNameTextField.setDisable(true);

    }


    /**
     * Constructor of the main controller
     */
    public MainCtrl() {
        System.out.println("Initialisation of the window ");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/data/arciesis/dev/java/BeerOCraft/src/resources/MainView.fxml"));
        loader.setController(this);
    }

    /**
     * Method that load the details of the selectionned fermentable
     */
    @FXML
    private void maltsMouseClicked() {
        //String maltName = listOfFermentablesTab.getSelectionModel().getSelectedItems().get(0);
        //PreparedStatement pstmt = DBHandler.myConn.prepareStatement("SELECT")

        //TODO refactor en DAO

        listOfFermentablesTab.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                System.out.println("Selectioned malt : " + newValue);

                try {

                    PreparedStatement pstmt = DBConnectionHandler.myConn.prepareStatement("SELECT * FROM fermentables WHERE name LIKE ?");
                    pstmt.setString(1, newValue);

                    ResultSet rs = pstmt.executeQuery();


                    while (rs.next()) {

                        maltNameTextField.setText(rs.getString(2));
                        maltEBCTextField.setText(rs.getString(3));
                        maltLovibondTextField.setText(rs.getString(4));
                        maltPotentialTextField.setText(rs.getString(5));
                        maltTypeComboBox.setValue(rs.getString(6));

                    }


                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    /**
     * Method that search and clear the list view according thetext typed on the search text field
     *
     * @param event the event that is listened
     */
    @FXML
    private void maltsKeyHasBeenReleased(KeyEvent event) {
        //TODO refactor vers DAO
        String letters = textfieldSearchMalts.getText();

        try (PreparedStatement pstmt = DBConnectionHandler.myConn.prepareStatement("SELECT name FROM fermentables WHERE name LIKE ?")) {
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


    /**
     * Method that load all the malts contained on the db into the list view of the fermentable tab of the main window
     */
    private void loadMaltsToFermentablesTabListView() {
        //TODO refactor vers DAO
        try {
            String query = "SELECT name FROM fermentables";
            Statement st = DBConnectionHandler.myConn.createStatement();
            try (ResultSet rs = st.executeQuery(query)) {

                while (rs.next()) {

                    System.out.println(rs.getString(1));
                    malts.add(rs.getString(1));

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Method that load the type of malt to the combo box of the tab fermentable of the main window
     */
    private void loadFermentableToComboBox() {
        if (maltTypeComboBox.getItems().isEmpty()) {

            maltTypeChoices.addAll(Malt.TYPE_POSSIBLE);
            maltTypeComboBox.getItems().addAll(maltTypeChoices);

        }
    }

    /**
     * method that handle the add a malt button of the main window
     * in fact this button create a new window to add a malt properly
     *
     * @param event the event that is listened
     */
    @FXML
    private void handleAddMaltButton(ActionEvent event) {

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


    /**
     * Method that delete a fermentables of the db
     *
     * @param event the event that is listened
     */
    @FXML
    private void handleDeleteFermentablesButton(ActionEvent event) {
        Alert confirmYouWantToDeleteFermentableAlert = new Alert(Alert.AlertType.INFORMATION, "Delete ?", ButtonType.YES, ButtonType.NO);
        confirmYouWantToDeleteFermentableAlert.setTitle("Delete this fermentable");
        confirmYouWantToDeleteFermentableAlert.setContentText("Are you sur you want to delete this fermentable ?");
        confirmYouWantToDeleteFermentableAlert.showAndWait();

        if (confirmYouWantToDeleteFermentableAlert.getResult() == ButtonType.YES) {

            System.out.println("remove fermentable confirmed");
            String name = listOfFermentablesTab.getSelectionModel().getSelectedItem();

            dao.deleteFermentableOfDB(name);
            malts.clear();
            loadMaltsToFermentablesTabListView();
            listOfFermentablesTab.setItems(malts);

        }
    }


    /**
     * The method that handle the modify malt button of the main window
     *
     * @param event that is listened
     */
    @FXML
    private void handleModifyMaltButton(ActionEvent event) {
        System.out.println("Modify fermentable Button clicked");

        String nameOfTheFermentable = listOfFermentablesTab.getSelectionModel().getSelectedItem();
        Malt modifiedMalt = dao.selectFermentableFromName(nameOfTheFermentable);

        if (modifiedMalt != null) {
            String stringEbc = maltEBCTextField.getText();
            String stringLovibond = maltLovibondTextField.getText();
            String stringPotential= maltPotentialTextField.getText();
            String stringType = maltTypeComboBox.getSelectionModel().getSelectedItem();

            if (isFloatInput((stringEbc)) && isFloatInput(stringLovibond) && isFloatInput(stringPotential)) {

                float floatEbc = stringToFloatParser(stringEbc);
                float floatLovibond = stringToFloatParser(stringLovibond);
                float floatPotential = stringToFloatParser(stringPotential);

                if (floatPotential >= 0 || floatPotential <= 100) {

                    modifiedMalt.setPotential(floatPotential);
                    modifiedMalt.setType(stringType);

                    if (floatEbc != dao.getEbcFromDB(modifiedMalt)) {
                        ;
                        floatLovibond = (floatEbc + 1.2f) / 2.65f;
                    } else if (floatLovibond != dao.getLovibondFromDB(modifiedMalt)) {
                        floatEbc = (floatLovibond * 2.65f) - 1.2f;
                    }


                    modifiedMalt.setEbc(floatEbc);
                    modifiedMalt.setLovibond(floatLovibond);

                    dao.updateMaltEntry(modifiedMalt);

                    malts.clear();
                    loadMaltsToFermentablesTabListView();
                    listOfFermentablesTab.setItems(malts);
                    listOfFermentablesTab.getSelectionModel().select(nameOfTheFermentable);

                } else {

                    System.out.println("Potential entry not valid");

                    Alert potentialEntryIsNotValidAlert = new Alert(Alert.AlertType.WARNING);
                    potentialEntryIsNotValidAlert.setTitle("Invalid entry for potential");;
                    potentialEntryIsNotValidAlert.setContentText("Your potential value is in % so it must be between 0% and 100%");
                    potentialEntryIsNotValidAlert.showAndWait();

                }
            } else {

                System.out.println("An Input dosen't match requirement to modify this fermentable");

                Alert inputDosentMatchRequirementAlert = new Alert(Alert.AlertType.WARNING);
                inputDosentMatchRequirementAlert.setTitle("Input dosen't match requirement");
                inputDosentMatchRequirementAlert.setContentText("An Input dosen't match requirement.\ne.g. one of the input is not a valid number");
                inputDosentMatchRequirementAlert.showAndWait();
            }

        } else {
            System.out.println("Hmm The name has been modify what can I do ? c'est pour ca qu'il faut que jenbtraite aavec l'id");
        }

    }


    /**
     * Test if a String only contains digit
     *
     * @param str The String to test
     * @return True if the String only contains digit
     */
    public static boolean isIntInput(String str) {
        if (str == null || str.trim().equalsIgnoreCase(""))
            return false;

        char[] c = str.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] < '0' || c[i] > '9')
                return false;
        }
        return true;
    }

    /**
     * the method that parse a string nto an Int
     *
     * @param str the string to parse
     * @return the parsed int
     */
    public static int stringToIntParser(String str) {
        if (isIntInput(str)) {
            return Integer.parseInt(str);
        } else return -1;
    }

    /**
     * the method that parse a string into a float
     *
     * @param str the strings that need to be parsed
     * @return the parsed float
     */
    public static float stringToFloatParser(String str) {
        if (isFloatInput(str)) {
            return Float.parseFloat(str);
        } else return -1;
    }

    /**
     * the method that test if a string can be cparse into a float
     *
     * @param str the strings to test
     * @return true if the float is parsable and false if it isn't
     */
    public static boolean isFloatInput(String str) {
        if (str == null || str.trim().equalsIgnoreCase(""))
            return false;

        char[] c = str.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] < '0' || c[i] > '9') {
                return c[i] == '.';
            }
        }
        return true;
    }
}
