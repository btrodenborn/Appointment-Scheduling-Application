package com.example.c195_finalproject.DAO;

import com.example.c195_finalproject.controller.MainScreen;
import com.example.c195_finalproject.helper.JDBC;

import com.example.c195_finalproject.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**This public class accesses the users table in the database.*/
public class UsersDAO {

    /**This is a static observable list of all users from the database.*/
    private static ObservableList<User> allUsers = FXCollections.observableArrayList();

    /**
     * This method selects all users from the users table in the database and returns an observable list of all users.
     * @return allUsers
     * @throws SQLException
     */
    public static ObservableList<User> getAllUsers() throws SQLException {
        if(allUsers.isEmpty()) {
            String sql = "SELECT * FROM USERS";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int userID = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String password = rs.getString("Password");
                User userResults = new User(userID, userName, password);
                allUsers.add(userResults);
            }
        }
        return allUsers;
    }

    /**
     * This method selects all users from the database and compares the userName and password. If the userName and password match one
     * of the userName and password sets from the database, the method returns true. Otherwise, the method returns false.
     * @param userName
     * @param password
     * @return
     * @throws SQLException
     */
    public static boolean findUser(String userName, String password) throws SQLException {
        String sql = "SELECT * FROM USERS WHERE USER_NAME = ? AND PASSWORD = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1,userName);
        ps.setString(2,password);
        ResultSet rs = ps.executeQuery();
        if(rs.next()) {
            MainScreen.loggedInUser = rs.getInt("User_ID");
            return true;
        }

        return false;
    }

    /**
     * This method returns a username from the database where the userID matches.
     * @param userID
     * @return userName.
     * @throws SQLException
     */
    public static String findUserName(int userID) throws SQLException {
        String userName = "";
        String sql = "SELECT * FROM USERS WHERE USER_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, userID);
        ResultSet rs = ps.executeQuery();
        if(rs.next()) {
            userName = rs.getString("User_Name");
        }
        return userName;
    }

    /**
     * This method returns a password from the where the userID matches.
     * @param userID
     * @return password.
     * @throws SQLException
     */
    public static String findPassWord(int userID) throws SQLException{
        String password = "";
        String sql = "SELECT * FROM USERS WHERE USER_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, userID);
        ResultSet rs = ps.executeQuery();
        if(rs.next()) {
            password = rs.getString("Password");
        }
        return password;
    }

}

