package com.example.c195_finalproject.controller;

import com.example.c195_finalproject.DAO.CountriesDAO;
import com.example.c195_finalproject.DAO.CustomersDAO;
import com.example.c195_finalproject.DAO.FirstLevelDivDAO;
import com.example.c195_finalproject.Main;
import com.example.c195_finalproject.model.Country;
import com.example.c195_finalproject.model.Customer;
import com.example.c195_finalproject.model.FirstLevelDiv;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**Public class that creates the add customer form.*/

public class AddCustomer implements Initializable {
    /**This variable creates the stage for the add customer form.*/
    Stage stage;

    /**The address text field.*/
    @FXML
    private TextField AddressTxt;

    /**The combo box for the different countries.*/
    @FXML
    private ComboBox<Country> CountryCombo;

    /**The text field for customer name.*/
    @FXML
    private TextField CustomerNameTxt;

    /**The combo box for each first level division.*/
    @FXML
    private ComboBox<FirstLevelDiv> FLDCombo;

    /**The text field for customer phone numbers.*/
    @FXML
    private TextField PhoneNumTxt;

    /**The text field for customer postal code.*/
    @FXML
    private TextField PostalCodeTxt;

    /**
     * This exits the add customer screen back to the main screen.
     * @param event The exit button.
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
     * This save the new customer to the customers list.
     * @param event The save button.
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    void OnActionSave(ActionEvent event) throws SQLException, IOException {
        try {
            int customerID = CustomersDAO.getNewCustomerID();
            String customerName = CustomerNameTxt.getText();
            String address = AddressTxt.getText();
            String postalCode = PostalCodeTxt.getText();
            String phoneNumber = PhoneNumTxt.getText();
            int divisionID = FLDCombo.getSelectionModel().getSelectedItem().getDivisionID();

            CustomersDAO.insert(customerID, customerName, address, postalCode, phoneNumber, divisionID);

            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainScreen.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setTitle("c195 project");
            stage.setScene(scene);
            stage.show();

        }catch (NumberFormatException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setContentText("Please enter a valid value for each field");
        alert.showAndWait();
    }


    }

    /**
     * This takes the selection from the country combo box and sets the items in the FLD combo box based on the selected country.
     * @param event The country combo box.
     * @throws SQLException
     */
    @FXML
    void onActionCountryCombo(ActionEvent event) throws SQLException {
        int countryID;
        countryID = CountryCombo.getSelectionModel().getSelectedItem().getCountryID();
        FLDCombo.setItems(FirstLevelDivDAO.getFirstLevelDivByCountry(countryID));

    }


    /**This method runs at the beginning of the add customer form. Begins with the country combo box items set.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            CountryCombo.setItems(CountriesDAO.getAllCountries());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
