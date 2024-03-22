package com.example.c195_finalproject.DAO;

import com.example.c195_finalproject.helper.JDBC;
import com.example.c195_finalproject.model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**This public class accesses the contacts table in the database.*/
public class ContactsDAO {
    /**This is a static observable list of all contacts from the database.*/
    private static ObservableList<Contact> allContacts = FXCollections.observableArrayList();

    /**
     * This method accesses the contacts table in the database and stores each contact in an observable list.
     * @return allContacts.
     * @throws SQLException
     */
    public static ObservableList<Contact> getAllContacts() throws SQLException {
        if(allContacts.isEmpty()) {
            String sql = "SELECT * FROM CONTACTS";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String email = rs.getString("Email");

                Contact contactResults = new Contact(contactID, contactName, email);
                allContacts.add(contactResults);
            }
        }
        return allContacts;
    }

    /**
     * This method selects a contacts id from the database based on the contacts name.
     * @param name
     * @return contactID.
     * @throws SQLException
     */
    public static int contactNametoInt(String name) throws SQLException {
        int contactID = 0;
        String sql = "SELECT CONTACT_ID FROM CONTACTS WHERE CONTACT_NAME = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1,name);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            contactID = rs.getInt("Contact_ID");
        }
        return contactID;
    }

    /**
     * This method selects a contacts name from the database based on the contacts id.
     * @param id
     * @return contactName.
     * @throws SQLException
     */
    public static String contactIntToName(int id) throws SQLException {
        String contactName = "";
        String sql = "SELECT CONTACT_NAME FROM CONTACTS WHERE CONTACT_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs= ps.executeQuery();
        while (rs.next()){
            contactName = rs.getString("Contact_Name");
        }
        return contactName;
    }

    /**
     * This method returns the contacts email based on the contacts id.
     * @param id
     * @return email.
     * @throws SQLException
     */
    public static String contactEmail(int id) throws SQLException{
        String email = "";
        String sql = "SELECT EMAIL FROM CONTACTS WHERE CONTACT_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            email = rs.getString("Email");
        }
        return email;
    }
}
