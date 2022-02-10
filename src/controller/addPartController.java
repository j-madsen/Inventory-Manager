package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import model.InHouse;
import model.Inventory;
import model.Outsourced;

import java.io.IOException;
import java.util.Optional;

import static controller.mainController.toMainScreen;
import static model.Inventory.*;

/** This class houses the methods used to work with the addPart scene. */
public class addPartController {
    public RadioButton inHouseButton;
    public RadioButton outsourcedButton;
    public ToggleGroup toggleGroup;
    public TextField partIDField;
    public TextField partMax;
    public TextField partPriceField;
    public TextField partInvField;
    public TextField partNameField;
    public TextField partMin;
    public Button partSaveButton;
    public Button partCancelButton;
    public TextField mOrCField;
    public Label mOrCLabel;



    /** This method changes the last field label to Company Name if the Outsourced radio button is selected. */
    public void onOutsourced(ActionEvent actionEvent) {mOrCLabel.setText("Company Name");}

    /** This method changes the last field label to Machine ID if the In-House radio button is selected. */
    public void onInHouse(ActionEvent actionEvent) {mOrCLabel.setText("Machine ID");}

    /** This method firsts asks the user if they want to save. If the user selects OK, it will attempt to take the input
     * from the text fields and create a new InHouse or Outsourced part depending on which radio button is selected. It
     * will also validate the inputted values for the fields and display an error if any fields are invalid. If the part
     * is valid it will add it to the list of parts. It will the load and show mainScreen.
     * @param actionEvent event that the user clicks the save button.
     * @throws IOException from toMainScreen() method.
     */
    public void onPartSave(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning");
        alert.setContentText("Are you sure you want to save?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                if (inHouseButton.isSelected()) {
                    int partID = makePartID();
                    String partName = partNameField.getText();
                    double partPrice = Double.parseDouble(partPriceField.getText());
                    int partInv = Integer.parseInt(partInvField.getText());
                    int partMinn = Integer.parseInt(partMin.getText());
                    int partMaxx = Integer.parseInt(partMax.getText());
                    int machineID = Integer.parseInt(mOrCField.getText());
                    if (partMinn < partInv && partInv < partMaxx) {
                        InHouse newPart = new InHouse(partID, partName, partPrice, partInv, partMinn, partMaxx, machineID);
                        addPart(newPart);
                        toMainScreen(actionEvent);
                    } else {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Value in min field must be less than value in max field. Inv field value must be between min and max.");
                        alert.show();
                    }
                }
                if (outsourcedButton.isSelected()) {
                    int partID = makePartID();
                    String partName = partNameField.getText();
                    double partPrice = Double.parseDouble(partPriceField.getText());
                    int partInv = Integer.parseInt(partInvField.getText());
                    int partMinn = Integer.parseInt(partMin.getText());
                    int partMaxx = Integer.parseInt(partMax.getText());
                    String companyName = mOrCField.getText();
                    if (partMinn < partInv && partInv < partMaxx) {
                        Outsourced newPart = new Outsourced(partID, partName, partPrice, partInv, partMinn, partMaxx, companyName);
                        addPart(newPart);
                        toMainScreen(actionEvent);
                    } else {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Value in min field must be less than value in max field. Inv field value must be between min and max.");
                        alert.show();
                    }
                }
            }  catch (NumberFormatException e) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please enter valid values for each input field!");
                alert.show();
            }
        }
    }

    /** This method will confirm if the customer wants to cancel, and if the user selects OK it will load and show mainScreen.
     *
     * @param actionEvent event that user clicks the cancel button.
     * @throws IOException from toMainScreen()
     */
    public void onPartCancel(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning");
        alert.setContentText("Are you sure you want to cancel?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            toMainScreen(actionEvent);
        }
    }

    /**
     * This method creates a unique Part ID by comparing the ID to each part and incrementing by one to ensure the returned
     * ID is always the largest of all parts
     * @return unique ID
     */
    public int makePartID() {
        int id = 0;
        for (int i = 0; i < Inventory.getAllParts().size(); i++) {
            if (id <= getAllParts().get(i).getId()) {
                id = getAllParts().get(i).getId() + 1;
            }
        }
        return id;
    }



}

