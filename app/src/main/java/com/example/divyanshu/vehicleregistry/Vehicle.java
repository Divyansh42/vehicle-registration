package com.example.divyanshu.vehicleregistry;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.security.acl.Owner;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Vehicle {


    private String vin;
    private VehicleDetails vehicleDetails;
   /* private VehicleStatus vehicleStatus;
    private Owner owner;
    private Manufacturer manufacturer;
    private List<VehicleDealer> vehicleDealers;
    private List<VehicleTransferLogEntry> vehicleTransferLogEntries;
*/

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public VehicleDetails getVehicleDetails() {
        return vehicleDetails;
    }

    public void setVehicleDetails(VehicleDetails vehicleDetails) {
        this.vehicleDetails = vehicleDetails;
    }

    /*public VehicleStatus getVehicleStatus() {
        return vehicleStatus;
    }

    public void setVehicleStatus(VehicleStatus vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public List<VehicleTransferLogEntry> getVehicleTransferLogEntries() {
        return vehicleTransferLogEntries;
    }

    public void setVehicleTransferLogEntries(List<VehicleTransferLogEntry> vehicleTransferLogEntries) {
        this.vehicleTransferLogEntries = vehicleTransferLogEntries;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public List<VehicleDealer> getVehicleDealers() {
        return vehicleDealers;
    }

    public void setVehicleDealers(List<VehicleDealer> vehicleDealers) {
        this.vehicleDealers = vehicleDealers;
    }*/
}
