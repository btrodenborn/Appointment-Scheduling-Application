package com.example.c195_finalproject.controller;

import com.example.c195_finalproject.DAO.AppointmentsDAO;
import com.example.c195_finalproject.DAO.CustomerWithCountryDAO;
import com.example.c195_finalproject.DAO.CustomersDAO;
import com.example.c195_finalproject.Main;
import com.example.c195_finalproject.model.Appointment;
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
import java.util.Optional;
import java.util.ResourceBundle;

/**Public class that controls the main screen for the program.*/

public class MainScreen implements Initializable {
    /**This variable holds which appointment is going to be modified.*/
    private static Appointment modifyAppointment;

    /**This variable holds which customer is going to be modified.*/
    private static Customer modifyCustomer;

    /**This variable holds which user is logged into the program.*/
    public static int loggedInUser;

    /**This variable creates the stage for the main screen form.*/
    Stage stage;

    /**This is the toggle group for the radio buttons on the main form.*/
    @FXML
    private ToggleGroup ApptSch;

    /**This is the radio button to select all appointments.*/
    @FXML
    private RadioButton allApptBtn;

    /**This is the button to add an appointment.*/
    @FXML
    private Button appointmentAddBtn;

    /**This is the label to alert the user of an upcoming appointment.*/
    @FXML
    private Label appointmentAlertLbl;

    /**This is the contact column for the appointments table.*/
    @FXML
    private TableColumn<?, ?> appointmentContactCol;

    /**This is the customerID column for the appointments table.*/
    @FXML
    private TableColumn<?, ?> appointmentCustomerIDCol;

    /**This is the button used to delete an appointment.*/
    @FXML
    private Button appointmentDeleteBtn;

    /**This is the description column for the appointments table.*/
    @FXML
    private TableColumn<?, ?> appointmentDescriptionCol;

    /**This is the appointment end time column for the appointments table.*/
    @FXML
    private TableColumn<?, ?> appointmentEndCol;

    /**This is the appointmentID column for the appointments table.*/
    @FXML
    private TableColumn<Appointment, Integer> appointmentIDCol;

    /**This is the location column for the appointments table.*/
    @FXML
    private TableColumn<?, ?> appointmentLocationCol;

    /**This is the button used to modify an appointment.*/
    @FXML
    private Button appointmentModifyBtn;

    /**This is the radio button used to filter appointments by month.*/
    @FXML
    private RadioButton appointmentMonthRBtn;

    /**This is the appointment start time column for the appointments table.*/
    @FXML
    private TableColumn<?, ?> appointmentStartCol;

    /**This is the appointment table view.*/
    @FXML
    private TableView<Appointment> appointmentTableView;

    /**This is the title column for the appointments table.*/
    @FXML
    private TableColumn<?, ?> appointmentTitleCol;

    /**This is the typecontact column for the appointments table.*/
    @FXML
    private TableColumn<?, ?> appointmentTypeCol;

    /**This is the userID column for the appointments table.*/
    @FXML
    private TableColumn<?, ?> appointmentUserIDCol;

    /**This is the radio button used to filter appointments by week.*/
    @FXML
    private RadioButton appointmentWeekRBtn;

    /**This is the button used to add customers.*/
    @FXML
    private Button customerAddBtn;

    /**This is the address column for the customers table.*/
    @FXML
    private TableColumn<?, ?> customerAddressCol;

    /**This is the country column for the customers table.*/
    @FXML
    private TableColumn<?, ?> customerCountryCol;

    /**This is the button used to delete a customer.*/
    @FXML
    private Button customerDeleteBtn;

    /**This is the first level division column for the customers table.*/
    @FXML
    private TableColumn<?, ?> customerFLDCol;

    /**This is the customerID column for the customers table.*/
    @FXML
    private TableColumn<?, ?> customerIdCol;

    /**This is the button used to modify a customer.*/
    @FXML
    private Button customerModifyBtn;

    /**This is the customer name column for the customers table.*/
    @FXML
    private TableColumn<?, ?> customerNameCol;

    /**This is the customer phone number column for the customers table.*/
    @FXML
    private TableColumn<?, ?> customerPhoneNumbCol;

    /**This is the customer postal code column for the customers table.*/
    @FXML
    private TableColumn<?, ?> customerPostalCodeCol;

    /**This is the customers table view.*/
    @FXML
    private TableView<Customer> customerTableView;

    /**This is the button used to exit the program.*/
    @FXML
    private Button exitBtn;

    /**
     * This method is used to return the appointment that is to be modified in the modify appointment screen.
     * @return modifyAppointment variable.
     */
    public static Appointment getModifyAppointment() {return modifyAppointment;}

    /**
     * This method is used to return the customer that is to be modified in the modify customer screen,
     * @return modifyCustomer variable.
     */
    public static Customer getModifyCustomer() {return modifyCustomer;}

