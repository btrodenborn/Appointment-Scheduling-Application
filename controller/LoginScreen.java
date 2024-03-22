package com.example.c195_finalproject.controller;

import com.example.c195_finalproject.DAO.UsersDAO;
import com.example.c195_finalproject.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

/**Public class that controls the login screen.*/

public class LoginScreen implements Initializable {
    /**This variable creates the stage for the Login Screen.*/
    Stage stage;

    /**The label for user location.*/
    @FXML
    private Label LocationLbl;

    /**The button to log in to the form.*/
    @FXML
    private Button LoginBtn;

    /**The label for password text field.*/
    @FXML
    private Label PasswordLbl;

    /**The text field for password entry.*/
    @FXML
    private TextField PasswordTxtBox;

    /**The label for username text field.*/
    @FXML
    private Label UsernameLbl;

    /**The text field for username entry.*/
    @FXML
    private TextField UsrNmTxtBox;

    /**The label to welcome users to the form.*/
    @FXML
    private Label WelcomeLbl;

    /**
     * This checks for valid usernames and passwords. If valid, the user is switched to the main screen.
     * This also records unsuccessful and successful log in attempts into a text file.
     * @param  event The login form.
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    void onActionLogin(ActionEvent event) throws SQLException, IOException {

        LocalDateTime timeStamp = LocalDateTime.now();

        String filename = "src/login_activity.txt";

        FileWriter fwriter = new FileWriter(filename, true);

        PrintWriter outputFile = new PrintWriter(fwriter);

        outputFile.println(UsrNmTxtBox.getText() + ", " + PasswordTxtBox.getText() + ", " + timeStamp);

        if(UsersDAO.findUser(UsrNmTxtBox.getText(), PasswordTxtBox.getText())){
            outputFile.println("Login Successful\n");

            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainScreen.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setTitle("c195 project");
            stage.setScene(scene);
            stage.show();
        }
        else{
            outputFile.println("Login Failed\n");

            ResourceBundle rb = ResourceBundle.getBundle("Language", Locale.getDefault());
            if(Locale.getDefault().getLanguage().equals("fr")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(rb.getString("Title"));
                alert.setContentText(rb.getString("Content"));
                alert.showAndWait();
            }
            if(Locale.getDefault().getLanguage().equals("en")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(rb.getString("Title"));
                alert.setContentText(rb.getString("Content"));
                alert.showAndWait();
            }

        }
        outputFile.close();



    }

    /**This method runs at the beginning of the program running. This checks the users location and changes the language accordingly.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ResourceBundle rb = ResourceBundle.getBundle("Language", Locale.getDefault());

        if(Locale.getDefault().getLanguage().equals("fr")) {
            UsernameLbl.setText(rb.getString("Username"));
            LoginBtn.setText(rb.getString("Log-in"));
            PasswordLbl.setText(rb.getString("Password"));
            WelcomeLbl.setText(rb.getString("Welcome"));
        }
        if(Locale.getDefault().getLanguage().equals("en")) {
            UsernameLbl.setText(rb.getString("Username"));
            LoginBtn.setText(rb.getString("Log-in"));
            PasswordLbl.setText(rb.getString("Password"));
            WelcomeLbl.setText(rb.getString("Welcome"));
        }

        ZoneId zoneid = ZoneId.systemDefault();

        LocationLbl.setText(zoneid.toString());


    }
}