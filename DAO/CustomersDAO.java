package com.example.c195_finalproject.DAO;

import com.example.c195_finalproject.helper.JDBC;

import com.example.c195_finalproject.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**This public class accesses the countries table in the database.*/
public class CustomersDAO {

    /**
     * This method accesses the customers table in the database and stores each customer in an observable list.
     * @return allCustomers.
     * @throws SQLException
     */
    public static ObservableList<Customer> getAllCustomers() throws SQLException {
        ObservableList<Customer> allCustomers= FXCollections.observableArrayList();
        String sql = "SELECT * FROM CUSTOMERS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int customerID = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phoneNumber = rs.getString("Phone");
            int divisionID = rs.getInt("Division_ID");

            Customer customerResults = new Customer(customerID, customerName, address, postalCode, phoneNumber, divisionID);
            allCustomers.add(customerResults);
        }
        return allCustomers;
    }

    /**
     * This method inserts a new customer into the customers table in the database.
     * @param customerID
     * @param customerName
     * @param address
     * @param postalCode
     * @param phoneNumber
     * @param divisionID
     * @throws SQLException
     */
    public static void insert(int customerID, String customerName, String address, String postalCode, String phoneNumber,int divisionID) throws SQLException{

        String sql = "INSERT INTO CUSTOMERS (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES(?,?,?,?,?,?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerID);
        ps.setString(2, customerName);
        ps.setString(3, address);
        ps.setString(4, postalCode);
        ps.setString(5, phoneNumber);
        ps.setInt(6, divisionID);
        ps.executeUpdate();
    }

    /**
     * This method deletes a customer from the database based on the customerID.
     * @param customerID
     * @throws SQLException
     */
    public static void delete(int customerID) throws SQLException{
        String sql = "DELETE FROM CUSTOMERS WHERE CUSTOMER_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerID);
        ps.executeUpdate();
    }

    /**
     * This method changes a customer in the customers table in the database.
     * @param customerID
     * @param customerName
     * @param address
     * @param postalCode
     * @param phoneNumber
     * @param divisionID
     * @throws SQLException
     */
    public static void update(int customerID, String customerName, String address, String postalCode, String phoneNumber,int divisionID) throws SQLException {
        String sql = "UPDATE CUSTOMERS SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phoneNumber);
        ps.setInt(5, divisionID);
        ps.setInt(6, customerID);
        ps.executeUpdate();
    }

    /**
     * This method returns a customer name from the database based on the customerID.
     * @param customerID
     * @return customerName.
     * @throws SQLException
     */
    public static String idToName(int customerID) throws SQLException {
        String customerName = "";
        String sql = "SELECT CUSTOMER_NAME FROM CUSTOMERS WHERE CUSTOMER_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerID);
        ResultSet rs= ps.executeQuery();
        while (rs.next()){
            customerName = rs.getString("Customer_Name");
        }
        return customerName;
    }

    /**
     * This method returns a customer address from the database based on the customerID.
     * @param customerID
     * @return customerAddress.
     * @throws SQLException
     */
    public static String idToAddress(int customerID) throws SQLException {
        String customerAddress = "";
        String sql = "SELECT ADDRESS FROM CUSTOMERS WHERE CUSTOMER_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            customerAddress = rs.getString("Address");
        }
        return customerAddress;
    }

    /**
     * This method returns a customer postal code from the database based on the customerID.
     * @param customerID
     * @return postalCode.
     * @throws SQLException
     */
    public static String idToPostal(int customerID) throws SQLException {
        String postalCode = "";
        String sql = "SELECT POSTAL_CODE FROM CUSTOMERS WHERE CUSTOMER_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            postalCode = rs.getString("Postal_Code");
        }
        return postalCode;
    }

    /**
     * This method returns a customer phone number from the database based on the customerID.
     * @param customerID
     * @return phone.
     * @throws SQLException
     */
    public static String idToPhone(int customerID) throws SQLException {
        String phone = "";
        String sql = "SELECT PHONE FROM CUSTOMERS WHERE CUSTOMER_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            phone = rs.getString("Phone");
        }
        return phone;
    }

    /**
     * This method returns a customer divisionID from the database based on the customerID.
     * @param customerID
     * @return divID
     * @throws SQLException
     */
    public static int idToDivID(int customerID) throws SQLException {
        int divID = 0;
        String sql = "SELECT DIVISION_ID FROM CUSTOMERS WHERE CUSTOMER_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            divID = rs.getInt("Division_ID");
        }
        return divID;
    }

    /**
     * This method returns a unique customerID.
     * @return max+1.
     * @throws SQLException
     */
    public static int getNewCustomerID() throws SQLException {
        int max=0;
        for(Customer t: CustomersDAO.getAllCustomers()) {
            if(t.getCustomerID() > max) {
                max = t.getCustomerID();
            }
        }
        return max+1;
    }

    /**
     * This method returns a count of all the customers based in the US.
     * @return customerCount.
     * @throws SQLException
     */
    public static int getCustomerInUS() throws SQLException {
        int customerCount = 0;
        for(Customer t: CustomersDAO.getAllCustomers()) {
            if(FirstLevelDivDAO.getCountryID(t.getDivisionID()) == 1){
                customerCount++;
            }
        }
        return customerCount;
    }

    /**
     * This method returns a count of all the customers based in the UK.
     * @return customerCount.
     * @throws SQLException
     */
    public static int getCustomerInUK() throws SQLException {
        int customerCount = 0;
        for(Customer t: CustomersDAO.getAllCustomers()) {
            if(FirstLevelDivDAO.getCountryID(t.getDivisionID()) == 2){
                customerCount++;
            }
        }
        return customerCount;
    }

    /**
     * This method returns a count of all the customers based in Canada.
     * @return customerCount.
     * @throws SQLException
     */
    public static int getCustomerInCanada() throws SQLException {
        int customerCount = 0;
        for(Customer t: CustomersDAO.getAllCustomers()) {
            if(FirstLevelDivDAO.getCountryID(t.getDivisionID()) == 3){
                customerCount++;
            }
        }
        return customerCount;
    }
}
