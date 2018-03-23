package com.example.divyanshu.vehicleregistry;

import java.util.Date;

/**
 * Created by divyanshu on 11/3/18.
 */

public class Vehicle {

    private String rcValidity;
    private String insuranceValidity;
    private String pollutionValidity;
    private String owner;
    private String registered_no;
    private String model_no;
    private String chassis_no;
    private String engine_no;
    private Date date_of_registration;
    private String fuel_type;

    public String getRcValidity() {
        return rcValidity;
    }

    public String getInsuranceValidity() {
        return insuranceValidity;
    }

    public String getPollutionValidity() {
        return pollutionValidity;
    }

    public String getOwner() {
        return owner;
    }

    public String getRegistered_no() {
        return registered_no;
    }

    public String getModel_no() {
        return model_no;
    }

    public String getChassis_no() {
        return chassis_no;
    }

    public String getEngine_no() {
        return engine_no;
    }


    public Date getDate_of_registration() {
        return date_of_registration;
    }

    public String getFuel_type() {
        return fuel_type;
    }

    public void setRcValidity(String rcValidity) {
        this.rcValidity = rcValidity;
    }

    public void setInsuranceValidity(String insuranceValidity) {
        this.insuranceValidity = insuranceValidity;
    }

    public void setPollutionValidity(String pollutionValidity) {
        this.pollutionValidity = pollutionValidity;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setRegistered_no(String registered_no) {
        this.registered_no = registered_no;
    }

    public void setModel_no(String model_no) {
        this.model_no = model_no;
    }

    public void setChassis_no(String chassis_no) {
        this.chassis_no = chassis_no;
    }

    public void setEngine_no(String engine_no) {
        this.engine_no = engine_no;
    }

    public void setDate_of_registration(Date date_of_registration) {
        this.date_of_registration = date_of_registration;
    }

    public void setFuel_type(String fuel_type) {
        this.fuel_type = fuel_type;
    }
}
