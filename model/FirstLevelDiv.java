package com.example.c195_finalproject.model;

/**This is the first level division public class.*/
public class FirstLevelDiv {
    private int divisionID;
    private String division;
    private int countryID;

    public FirstLevelDiv(int divisionID, String division, int countryID) {
        this.divisionID = divisionID;
        this.division = division;
        this.countryID = countryID;
    }

    /**
     * This method returns the divisionID.
     * @return divisonID.
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * This method sets the divisionID.
     * @param divisionID
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     * This method returns the name of the division.
     * @return division.
     */
    public String getDivision() {
        return division;
    }

    /**
     * This method sets the name of the division.
     * @param division
     */
    public void setDivision(String division) {
        this.division = division;
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
     * This method returns a string.
     * @return string.
     */
    @Override
    public String toString() {
        return getDivision();
    }
}
