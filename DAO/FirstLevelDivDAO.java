package com.example.c195_finalproject.DAO;

import com.example.c195_finalproject.helper.JDBC;

import com.example.c195_finalproject.model.FirstLevelDiv;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**This public class accesses the first level division table in the database.*/
public class FirstLevelDivDAO {

    /**This is a static observable list of all first level divisions from the database.*/
    private static ObservableList<FirstLevelDiv> allFirstLevelDiv= FXCollections.observableArrayList();

    /**
     * This method accesses the first level division table in the database and stores each first level division in an observable list.
     * @return allFirstLevelDiv.
     * @throws SQLException
     */
    public static ObservableList<FirstLevelDiv> getAllFirstLevelDiv() throws SQLException {
        if(allFirstLevelDiv.isEmpty()) {
            String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int divisionID = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                int countryID = rs.getInt("Country_ID");

                FirstLevelDiv firstLevelDivResults = new FirstLevelDiv(divisionID, division, countryID);
                allFirstLevelDiv.add(firstLevelDivResults);
            }
        }
        return allFirstLevelDiv;
    }

    /**
     * This method selects all from the first level division table that matches the given divisionID.
     * Then takes the countryID and compares it to the three listed countries, and returns the matching country string.
     * @param divisionID
     * @return country.
     * @throws SQLException
     */
    public static String getCountry(int divisionID) throws SQLException {
        String country = "";
        String sql= "SELECT * FROM FIRST_LEVEL_DIVISIONS WHERE DIVISION_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, divisionID);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int countryID = rs.getInt("Country_ID");
            if (countryID == 1)
                country = "U.S";
            if (countryID == 2)
                country = "UK";
            if (countryID == 3)
                country = "Canada";
        }
        return country;
    }

    /**
     * This method selects a division from the first level division table in the database that matches the selected divisionID.
     * @param divisionID
     * @return division.
     * @throws SQLException
     */
    public static String getDivision(int divisionID) throws SQLException {
        String division = "";
        String sql= "SELECT * FROM FIRST_LEVEL_DIVISIONS WHERE DIVISION_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, divisionID);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            division = rs.getString("Division");
        }
        return division;
    }

    /**
     * This method selects all first level divisions from the database that match the selectedCountryID and stores them in an observable list.
     * @param selectedCountryID
     * @return getDivByCountry.
     * @throws SQLException
     */
    public static ObservableList<FirstLevelDiv> getFirstLevelDivByCountry(int selectedCountryID) throws SQLException {
        ObservableList<FirstLevelDiv> getDivByCountry = FXCollections.observableArrayList();
        String sql ="SELECT * FROM FIRST_LEVEL_DIVISIONS WHERE COUNTRY_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, selectedCountryID);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int divisionID = rs.getInt("Division_ID");
            String division = rs.getString("Division");
            int countryID = rs.getInt("Country_ID");

            FirstLevelDiv getDivByCountryResults = new FirstLevelDiv(divisionID, division, countryID);
            getDivByCountry.add(getDivByCountryResults);
        }
        return getDivByCountry;
    }

    /**
     * This method selects a countryID from the database based on the given divisionID.
     * @param divisionID
     * @return countryID.
     * @throws SQLException
     */
    public static int getCountryID(int divisionID) throws SQLException {
        int countryID = 0;
        String sql= "SELECT * FROM FIRST_LEVEL_DIVISIONS WHERE DIVISION_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, divisionID);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            countryID = rs.getInt("Country_ID");
        }
        return countryID;
    }

}

