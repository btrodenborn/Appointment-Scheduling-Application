package com.example.c195_finalproject.model;

/**This is the public class for country.*/
public class Country {
    private int countryID;
    private String country;

    public Country(int countryID, String country) {
        this.countryID = countryID;
        this.country = country;
    }

    /**
     * This method returns the countryID.
     * @return countryID.
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * This method sets the countryID.
     * @param countryID
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * This method returns the country name.
     * @return country.
     */
    public String getCountry() {
        return country;
    }

    /**
     * This method sets the country name.
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * This method returns a string.
     * @return string.
     */
    @Override
    public String toString() {
        return getCountry();
    }


}
