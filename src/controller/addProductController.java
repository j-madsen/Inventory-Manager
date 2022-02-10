package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static controller.mainController.toMainScreen;
import static model.Inventory.*;

/** This class houses the methods used to work with the addProduct scene. */
public class addProductController implements Initializable {
    public TableView allPartsAvail;
    public TableColumn partIDAvail;
    public TableColumn partNameAvail;
    public TableColumn invAvail;
    public TableColumn priceAvail;
    public TableView associatedParts;
    public TableColumn partIDAssoc;
    public TableColumn partNameAssoc;
    public TableColumn invAssoc;
    public TableColumn priceAssoc;
    public TextField idField;
    public TextField nameField;
    public TextField invField;
    public TextField priceField;
    public TextField maxField;
    public TextField minField;
    public Button partAdd;
    public Button partRemove;
    public Button partSave;
    public Button cancelButton;
    public TextField searchBar;
    public Button searchSubmit;
    public Pane paneID;

    /**
     * This creates a list to house associated parts.
     */
    private ObservableList<Part> partsAssoc = FXCollections.observableArrayList();

    /**
     * This sets the initial values of the all parts table and associated parts table
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allPartsAvail.setItems(Inventory.getAllParts());
        partIDAvail.setCellValueFactory(new PropertyValueFactory<>("Id"));
        partNameAvail.setCellValueFactory(new PropertyValueFactory<>("name"));
        invAvail.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceAvail.setCellValueFactory(new PropertyValueFactory<>("price"));


        partIDAssoc.setCellValueFactory(new PropertyValueFactory<>("Id"));
        partNameAssoc.setCellValueFactory(new PropertyValueFactory<>("name"));
        invAssoc.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceAssoc.setCellValueFactory(new PropertyValueFactory<>("price"));
        associatedParts.setItems(partsAssoc);
    }

    /**
     * This method takes a selected part from the all parts table and adds it to the associated parts table. If no part
     * is selected the user is alerted to select a part.
     *
     * @param actionEvent event that the user clicks the add button.
     */
    public void onAdd(ActionEvent actionEvent) {
        Part selectedPart = (Part) allPartsAvail.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            partsAssoc.add(selectedPart);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select a part!");
            alert.show();
        }
    }

    /**
     * This method takes a selected part from the associated parts table and removes it from the table if the user confirms.
     * If no part is selected the user is alerted to select a part.
     *
     * @param actionEvent event that the user clicks the remove button.
     */
    public void onRemove(ActionEvent actionEvent) {
        Part selectedPart = (Part) associatedParts.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select a part!");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setContentText("Are you sure you want to remove the part?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                partsAssoc.remove(selectedPart);
            }
        }
    }

    /** This method firsts asks the user if they want to save. If the user selects OK, it will attempt to take the input
     * from the text fields and create a new product. It will also validate the inputted values for the fields and display
     * an error if any fields are invalid. If the product is valid it will add it to the list of products. It will also add
     * the associated parts to the associated parts list for the product. It will the load and show mainScreen.
     * @param actionEvent event that the user clicks the save button.
     * @throws IOException from toMainScreen() method.
     */
    public void onSave(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning");
        alert.setContentText("Are you sure you want to save?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                int productID = makeProductID();
                String productName = nameField.getText();
                double productPrice = Double.parseDouble(priceField.getText());
                int productInv = Integer.parseInt(invField.getText());
                int productMin = Integer.parseInt(minField.getText());
                int productMax = Integer.parseInt(maxField.getText());
                if (productMin < productInv && productInv < productMax) {
                    Product newProduct = new Product(productID, productName, productPrice, productInv, productMin, productMax);
                    for (Part part : partsAssoc) {
                        newProduct.addAssociatedPart(part);
                    }
                    addProduct(newProduct);
                    toMainScreen(actionEvent);
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Value in min field must be less than value in max field. Inv field value must be between min and max.");
                    alert.show();
                }
            } catch (NumberFormatException | IOException e) {
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
        public void onCancel (ActionEvent actionEvent) throws IOException {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setContentText("Are you sure you want to cancel?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                toMainScreen(actionEvent);
            }
        }

    /** This method grabs the user inputted text in the search field upon the user clicking the search button, and checks
     * if the input partially or fully matches a part name in the all parts table or if an inputted number matches a part ID, and it
     * displays the part(s) in the all parts table if found. If no parts are found the user is alerted, and if there is no input all parts are
     * displayed.
     * @param actionEvent event that user clicks the submit button
     */
        public void onSubmit (ActionEvent actionEvent){
            String query = searchBar.getText();
            ObservableList<Part> parts = Inventory.lookupPart(query);
            try {
                if (parts.size() == 0) {
                    int ID = Integer.parseInt(query);
                    Part partID = lookupPart(ID);
                    if (partID != null) {
                        parts.add(partID);
                    }
                }
                allPartsAvail.setItems(parts);
            } catch (NumberFormatException ignored) {
            }

            if (parts.size() == 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Search Results");
                alert.setContentText("No parts found.");
                alert.show();

            }
        }

    /**
     * This method creates a unique product ID by comparing the ID to each product and incrementing by one to ensure the returned
     * ID is always the largest of all parts
     * @return unique ID
     */
    public int makeProductID() {
        int id = 0;
        for (int i = 0; i < Inventory.getAllProducts().size(); i++) {
            if (id <= getAllProducts().get(i).getID()) {
                id = getAllProducts().get(i).getID() + 1;
            }
        }
        return id;
    }
    }