    /**
     * This method switches the user from the main screen to the add appointment screen.
     * @param event add appointment button.
     * @throws IOException
     */
    @FXML
    void onActionAddAppointment(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("AddAppointment.fxml"));
        Scene scene = new Scene(fxmlLoader.load() );
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.setTitle("c195 project");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method switches the user from the main screen to the add customer screen.
     * @param event add customer button.
     * @throws IOException
     */
    @FXML
    void onActionAddCustomer(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("AddCustomer.fxml"));
        Scene scene = new Scene(fxmlLoader.load() );
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.setTitle("c195 project");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This sets the appointment table view to all recorded appointments.
     * @param event all appointments button.
     * @throws SQLException
     */
    @FXML
    void onActionAllAppt(ActionEvent event) throws SQLException {
        appointmentTableView.setItems(AppointmentsDAO.getAllAppointments());
    }

    /**
     * This sets the appointment table view to all recorded appointments in the current month.
     * @param event appointments by month button.
     * @throws SQLException
     */
    @FXML
    void onActionAppointmentMonth(ActionEvent event) throws SQLException {
        appointmentTableView.setItems(AppointmentsDAO.getAppointmentByMonth());
    }

    /**
     * This sets the appointment table to all recorded appointments in the current week.
     * @param event appointments by week button.
     * @throws SQLException
     */
    @FXML
    void onActionAppointmentWeek(ActionEvent event) throws SQLException {
        appointmentTableView.setItems(AppointmentsDAO.getAppointmentByWeek());

    }

    /**
     * This deletes the selected appointment.
     * @param event delete appointment button.
     * @throws SQLException
     */
    @FXML
    void onActionDeleteAppointment(ActionEvent event) throws SQLException {
        Appointment selectedAppointment = appointmentTableView.getSelectionModel().getSelectedItem();
        AppointmentsDAO.delete(selectedAppointment.getAppointmentID());
        appointmentTableView.setItems(AppointmentsDAO.getAllAppointments());

        Alert alert = new Alert(Alert.AlertType.WARNING, "Appointment " + selectedAppointment.getAppointmentID() + " was a " +
                selectedAppointment.getType() + " meeting and has been deleted.");
        Optional<ButtonType> result = alert.showAndWait();

    }

    /**
     * This deletes the selected customer, given the selected customer does not have any active appointments.
     * @param event delete customer button.
     * @throws SQLException
     */
    @FXML
    void onActionDeleteCustomer(ActionEvent event) throws SQLException {
        Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        if(AppointmentsDAO.activeCustomerAppointmentsCheck(selectedCustomer.getCustomerID())) {
            CustomersDAO.delete(selectedCustomer.getCustomerID());
            customerTableView.setItems(CustomerWithCountryDAO.getAllCustomerWithCountry());

            Alert alert = new Alert(Alert.AlertType.WARNING, "Customer " + selectedCustomer.getCustomerID() + " has been deleted");
            Optional<ButtonType> result = alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Customer " + selectedCustomer.getCustomerID() + " has active appointments and can not be deleted");
            Optional<ButtonType> result = alert.showAndWait();
        }

    }

    /**
     * This exits the program.
     * @param event exit button.
     */
    @FXML
    void onActionExit(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit the program");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }

    }

    /**
     * This method switches the user from the main screen to the modify appointment screen.
     * @param event modify appointment button.
     * @throws IOException
     */
    @FXML
    void onActionModifyAppointment(ActionEvent event) throws IOException {
        modifyAppointment = appointmentTableView.getSelectionModel().getSelectedItem();

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ModifyAppointment.fxml"));
        Scene scene = new Scene(fxmlLoader.load() );
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.setTitle("c195 project");
        stage.setScene(scene);
        stage.show();

    }

    /**
     * This method switches the user from the main screen to the modify customer screen.
     * @param event modify customer button.
     * @throws IOException
     */
    @FXML
    void onActionModifyCustomer(ActionEvent event) throws IOException {
        modifyCustomer = customerTableView.getSelectionModel().getSelectedItem();

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ModifyCustomer.fxml"));
        Scene scene = new Scene(fxmlLoader.load() );
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.setTitle("c195 project");
        stage.setScene(scene);
        stage.show();

    }

    /**
     * This method switches the user from the main screen to the reports screen.
     * @param event reports button.
     * @throws IOException
     */
    @FXML
    void onActionReports(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Report.fxml"));
        Scene scene = new Scene(fxmlLoader.load() );
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.setTitle("c195 project");
        stage.setScene(scene);
        stage.show();

    }

    /**This is the initialize method for the main screen. This sets the table view for the appointments and customers tables.
     * This sets the upcoming appointment alert.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            customerTableView.setItems(CustomerWithCountryDAO.getAllCustomerWithCountry());
            appointmentTableView.setItems(AppointmentsDAO.getAllAppointments());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        appointmentIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        appointmentTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        appointmentLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        appointmentContactCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        appointmentTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        appointmentStartCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        appointmentEndCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        appointmentCustomerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        appointmentUserIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));

        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPostalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerPhoneNumbCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        customerFLDCol.setCellValueFactory(new PropertyValueFactory<>("division"));
        customerCountryCol.setCellValueFactory(new PropertyValueFactory<>("country"));

        try {
            appointmentAlertLbl.setText(AppointmentsDAO.appointmentAlert(loggedInUser));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
