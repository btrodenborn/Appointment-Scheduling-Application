package com.example.c195_finalproject;

import com.example.c195_finalproject.DAO.AppointmentsDAO;
import com.example.c195_finalproject.helper.JDBC;
import com.example.c195_finalproject.model.Appointment;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * This class creates a form for adding and organizing a companies customers and appointments.
 */
public class Main extends Application {
    @Override
    /**
     * This is the start method.
     */
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("LoginScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This is the main method. This establishes a database connection as well.
     * @param args
     * @throws SQLException
     */

    public static void main(String[] args) throws SQLException {
        JDBC.openConnection();
        launch();
        JDBC.closeConnection();





    }
}