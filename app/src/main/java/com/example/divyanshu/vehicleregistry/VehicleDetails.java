package com.example.divyanshu.vehicleregistry;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by divyanshu on 25/3/18.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VehicleDetails {

    private String rcValidity;
    private String insuranceValidity;
    private String pollutionValidity;
    private String make;
    private String modelType;
    private String color;
    private String vin;
    private String chassis;
    private String fuelType;

    public String getRcValidity() {
        return rcValidity;
    }

    public void setRcValidity(String rcValidity) {
        this.rcValidity = rcValidity;
    }

    public String getInsuranceValidity() {
        return insuranceValidity;
    }

    public void setInsuranceValidity(String insuranceValidity) {
        this.insuranceValidity = insuranceValidity;
    }

    public String getPollutionValidity() {
        return pollutionValidity;
    }

    public void setPollutionValidity(String pollutionValidity) {
        this.pollutionValidity = pollutionValidity;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getChassis() {
        return chassis;
    }

    public void setChassis(String chassis) {
        this.chassis = chassis;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

}
