package xyz.beerocraft.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import xyz.beerocraft.model.Consumable;
import xyz.beerocraft.model.DBHandler;
import xyz.beerocraft.model.Malt;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;


import static xyz.beerocraft.model.Malt.maltTypeChoices;
import static xyz.beerocraft.model.Malt.malts;

public class AddAFermentableController implements Initializable {


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadFermentableToComboBoxOnAdd();
    }

    /**
     * the name text field of the "Add a fermentable" window
     */
    @FXML
    private TextField addAfermentableNameTextField;

    /**
     * the EBC text field of the "Add a fermentable" window
     */
    @FXML
    private TextField addAfermentableEBCTextField;

    /**
     * the Lovibond text field of the "Add a fermentable" window
     */
    @FXML
    private TextField addafermentableLovibondTextField;

    /**
     * the potential text Field of the "Add a fermentable" window
     */
    @FXML
    private TextField addAFermentablePotentialTextField;

    /**
     * the type combo box of the "Add a fermentable" window
     */
    @FXML
    private ComboBox<String> addAFermentableTypeComboBox;

    /**
     * the button to add a fermentable of the "Add a fermentable" window
     */
    @FXML
    private Button addAFermentableAddButton;

    /**
     * the reset button of the "Add a fermentable" window
     */
    @FXML
    private Button addaFermentableResetButton;

    /**
     * the EBC toggle button of the "Add a fermentable" window
     */
    @FXML
    private ToggleButton addAFermentableEBCToggleButton;

    /**
     * the toglle group of the "Add a fermentable" window
     * it contains :
     * - ebc toggle button
     * -Lovibond toggle button
     */
    @FXML
    private ToggleGroup maltColorToggleGroup;

    /**
     * the lovibond toggle  button of the "Add a fermentable" window
     */
    @FXML
    private ToggleButton addAfermentableLovibondToggleButton;

    /**
     * the method that handle the button "add a fermentable" of the "Add a fermentable" window
     * @param event the event taht is listenned
     */
    @FXML
    void handleAddButton(ActionEvent event) {
        System.out.println("Add fermentable Button clicked");

        try {
            String querry = "SELECT name FROM fermentables";
            Statement st = DBHandler.myConn.createStatement();
            ResultSet rs = st.executeQuery(querry);

            String nameOfNewFermenatble = addAfermentableNameTextField.getText();
            //System.out.println(nameOfNewFermenatble);

            boolean isAWarningAppeared = false;

            if (!addAfermentableNameTextField.getText().trim().isEmpty()) {

                while (rs.next()) {

                    String nameValue = rs.getString(1);

                    if (nameValue.trim().equalsIgnoreCase(nameOfNewFermenatble)) {

                        isAWarningAppeared = true;

                        System.out.println("Fermentable already exists");
                        Alert fermentableAlreadyExistsAlert = new Alert(Alert.AlertType.WARNING);
                        fermentableAlreadyExistsAlert.setTitle("Fermentable already exists");
                        fermentableAlreadyExistsAlert.setContentText("You are trying a add fermentable with a name that already exists.\nYou must choose another name or search for your fermentable");
                        fermentableAlreadyExistsAlert.showAndWait();
                    }
                }


                boolean isEBCSelected = addAFermentableEBCToggleButton.isSelected();
                boolean isLovibondSelected = addAfermentableLovibondToggleButton.isSelected();

                if (!isAWarningAppeared) {
                    if (isEBCSelected || isLovibondSelected) {

                        if ((!addAfermentableEBCTextField.getText().isEmpty() && isEBCSelected) || (!addafermentableLovibondTextField.getText().isEmpty() && isLovibondSelected ))  {

                            if (!addAFermentablePotentialTextField.getText().isEmpty()) {

                                if (!addAFermentableTypeComboBox.getSelectionModel().isEmpty()) {


                                    float ebc = stringToFloatParser(addAfermentableEBCTextField.getText());
                                    float lovibond = stringToFloatParser(addafermentableLovibondTextField.getText());
                                    float potential = stringToFloatParser(addAFermentablePotentialTextField.getText());
                                    String type = addAFermentableTypeComboBox.getValue();


                                    if (isEBCSelected) lovibond = ((ebc + 1.2f) / 2.65f);
                                    else ebc = (lovibond * 2.65f) - 1.2f;


                                    Malt m = new Malt(nameOfNewFermenatble, ebc, lovibond, potential, type);
                                    Consumable.addMaltToDB(m);

                                    malts.add(m.getName());
                                    malts.sorted();


                                    Node source = (Node) event.getSource();
                                    Stage thisStage = (Stage) source.getScene().getWindow();
                                    thisStage.close();


                                } else {
                                    System.out.println("No entry for ConmboBox");
                                    Alert emptyComboBoxAlert = new Alert(Alert.AlertType.WARNING);
                                    emptyComboBoxAlert.setTitle("Choose your type");
                                    emptyComboBoxAlert.setContentText("You haven't choose a type for your fermentable !");
                                    emptyComboBoxAlert.showAndWait();
                                }

                            } else {
                                System.out.println("No entry for potential value");
                                Alert emptyPotentialAlert = new Alert(Alert.AlertType.WARNING);
                                emptyPotentialAlert.setTitle("Empty Potential");
                                emptyPotentialAlert.setContentText("You must enter a value for Potential !");
                                emptyPotentialAlert.showAndWait();
                            }

                        } else {
                            System.out.println("No entry for EBC");
                            Alert emptyEBCTextFieldAlert = new Alert(Alert.AlertType.INFORMATION);
                            emptyEBCTextFieldAlert.setTitle("Empty EBC");
                            emptyEBCTextFieldAlert.setContentText("You can either choose a Lovibond calculator or enter an EBC value");
                            emptyEBCTextFieldAlert.showAndWait();
                        }

                    } else {
                        System.out.println("Toggle Button not selected");
                        Alert toggleGroupAlert = new Alert(Alert.AlertType.WARNING);
                        toggleGroupAlert.setTitle("Is your unit EBC or Lovibind");
                        toggleGroupAlert.setContentText("You must choose either EBC or Lovibind unit");
                        toggleGroupAlert.showAndWait();
                    }

                }

            } else {
                System.out.println("Name textField is empty");
                Alert emptyNameTextFieldAlert = new Alert(Alert.AlertType.WARNING);
                emptyNameTextFieldAlert.setTitle("Empty Name Field");
                emptyNameTextFieldAlert.setContentText("You must choose a name for your fermentable");
                emptyNameTextFieldAlert.showAndWait();
            }



        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * the method that handle the reset button of the "add a fermentable" window
     * @param event
     */
    @FXML
    void handleResetButton(ActionEvent event) {
        addAfermentableNameTextField.clear();
        addAfermentableEBCTextField.clear();
        addafermentableLovibondTextField.clear();
        addAFermentablePotentialTextField.clear();
        addAFermentableTypeComboBox.setValue(null);
    }

    /**
     * the method that load the type of a fermentable to the combo box "type"
     */
    private void loadFermentableToComboBoxOnAdd() {
        if (addAFermentableTypeComboBox.getItems().isEmpty() && maltTypeChoices.isEmpty())
            maltTypeChoices.addAll(Malt.TYPE_POSSIBLE);

        addAFermentableTypeComboBox.getItems().addAll(maltTypeChoices);
    }

    /**
     * the method that parse a string into a float
     * @param str the strings that need to be parsed
     * @return the parsed float
     */
    private float stringToFloatParser(String str) {
        if (isFloatInput(str)) {
            return Float.parseFloat(str);
        } else return -1;
    }

    /**
     * the method that test if a string can be cparse into a float
     * @param str the strings to test
     * @return true if the float is parsable and false if it isn't
     */
    private boolean isFloatInput(String str) {
        if (str == null || str.trim().equalsIgnoreCase(""))
            return false;

        char c[] = str.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] < '0' || c[i] > '9') {
                if (c[i] == '.')
                    return true;
                return false;
            }
        }
        return true;
    }

}

