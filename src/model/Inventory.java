package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This creates the inventory class housing methods for parts and products */

public class Inventory {

    /**
     * This creates a list of parts.
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /**
     * This creates a list of products.
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * This method adds a new part to the list of parts.
     *
     * @param newPart the new part created.
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * This method adds a new product to the list of products.
     *
     * @param newProduct the new product created.
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * This method locates a part by the part ID.
     *
     * @param partId the part's ID.
     * @return null if part is not located.
     */
    public static Part lookupPart(int partId) {
        ObservableList<Part> allParts = getAllParts();
        for (Part pID : allParts) {
            if (pID.getId() == partId) {
                return pID;
            }

        }
        return null;
    }

    /**
     * This method locates a product by the product ID.
     *
     * @param productID the product's ID.
     * @return null if product is not located.
     */
    public static Product lookupProduct(int productID) {
        ObservableList<Product> allProducts = getAllProducts();
        for (Product pID : allProducts) {
            if (pID.getID() == productID) {
                return pID;
            }

        }
        return null;
    }

    /**
     * This method locates a part by the part name.
     *
     * @param partName the part's partial or full name.
     * @return list of parts that contain the input.
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> namedParts = FXCollections.observableArrayList();
        ObservableList<Part> allParts = getAllParts();
        for (Part pName : allParts) {
            if (pName.getName().contains(partName)) {
                namedParts.add(pName);
            }

        }
        return namedParts;
    }

    /**
     * This method locates a product by the product name.
     *
     * @param productName the product's partial or full name.
     * @return list of products that contain the input.
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> namedProducts = FXCollections.observableArrayList();
        ObservableList<Product> allProducts = getAllProducts();
        for (Product prodName : allProducts) {
            if (prodName.getName().contains(productName)) {
                namedProducts.add(prodName);
            }
        }
        return namedProducts;
    }

    /**
     * This method updates a part in the parts list by replacing it by index
     *
     * @param index        the original part's index in the list
     * @param selectedPart the new part
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * This method updates a product in the products list by replacing it by index
     *
     * @param index           the original product's index in the list
     * @param selectedProduct the new product
     */
    public static void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

    /**
     * This method updates a part in the parts list by replacing it by ID
     *
     * @param modPart the new part
     */
    public static void partUpdateByID(Part modPart) {
        for (int i = 0; i < allParts.size(); i++) {
            if (modPart.getId() == allParts.get(i).getId()) {
                updatePart(i, modPart);
            }
        }
    }

    /**
     * This method updates a product in the products list by replacing it by ID
     *
     * @param modProd the new product
     */
    public static void productUpdateByID(Product modProd) {
        for (int i = 0; i < allProducts.size(); i++) {
            if (modProd.getID() == allProducts.get(i).getID()) {
                updateProduct(i, modProd);
            }
        }
    }

    /**
     * This method returns true if a selected part is deleted
     *
     * @param selectedPart the selected part to be deleted
     */
    public static boolean deletePart(Part selectedPart) {
        return true;
    }

    /**
     * This method returns true if a selected product is deleted
     *
     * @param selectedProduct the selected product to be deleted
     */
    public static boolean deleteProduct(Product selectedProduct) {
        return true;
    }

    /**
     * This method removes a selected product from the product list
     *
     * @param selectedProduct the selected product to be removed
     */
    public static void removeProduct(Product selectedProduct) {
        allProducts.remove(selectedProduct);
    }

    /**
     * This method removes a selected part from the parts list
     *
     * @param selectedPart the selected product to be removed
     */
    public static void removePart(Part selectedPart) {
        allParts.remove(selectedPart);
    }

    /**
     * This method gets all parts in the parts list
     *
     * @return all parts in the list
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * This method gets all products in the products list
     *
     * @return all products in the list
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }




    /**
     * This method creates a unique Product ID by checking all products and incrementing the ID by 1 if it already exists
     *
     * @return unique ID
     */
    public static int makeProductID() {
        int id = 0;
        for (Product product : getAllProducts()) {
            if (id == product.getID()) {
                id += 1;
            }
        }
        return id;
    }
}





