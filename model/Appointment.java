package com.example.c195_finalproject.model;

import com.example.c195_finalproject.DAO.AppointmentsDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.time.*;
import java.util.stream.Collectors;

/**This is the public class for Appointment.*/
public class Appointment {
    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private int customerID;
    private int userID;
    private int contactID;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Appointment(int appointmentID, String title, String description, String location, String type, int customerID, int userID, int contactID,
                       LocalDateTime startTime, LocalDateTime endTime) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * This method returns the appointmentID.
     * @return appointmentID.
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     * This method sets the appointmentID.
     * @param appointmentID
     */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    /**
     * This method returns the appointment title.
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method sets the appointment title.
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * This method returns the appointment description.
     * @return description.
     */
    public String getDescription() {
        return description;
    }

    /**
     *This method sets the appointment description.
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This method returns the appointment location.
     * @return location.
     */
    public String getLocation() {
        return location;
    }

    /**
     * This method sets the appointment location.
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
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
     * This method returns the userID.
     * @return userID.
     */
    public int getUserID() {
        return userID;
    }

    /**
     * This method sets the userID.
     * @param userID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * This method returns the contactID.
     * @return contactID.
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * This method sets the contactID.
     * @param contactID
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * This method returns the appointment start time.
     * @return startTime.
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * This method sets the appointment start time.
     * @param startTime
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * This method returns the appointment end time.
     * @return endTime.
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /**
     * This method sets the appointment end time.
     * @param endTime
     */
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    /**
     * This method returns the appointment type.
     * @return type.
     */
    public String getType() {
        return type;
    }

    /**
     * This method sets the appointment type.
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * This method returns an observable list of all 12 months listed numerically.
     * @return allMonths.
     */
    public static ObservableList<String> getAllMonths() {
        ObservableList<String> allMonths = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12");

        return allMonths;
    }

}




