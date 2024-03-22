package com.example.c195_finalproject.controller;

import com.example.c195_finalproject.DAO.AppointmentsDAO;
import com.example.c195_finalproject.DAO.ContactsDAO;
import com.example.c195_finalproject.DAO.CustomersDAO;
import com.example.c195_finalproject.DAO.UsersDAO;
import com.example.c195_finalproject.Main;
import com.example.c195_finalproject.model.Appointment;
import com.example.c195_finalproject.model.Contact;
import com.example.c195_finalproject.model.Customer;
import com.example.c195_finalproject.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;


/**Public class that controls the modify appointment form.*/
public class ModifyAppointment implements Initializable {

    /**This variable creates the stage for the modify appointment form.*/
    Stage stage;

    /**This is the combo box for selecting a contact.*/
    @FXML
    private ComboBox<Contact> ContactCombo;

    /**This is the combo box for selecting a customer.*/
    @FXML
    private ComboBox<Customer> CustomerIDCombo;

    /**This is the date picker for selecting an appointment date.*/
    @FXML
    private DatePicker DateBox;

    /**This is the description text field.*/
    @FXML
    private TextField DescriptionTxt;

    /**This is the combo box for selecting the end appointment time.*/
    @FXML
    private ComboBox<LocalTime> EndTimeCombo;

    /**This is the location text field.*/
    @FXML
    private TextField LocationTxt;

    /**This is the combo box for selecting the appointment start time.*/
    @FXML
    private ComboBox<LocalTime> StartTimeCombo;

    /**This is the title text field.*/
    @FXML
    private TextField TitleTxt;

    /**This is the type text field.*/
    @FXML
    private TextField TypeTxt;

    /**This is the combo box for selecting userID.*/
    @FXML
    private ComboBox<User> UserIDCombo;

    /**This is the appointmentID text field.*/
    @FXML
    private TextField appointmentIDTxt;

    /**
     * This exits the modify appointment form and moves the user back to the main screen form.
     * @param event the exit button.
     * @throws IOException
     */
    @FXML
    void OnActionExit(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load() );
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.setTitle("c195 project");
        stage.setScene(scene);
        stage.show();

    }

    /**
     * This saves modified elements of the appointment and returns the user to the main screen form.
     * @param event the save button.
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    void OnActionSave(ActionEvent event) throws SQLException, IOException {
        Appointment modifyAppointment = MainScreen.getModifyAppointment();

        try {

            int appointmentID = modifyAppointment.getAppointmentID();
            String title = TitleTxt.getText();
            String description = DescriptionTxt.getText();
            String location = LocationTxt.getText();
            String type = TypeTxt.getText();
            int contactID = ContactsDAO.contactNametoInt(ContactCombo.getValue().getContactName());
            int userID = UserIDCombo.getSelectionModel().getSelectedItem().getUserID();
            int customerID = CustomerIDCombo.getSelectionModel().getSelectedItem().getCustomerID();
            LocalDateTime start = LocalDateTime.of(DateBox.getValue(), StartTimeCombo.getSelectionModel().getSelectedItem());
            LocalDateTime end = LocalDateTime.of(DateBox.getValue(), EndTimeCombo.getSelectionModel().getSelectedItem());

            if (end.isBefore(start) || end.isEqual(start)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Time");
                alert.setContentText("Please select an end meeting time that is after the start time");
                alert.showAndWait();
            }
            else {
                if (AppointmentsDAO.modifyAppointmentOverlap(customerID, appointmentID, start, end)) {
                    AppointmentsDAO.delete(appointmentID);
                    AppointmentsDAO.insert(appointmentID, title, description, location, type, customerID, userID, contactID, start, end);

                    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainScreen.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    stage.setTitle("c195 project");
                    stage.setScene(scene);
                    stage.show();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Appointment Time Conflict");
                    alert.setContentText("This customer already has a meeting scheduled at this time, please select a new time");
                    alert.showAndWait();
                }
            }
        }catch (NumberFormatException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setContentText("Please enter a valid value for each field");
        alert.showAndWait();
        }


    }


    /**This is the initialize method for modify appointment form. This sets each text field and combo box with each element from the appointment being modified.
     * This also sets the options for each combo box. This also sets the proper times to be selected.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Appointment modifyAppointment = MainScreen.getModifyAppointment();

        appointmentIDTxt.setText(String.valueOf(modifyAppointment.getAppointmentID()));
        TypeTxt.setText(String.valueOf(modifyAppointment.getType()));
        TitleTxt.setText(String.valueOf(modifyAppointment.getTitle()));
        LocationTxt.setText(String.valueOf(modifyAppointment.getLocation()));
        DescriptionTxt.setText(String.valueOf(modifyAppointment.getDescription()));
        UserIDCombo.setPromptText(String.valueOf(modifyAppointment.getUserID()));
        CustomerIDCombo.setPromptText(String.valueOf(modifyAppointment.getCustomerID()));
        StartTimeCombo.setPromptText(String.valueOf(modifyAppointment.getStartTime().toLocalTime()));
        EndTimeCombo.setPromptText(String.valueOf(modifyAppointment.getEndTime().toLocalTime()));
        DateBox.setValue((modifyAppointment.getStartTime().toLocalDate()));


        try {
            ContactCombo.setPromptText(String.valueOf(ContactsDAO.contactIntToName(modifyAppointment.getContactID())));

            UserIDCombo.setItems(UsersDAO.getAllUsers());
            ContactCombo.setItems(ContactsDAO.getAllContacts());
            CustomerIDCombo.setItems(CustomersDAO.getAllCustomers());

            ContactCombo.setValue(new Contact(modifyAppointment.getContactID(), ContactsDAO.contactIntToName(modifyAppointment.getContactID()), ContactsDAO.contactEmail(modifyAppointment.getContactID())));
            UserIDCombo.setValue(new User(modifyAppointment.getUserID(), UsersDAO.findUserName(modifyAppointment.getUserID()), UsersDAO.findPassWord(modifyAppointment.getUserID())));
            CustomerIDCombo.setValue(new Customer(modifyAppointment.getCustomerID(), CustomersDAO.idToName(modifyAppointment.getCustomerID()), CustomersDAO.idToAddress(modifyAppointment.getCustomerID()), CustomersDAO.idToPostal(modifyAppointment.getCustomerID()), CustomersDAO.idToPhone(modifyAppointment.getCustomerID()), CustomersDAO.idToDivID(modifyAppointment.getCustomerID())));
            StartTimeCombo.setValue(modifyAppointment.getStartTime().toLocalTime());
            EndTimeCombo.setValue(modifyAppointment.getEndTime().toLocalTime());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        LocalTime start = LocalTime.of(8,0);
        LocalTime end = LocalTime.of(21, 45);
        while(start.isBefore(end.plusSeconds(1))){
            StartTimeCombo.getItems().add(start);
            start = start.plusMinutes(15);
            EndTimeCombo.getItems().add(start);
        }

    }
}
