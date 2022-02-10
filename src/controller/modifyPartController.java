package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import model.InHouse;
import model.Outsourced;
import model.Part;
import java.io.IOException;
import java.util.Optional;

import static controller.mainController.toMainScreen;
import static model.Inventory.*;

/** This class houses the methods used to work with the modifyPart scene. */
public class modifyPartController {
    public AnchorPane partForm;
    public RadioButton inHouseButton;
    public ToggleGroup toggleGroup;
    public RadioButton outsourcedButton;
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


    /** This method receives the data from the selected part on mainScreen and sets the field values depending on the part selected.
     * It also checks if the part is In-House or Outsourced and sets the last field label accordingly.
     * @param part selected part on mainScreen.
     */
    public void receiveData(Part part) {
        if (part instanceof InHouse) {
            mOrCField.setText(String.valueOf(InHouse.getMachineID()));
        } else {
            outsourcedButton.setSelected(true);
            mOrCField.setText(Outsourced.getCompanyName());
            mOrCLabel.setText("Company Name");
        }
        partIDField.setText(String.valueOf(part.getId()));
        partNameField.setText(part.getName());
        partInvField.setText(String.valueOf(part.getStock()));
        partPriceField.setText(String.valueOf(part.getPrice()));
        partMax.setText(String.valueOf(part.getMax()));
        partMin.setText(String.valueOf(part.getMin()));
    }

    /** This method changes the last field label to Machine ID if In-House radio button is selected.*/
    public void onInHouse(ActionEvent actionEvent) {mOrCLabel.setText("Machine ID");}

    /** This method changes the last field label to Company Name if the Outsourced radio button is selected. */
    public void onOutsourced(ActionEvent actionEvent) {mOrCLabel.setText("Company Name");}

    /** This method firsts asks the user if they want to save. If the user selects OK, it will attempt to take the input
     * from the text fields and create a new InHouse or Outsourced part depending on which radio button is selected. It
     * will also validate the inputted values for the fields and display an error if any fields are invalid. If the part
     * is valid it will replace the selected part by ID. It will the load and show mainScreen.
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
                    int partID = Integer.valueOf(partIDField.getText());
                    String partName = partNameField.getText();
                    double partPrice = Double.parseDouble(partPriceField.getText());
                    int partInv = Integer.parseInt(partInvField.getText());
                    int partMinn = Integer.parseInt(partMin.getText());
                    int partMaxx = Integer.parseInt(partMax.getText());
                    int machineID = Integer.parseInt(mOrCField.getText());
                    if (partMinn < partInv && partInv < partMaxx) {
                        Part modPart = new InHouse(partID, partName, partPrice, partInv, partMinn, partMaxx, machineID);
                        partUpdateByID(modPart);
                        toMainScreen(actionEvent);
                    } else {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Value in min field must be less than value in max field. Inv field value must be between min and max.");
                        alert.show();
                    }
                }
                    if (outsourcedButton.isSelected()) {
                        int partID = Integer.valueOf(partIDField.getText());
                        String partName = partNameField.getText();
                        double partPrice = Double.parseDouble(partPriceField.getText());
                        int partInv = Integer.parseInt(partInvField.getText());
                        int partMinn = Integer.parseInt(partMin.getText());
                        int partMaxx = Integer.parseInt(partMax.getText());
                        String companyName = mOrCField.getText();
                        if (partMinn < partInv && partInv < partMaxx) {
                            Outsourced modPart = new Outsourced(partID, partName, partPrice, partInv, partMinn, partMaxx, companyName);
                            partUpdateByID(modPart);
                            toMainScreen(actionEvent);
                        } else {
                            alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setContentText("Value in min field must be less than value in max field. Inv field value must be between min and max.");
                            alert.show();
                        }

                }
            } catch (NumberFormatException e) {
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


}
