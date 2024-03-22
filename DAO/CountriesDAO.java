package com.example.c195_finalproject.DAO;

import com.example.c195_finalproject.helper.JDBC;
import com.example.c195_finalproject.model.Country;
import com.example.c195_finalproject.model.FirstLevelDiv;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**This public class accesses the countries table in the database.*/
public class CountriesDAO {

    /**This is a static observable list of all countries from the database.*/
    private static ObservableList<Country> allCountries= FXCollections.observableArrayList();

    /**
     * This method accesses the countries table in the database and stores each country in an observable list.
     * @return allCountries.
     * @throws SQLException
     */
    public static ObservableList<Country> getAllCountries() throws SQLException {
        if(allCountries.isEmpty()) {
            String sql = "SELECT * FROM COUNTRIES";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int countryID = rs.getInt("Country_ID");
                String country = rs.getString("Country");

                Country countryResults = new Country(countryID, country);
                allCountries.add(countryResults);
            }
        }
        return allCountries;
    }

}

