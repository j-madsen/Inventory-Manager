package controller;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static model.Inventory.*;

/** This class houses the methods used to work with the main scene.
 */
public class mainController implements Initializable {
    public TableView partsTable;
    public TableColumn partID;
    public TableColumn partName;
    public TableColumn partInventory;
    public TableColumn partsPrice;
    public TableView productsTable;
    public TableColumn productID;
    public TableColumn productName;
    public TableColumn productInventory;
    public TableColumn productsPrice;
    public Button addButton;
    public TextField productsSearch;
    public TextField partsSearch;
    public Button partsSearchButton;
    public Button productSearchButton;
    public Button partModifyButton;
    public Button partDeleteButton;
    public Button prodDeleteButton;
    public Button addProdButton;
    public Button prodModifyButton;
    public Button exitButton;

    /**
     * This method class sets the initial values for the parts and products table.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partsTable.setItems(getAllParts());
        productsTable.setItems(getAllProducts());

        partID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        productID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productsPrice.setCellValueFactory(new PropertyValueFactory<>("price"));


    }


    /**
     * This method grabs a selected part from the part table or returns nothing if no part is selected
     *
     * @return a selected part
     * @return nothing if no selection
     */
    public Part getSelectedPart() {
        if ((Part) partsTable.getSelectionModel().getSelectedItem() != null) {
            return (Part) partsTable.getSelectionModel().getSelectedItem();
        } else {
            return null;
        }
    }

    /**
     * This method grabs a selected product from the product table or returns nothing if no product is selected
     *
     * @return a selected product
     * @return nothing if no selection
     */
    public Product getSelectedProduct() {
        if ((Product) productsTable.getSelectionModel().getSelectedItem() != null) {
            return (Product) productsTable.getSelectionModel().getSelectedItem();
        } else {
            return null;
        }
    }


    /**
     * This method loads AddPart and shows the stage upon the user clicking the add button
     *
     * @param actionEvent event that user clicks the add button
     * @throws IOException from FXMLLoader.load
     */
    public void onAddPartButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(mainController.class.getResource("/view/AddPart.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }




    /** This method loads ModifyPart and shows the stage upon the user clicking the modify button if a part is selected.
     * It checks if the user has selected a part, and if no part is selected it alerts the user to select one.
     * @param actionEvent event that user clicks the modify button
     * @throws IOException from FXMLLoader.load
     */
    public void onPartModify(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ModifyPart.fxml"));
        loader.load();

        modifyPartController mpController = loader.getController();
        Part selectedPart = getSelectedPart();

        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select a part to modify!");
            alert.show();
        } else {
            mpController.receiveData(selectedPart);
            Parent scene = loader.getRoot();
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /** This method checks if a user has selected a part. If a part is selected it confirms if the user wants to delete
     * the part, and if the user confirms it removes the part from the parts list. If there is no part selected it alerts
     * the user to select a part.
     * @param actionEvent event that user clicks the delete button
     */
    public void onPartDelete(ActionEvent actionEvent) {
        Part selectedPart = getSelectedPart();
        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select a part!");
            alert.showAndWait();
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setContentText("Are you sure you want to delete the part?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                removePart(selectedPart);
            }
        }
    }

    /** This method grabs the user inputted text in the search field upon the user clicking the search button, and checks
     * if the input partially or fully matches a part name in the parts table or if an inputted number matches a part ID, and it
     * displays the part(s) if found. If no parts are found the user is alerted, and if there is no input all parts are
     * displayed.
     * @param actionEvent event that user clicks the submit button
     */
    public void onPartsSearch(ActionEvent actionEvent) {
        String query = partsSearch.getText();
        ObservableList<Part> parts = Inventory.lookupPart(query);
        try {
            if (parts.size() == 0) {
                int ID = Integer.parseInt(query);
                Part partID = lookupPart(ID);
                if (partID != null) {
                    parts.add(partID);
                }
            }
            partsTable.setItems(parts);
        } catch (NumberFormatException ignored) {
        }

        if (parts.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Search Results");
            alert.setContentText("No parts found.");
            alert.show();
        }
    }

    /** This method loads AddProduct and shows the stage upon the user clicking the add button
     *
     * @param actionEvent event that user clicks the add button
     * @throws IOException from FXMLLoader.load
     */
    public void onAddProdButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(mainController.class.getResource("/view/addProduct.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /** This method loads ModifyProduct and shows the stage upon the user clicking the modify button if a product is selected.
     * It checks if the user has selected a product, and if no product is selected it alerts the user to select one.
     * @param actionEvent event that user clicks the modify button
     * @throws IOException from FXMLLoader.load
     */
    public void onProdModify(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ModifyProduct.fxml"));
        loader.load();

        modifyProductController mprodController = loader.getController();
        Product selectedProduct = getSelectedProduct();

        if (selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select a product to modify!");
            alert.show();
        } else {
            mprodController.receiveData(selectedProduct);
            Parent scene = loader.getRoot();
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }


    /** This method checks if a user has selected a product. If a product is selected it confirms if the user wants to delete
     * the product, and if the user confirms it removes the product from the product list. If there is no product selected it alerts
     * the user to select a product. If a product has an associated part it tells the user that it can't be deleted.
     * @param actionEvent event that user clicks the delete button
     */
    public void onProdDeleteButton(ActionEvent actionEvent) {
        Product selectedProduct = getSelectedProduct();
        if (selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select a product!");
            alert.showAndWait();
        }
        else if (selectedProduct.getAllAssociatedParts().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setContentText("Are you sure you want to delete the product?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.removeProduct(selectedProduct);
            }

        } else { Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("You cannot delete a product that has an associated part.");
            alert.showAndWait();
        }
    }
    /** This method grabs the user inputted text in the search field upon the user clicking the search button, and checks
     * if the input partially matches a product name in the product table or if an inputted number matches a product ID, and it
     * displays the product(s) if found. If no products are found the user is alerted, and if there is no input all products are
     * displayed.
     * @param actionEvent event that user clicks the submit button
     */
    public void onProductsSearch(ActionEvent actionEvent) {
        String query = productsSearch.getText();
        ObservableList<Product> products = Inventory.lookupProduct(query);
        try {
            if (products.size() == 0) {
                int ID = Integer.parseInt(query);
                Product productID = lookupProduct(ID);
                if (productID != null) {
                    products.add(productID);
                }
            }
            productsTable.setItems(products);
        } catch (NumberFormatException ignored) {
        }

        if (products.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Search Results");
            alert.setContentText("No Products found.");
            alert.show();
        }
    }

    /** This method loads mainScreen and shows the stage.
     *
     * @param actionEvent user event that triggers the method
     * @throws IOException from FXMLLoader.load
     */
    public static void toMainScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(mainController.class.getResource("/view/mainScreen.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /** This method confirms if the user wants to exit the application. If the user selected OK the application
     * is terminated.
     * @param actionEvent the user clicking the exit button.
     */
    public void onExitButton(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning");
        alert.setContentText("Are you sure you want to exit?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Platform.exit();
        }
    }
}








