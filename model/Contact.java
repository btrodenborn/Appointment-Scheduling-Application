package com.example.c195_finalproject.model;

/**This is the public class for contact.*/
public class Contact {
    private int contactID;
    private String contactName;
    private String email;

    public Contact(int contactID, String contactName, String email) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.email = email;
    }

    /**
     * This method returns the contactID.
     * @return contactID.
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * This method sets the contactID.
     * @param contactID
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * This method returns the contact name.
     * @return contactName.
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * This method sets the contact name.
     * @param contactName
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * This method returns the contacts email.
     * @return email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method sets the contacts email.
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * This method returns a string.
     * @return string.
     */
    @Override
    public String toString(){
        return (getContactName());
    }

}
