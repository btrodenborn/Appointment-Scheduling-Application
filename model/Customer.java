package com.example.c195_finalproject.model;

import com.example.c195_finalproject.DAO.AppointmentsDAO;
import com.example.c195_finalproject.DAO.CustomersDAO;
import com.example.c195_finalproject.DAO.FirstLevelDivDAO;

import java.sql.SQLException;

/**This is the public class for customer.*/
public class Customer {
    private int customerID;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private int divisionID;

    public Customer(int customerID, String customerName, String address, String postalCode, String phone, int divisionID) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionID = divisionID;
    }

    /**
     * This method returns the customerID.
     * @return customerID.
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * This method sets the customerID.
     * @param customerID
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * This method returns the customer name.
     * @return customerName.
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * This method sets the customer name.
     * @param customerName
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * This method returns the customer address.
     * @return address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * This method sets the customer address.
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * This method returns the customer postal code.
     * @return postalCode.
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * This method sets the customer postal code.
     * @param postalCode
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * This method returns the customer phone number.
     * @return phone.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * This method sets the customer phone number.
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * This method returns the customer divisionID.
     * @return divisionID.
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * This method sets the customer divisionID.
     * @param divisionID
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     * This method returns a string.
     * @return string.
     */
    @Override
    public String toString(){
        return (Integer.toString(getCustomerID()));
    }

}
