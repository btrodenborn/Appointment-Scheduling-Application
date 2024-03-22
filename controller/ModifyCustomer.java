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

/**Public class that controls the modify customer form.*/
public class ModifyCustomer implements Initializable {

    /**This variable creates the stage for the modify customer form.*/
    Stage stage;

    /**This is the address text field.*/
    @FXML
    private TextField AddressTxt;

    /**This is the combo box of countries.*/
    @FXML
    private ComboBox<Country> CountryCombo;

    /**This is the customer name text field.*/
    @FXML
    private TextField CustomerNameTxt;

    /**This is the combo box of first level divisions.*/
    @FXML
    private ComboBox<FirstLevelDiv> FLDCombo;

    /**This is the phone number text field.*/
    @FXML
    private TextField PhoneNumbTxt;

    /**This is the postal code text field.*/
    @FXML
    private TextField PostalCodeTxt;

    /**This is the customerID text field.*/
    @FXML
    private TextField customerIdTxt;

    /**
     * This exits the modify customer form and returns the user to the main screen form.
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
     * This saves each modified part to the customer and returns the user to the main screen form.
     * @param event the save button.
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    void OnActionSave(ActionEvent event) throws SQLException, IOException {
        Customer modifyCustomer = MainScreen.getModifyCustomer();

        try {
            int customerID = modifyCustomer.getCustomerID();
            String customerName = CustomerNameTxt.getText();
            String phoneNumber = PhoneNumbTxt.getText();
            String postalCode = PostalCodeTxt.getText();
            String address = AddressTxt.getText();
            int divisionID = FLDCombo.getValue().getDivisionID();

            CustomersDAO.delete(customerID);
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
     * When a country is selected, this updates the first level division combo box to the appropriated division based on the selected country.
     * @param event country combo box.
     * @throws SQLException
     */
    @FXML
    void onActionCountry(ActionEvent event) throws SQLException {
        int countryID;
        countryID = CountryCombo.getSelectionModel().getSelectedItem().getCountryID();
        FLDCombo.setItems(FirstLevelDivDAO.getFirstLevelDivByCountry(countryID));

    }


    /**This is the initialize method for the modify customer screen. This sets each element from the selected customer to be modified.
     * This also sets the values of the combo boxes.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Customer modifyCustomer = MainScreen.getModifyCustomer();

        customerIdTxt.setText(String.valueOf(modifyCustomer.getCustomerID()));
        CustomerNameTxt.setText(String.valueOf(modifyCustomer.getCustomerName()));
        PhoneNumbTxt.setText(String.valueOf(modifyCustomer.getPhone()));
        PostalCodeTxt.setText(String.valueOf(modifyCustomer.getPostalCode()));
        AddressTxt.setText(String.valueOf(modifyCustomer.getAddress()));
        try {
            CountryCombo.setPromptText(String.valueOf(FirstLevelDivDAO.getCountry(modifyCustomer.getDivisionID())));
            CountryCombo.setItems(CountriesDAO.getAllCountries());

            FLDCombo.setItems((FirstLevelDivDAO.getFirstLevelDivByCountry(FirstLevelDivDAO.getCountryID(modifyCustomer.getDivisionID()))));
            FLDCombo.setValue(new FirstLevelDiv(modifyCustomer.getDivisionID(), FirstLevelDivDAO.getDivision(modifyCustomer.getDivisionID()),
                    FirstLevelDivDAO.getCountryID(modifyCustomer.getDivisionID())));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}



