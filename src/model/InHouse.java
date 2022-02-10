package model;

/** This class inherits from the Part class and creates InHouse Part */
public class InHouse extends Part {

    private static int machineId;

    /** Constructor with attributes for InHouse part */
  public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
      super(id, name, price, stock, min, max);
      this.machineId = machineID;}

    /** This method sets the MachineID for an InHouse Part
     *  @param machineId the machine ID */
    public void setMachineID(int machineId) {this.machineId = machineId;}

     /** This method gets the machineID from an InHouse Part
      *  @return machineID */
    public static int getMachineID() {return machineId;}
}
