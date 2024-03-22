package com.example.c195_finalproject.model;

import com.example.c195_finalproject.DAO.UsersDAO;
import javafx.collections.ObservableList;

import java.sql.SQLException;

/**This is the user public class.*/
public class User {
    private int userID;
    private String userName;
    private String password;

    public User(int userID, String userName, String password) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
    }

    /**
     * This method returns a userID.
     * @return userID.
     */
    public int getUserID() {
        return userID;
    }

    /**
     * This method sets a userID.
     * @param userID
     */
    public  void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * This method returns a username.
     * @return userName.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This method sets a username.
     * @param userName
     */
    public  void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * This method returns a password.
     * @return password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method sets a password.
     * @param password
     */
    public  void setPassword(String password) {
        this.password = password;
    }

    /**
     * This method returns a string.
     * @return string.
     */
    @Override
    public String toString(){
        return (Integer.toString(getUserID()));
    }







}
