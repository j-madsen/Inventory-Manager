package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This creates the product class which houses methods for products */
public class Product {

    /** This creates a list of associated parts for the product */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /** Constructor with attributes for a product */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /** This method sets a product's ID
     *
     * @param productID the product ID
     */
    public void setID(int productID) {this.id = productID;}

    /** This method sets a product's name
     *
     * @param productName the product name
     */
    public void setName(String productName) {this.name = productName;}

    /** This method sets a product's price
     *
     * @param productPrice the product price
     */
    public void setPrice (double productPrice) {this.price = productPrice;}

    /** This method sets a product's stock
     *
     * @param productStock the product stock
     */
    public void setStock (int productStock) {this.stock = productStock;}

    /** This method sets a product's minimum stock
     *
     * @param productMin the product minimum stock
     */
    public void setMin (int productMin) {this.min = productMin;}

    /** This method sets a product's maximum stock
     *
     * @param productMax the product's maximum stock
     */
    public void setMax (int productMax) {this.max = productMax;}

    /** This method gets a product's ID
     *
     * @return product ID
     */
    public int getID() {return id;}

    /** This method gets a product's name
     *
     * @return product name
     */
    public String getName() {return name;}

    /** This method gets a product's price
     *
     * @return product price
     */
    public double getPrice() {return price;}

    /** This method gets a product's stock
     *
     * @return product stock
     */
    public int getStock() {return stock;}

    /** This method gets a product's minimum stock
     *
     * @return product minimum stock
     */
    public int getMin() {return min;}

    /** This method gets a product's maximum stock
     *
     * @return product maximum stock
     */
    public int getMax() {return max;}

    /** This method adds a part to a product's associated parts list
     *
     * @param part an associated part
     */
    public void addAssociatedPart(Part part) {associatedParts.add(part);}

    /** This method returns true if a product's associated part is deleted
     *
     * @return true
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {return true;}

    /** This method gets all associated parts for a product
     *
     * @return all associated parts
     */
    public ObservableList<Part> getAllAssociatedParts() {return associatedParts;}
}
