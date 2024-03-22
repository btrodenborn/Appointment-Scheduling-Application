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

/**Public class that allows the user to add appointments to the form.*/

public class AddAppointment implements Initializable {

    /**This variable creates the stage for the Add Appointment form.*/
    Stage stage;

    /**The combo box for the contactID numbers.*/
    @FXML
    private ComboBox<Contact> ContactIDCombo;

    /**The combo box for the customerID numbers.*/
    @FXML
    private ComboBox<Customer> CustomerIDCombo;

    /**The date picker to add a date to the appointment.*/
    @FXML
    private DatePicker Date;

    /**The description text field.*/
    @FXML
    private TextField DescriptionTxt;

    /**The combo box for appointment end time.*/
    @FXML
    private ComboBox<LocalTime> EndTimeCombo;

    /**The text field for appointment location.*/
    @FXML
    private TextField LocationTxt;

    /**The combo box for appointment start time.*/
    @FXML
    private ComboBox<LocalTime> StartTimeCombo;

    /**The text field for the title of the appointment.*/
    @FXML
    private TextField TitleTxt;

    /**The text field for the type of appointment.*/
    @FXML
    private TextField TypeTxt;

    /**The combo box for selecting userID.*/
    @FXML
    private ComboBox<User> UserIDCombo;

    /**
     * Exits the add appointment form back to the main screen.
     * @param event The exit button.
     * @throws IOException
     */
    @FXML
    void onActionExit(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load() );
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.setTitle("c195 project");
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Saves the new appointment to the list of appointments, and returns user to the main screen form.
     * @param event The save button.
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    void onActionSave(ActionEvent event) throws SQLException, IOException {
        try {
            int appointmentID = AppointmentsDAO.getNewAppointmentID();
            String title = TitleTxt.getText();
            String description = DescriptionTxt.getText();
            String location = LocationTxt.getText();
            String type = TypeTxt.getText();
            int contactID = ContactsDAO.contactNametoInt(ContactIDCombo.getSelectionModel().getSelectedItem().getContactName());
            int userID = UserIDCombo.getSelectionModel().getSelectedItem().getUserID();
            int customerID = CustomerIDCombo.getSelectionModel().getSelectedItem().getCustomerID();
            LocalDateTime start = LocalDateTime.of(Date.getValue(), StartTimeCombo.getSelectionModel().getSelectedItem());
            LocalDateTime end = LocalDateTime.of(Date.getValue(), EndTimeCombo.getSelectionModel().getSelectedItem());

            if (end.isBefore(start) || end.isEqual(start)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Time");
                alert.setContentText("Please select an end meeting time that is after the start time");
                alert.showAndWait();
            } else {
                if (AppointmentsDAO.newAppointmentOverlap(customerID, start, end)) {
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

    /**This method runs at the start of the add appointment form.
     * Begins with the combo box options filled as well as the appropriate meeting times.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
           UserIDCombo.setItems(UsersDAO.getAllUsers());
           ContactIDCombo.setItems(ContactsDAO.getAllContacts());
           CustomerIDCombo.setItems(CustomersDAO.getAllCustomers());
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
