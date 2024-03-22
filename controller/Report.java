package com.example.c195_finalproject.controller;

import com.example.c195_finalproject.DAO.AppointmentsDAO;
import com.example.c195_finalproject.DAO.ContactsDAO;
import com.example.c195_finalproject.DAO.CustomersDAO;
import com.example.c195_finalproject.Main;
import com.example.c195_finalproject.model.Appointment;
import com.example.c195_finalproject.model.Contact;
import com.example.c195_finalproject.model.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


/**Public class the controls the reports screen.*/
public class Report implements Initializable {

    /**This variable creates the stage for the reports screen.*/
    Stage stage;

    /**This is the appointmentID table column.*/
    @FXML
    private TableColumn<?, ?> appointmentIDCol;

    /**This is the appointment table view.*/
    @FXML
    private TableView<Appointment> appointmentsTbl;

    /**This is the back button.*/
    @FXML
    private Button backBtn;

    /**This is the combo box of contacts.*/
    @FXML
    private ComboBox<Contact> contactCombo;

    /**This is the text field for the number of Canadian customers.*/
    @FXML
    private TextField customerCanadaTxt;

    /**This is the customerID table column.*/
    @FXML
    private TableColumn<?, ?> customerIDCol;

    /**This is the text field for the number of UK customers.*/
    @FXML
    private TextField customerUKTxt;

    /**This is the text field for the number of American customers.*/
    @FXML
    private TextField customerUSATxt;

    /**This is the description table column.*/
    @FXML
    private TableColumn<?, ?> descriptionCol;

    /**This is the end appointment time table column.*/
    @FXML
    private TableColumn<?, ?> endCol;

    /**This is the find button.*/
    @FXML
    private Button findBtn;

    /**This is the combo box of months.*/
    @FXML
    private ComboBox<String> monthCombo;

    /**This is the number of customer appointments text field.*/
    @FXML
    private TextField numberCustomerApptTxt;

    /**This is the start appointment time table column.*/
    @FXML
    private TableColumn<?, ?> startCol;

    /**This is the title table column.*/
    @FXML
    private TableColumn<?, ?> titleCol;

    /**This is the type tabel column.*/
    @FXML
    private TableColumn<?, ?> typeCol;

    /**This is the combo box of types.*/
    @FXML
    private ComboBox<String> typeCombo;

    /**
     * This sets the table view based on the contact.
     * @param event contact combo box.
     * @throws SQLException
     */
    @FXML
    void onActionContactCombo(ActionEvent event) throws SQLException {
       appointmentsTbl.setItems(AppointmentsDAO.getAppointmentByContact(contactCombo.getSelectionModel().getSelectedItem().getContactID()));

    }

    /**
     * This finds the number of appointments based on appointment type and the month.
     * @param event find appointment button.
     * @throws SQLException
     */
    @FXML
    void onActionFind(ActionEvent event) throws SQLException {
        String type = typeCombo.getSelectionModel().getSelectedItem();
        String month = monthCombo.getSelectionModel().getSelectedItem();

        int count = AppointmentsDAO.numberOfCustomerApptByMonthAndType(type, month);
        numberCustomerApptTxt.setText(String.valueOf(count));
    }

    /**
     * This takes the user back to the main screen form.
     * @param event the back button.
     * @throws IOException
     */
    @FXML
    void onActionBack(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load() );
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.setTitle("c195 project");
        stage.setScene(scene);
        stage.show();

    }

    /**This is the initialize method for the reports form. This sets up the table view and the options for each combo box.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            contactCombo.setItems(ContactsDAO.getAllContacts());
            appointmentsTbl.setItems(AppointmentsDAO.getAllAppointments());
            typeCombo.setItems(AppointmentsDAO.getAllTypes());
            customerUSATxt.setText(String.valueOf(CustomersDAO.getCustomerInUS()));
            customerCanadaTxt.setText(String.valueOf(CustomersDAO.getCustomerInCanada()));
            customerUKTxt.setText(String.valueOf(CustomersDAO.getCustomerInUK()));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        appointmentIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));

        monthCombo.setItems(Appointment.getAllMonths());

    }
}
