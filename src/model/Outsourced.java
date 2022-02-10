package model;

/** This class inherits from the Part class and creates Outsourced Part */
public class Outsourced extends Part {

    private static String companyName;

    /** Constructor with attributes for Outsourced part */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        Outsourced.companyName = companyName;
    }
    /** This method sets the company name for an Outsourced Part
     *  @param companyName the company name */
    public void setCompanyName(String companyName) {this.companyName = companyName;}

    /** This method gets the company name from an Outsourced Part
     *  @return company name */
    public static String getCompanyName() {return companyName;}
}
