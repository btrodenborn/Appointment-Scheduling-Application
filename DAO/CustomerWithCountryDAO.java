package com.example.c195_finalproject.DAO;

import com.example.c195_finalproject.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class CustomerWithCountryDAO extends Customer {
    public CustomerWithCountryDAO(int customerID, String customerName, String address, String postalCode, String phone, int divisionID, String country, String division) {
        super(customerID, customerName, address, postalCode, phone, divisionID);
        this.country=country;
        this.division=division;
    }

    /**
     * This method returns an observable list of all customers with their country and first level division.
     * @return getAllCustomerWithCountry.
     * @throws SQLException
     */
    public static ObservableList<Customer> getAllCustomerWithCountry() throws SQLException {
        ObservableList<Customer> getAllCustomerWithCountry = FXCollections.observableArrayList();
        String country;
        String division;
        for(Customer c : CustomersDAO.getAllCustomers()){
            country = FirstLevelDivDAO.getCountry(c.getDivisionID());
            division = FirstLevelDivDAO.getDivision(c.getDivisionID());
            CustomerWithCountryDAO newCustomerWithCountry = new CustomerWithCountryDAO(c.getCustomerID(), c.getCustomerName(), c.getAddress(), c.getPostalCode(), c.getPhone(), c.getDivisionID(), country, division);
            getAllCustomerWithCountry.add(newCustomerWithCountry);
        }
        return getAllCustomerWithCountry;
    }

    /**
     * This method returns the customer's country.
     * @return country.
     */
    public String getCountry() {
        return country;
    }

    /**
     * This method sets the customer's country.
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * This method returns the customer's division.
     * @return division.
     */
    public String getDivision() {
        return division;
    }

    /**
     * This method sets the customer's division.
     * @param division
     */
    public void setDivision(String division) {
        this.division = division;
    }

    private String country;
    private String division;
}
