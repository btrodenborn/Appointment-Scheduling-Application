package com.example.c195_finalproject.DAO;

import com.example.c195_finalproject.helper.JDBC;
import com.example.c195_finalproject.model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**This public class accesses the appointments table in the database.*/
public class AppointmentsDAO {

    /**
     * This method selects all the appointments from the database and stores them in an observable list.
     * @return allAppointment
     * @throws SQLException
     */
    public static ObservableList<Appointment> getAllAppointments() throws SQLException {
        ObservableList<Appointment> allAppointment= FXCollections.observableArrayList();
        String sql = "SELECT * FROM APPOINTMENTS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int appointmentID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            int contactID = rs.getInt("Contact_ID");
            LocalDateTime startTime = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endTime = rs.getTimestamp("End").toLocalDateTime();

            Appointment appointmentResults = new Appointment(appointmentID, title, description, location, type, customerID,
                    userID, contactID, startTime, endTime);
            allAppointment.add(appointmentResults);
            }

        return allAppointment;
    }

    /**
     * This method takes an appointment and inserts it into appointments table in the database.
     * @param appointmentID
     * @param title
     * @param description
     * @param location
     * @param type
     * @param customerID
     * @param userID
     * @param contactID
     * @param startTime
     * @param endTime
     * @throws SQLException
     */
    public static void insert(int appointmentID, String title, String description, String location, String type, int customerID,
                              int userID, int contactID, LocalDateTime startTime, LocalDateTime endTime) throws SQLException{

        String sql = "INSERT INTO APPOINTMENTS (Appointment_ID, Title, Description, Location, Type, Customer_ID, User_ID, Contact_ID, " +
                "Start, End) VALUES(?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, appointmentID);
        ps.setString(2, title);
        ps.setString(3, description);
        ps.setString(4, location);
        ps.setString(5, type);
        ps.setInt(6, customerID);
        ps.setInt(7, userID);
        ps.setInt(8, contactID);
        ps.setTimestamp(9, Timestamp.valueOf(startTime));
        ps.setTimestamp(10, Timestamp.valueOf(endTime));
        ps.executeUpdate();
    }

    /**
     * This method deletes a selected appointment, using the appointmentID, from the appointments table in the database.
     * @param appointmentID
     * @throws SQLException
     */
    public static void delete(int appointmentID) throws SQLException{
        String sql = "DELETE FROM APPOINTMENTS WHERE APPOINTMENT_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, appointmentID);
        ps.executeUpdate();
    }

    /**
     * This method changes elements of a selected appointment in the appointments table in the database.
     * @param appointmentID
     * @param title
     * @param description
     * @param location
     * @param type
     * @param customerID
     * @param userID
     * @param contactID
     * @param startTime
     * @param endTime
     * @throws SQLException
     */
    public static void update(int appointmentID, String title, String description, String location, String type, int customerID,
                              int userID, int contactID, LocalDateTime startTime, LocalDateTime endTime) throws SQLException{
        String sql = "UPDATE APPOINTMENTS SET Title = ?, Description = ?,  Location = ?, Type = ?, Customer_ID = ?, User_ID = ?," +
                "Contact_ID = ?, Start = ?, End = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setInt(5, customerID);
        ps.setInt(6, userID);
        ps.setInt(7, contactID);
        ps.setTimestamp(8, Timestamp.valueOf(startTime));
        ps.setTimestamp(9, Timestamp.valueOf(endTime));
        ps.setInt(10, appointmentID);
        ps.executeUpdate();
    }

    /**
     * This method returns all appointment types.
     * @return allTypes.
     * @throws SQLException
     */
    public static ObservableList<String> getAllTypes() throws SQLException {
        ObservableList<String> allTypes = FXCollections.observableArrayList();
        for (Appointment t : getAllAppointments()) {
            allTypes.add(t.getType());
        }
        return allTypes;
    }

    /**
     * This method counts and returns the number of customers by the given type and month.
     * @param type
     * @param month
     * @return count.
     * @throws SQLException
     */
    public static int numberOfCustomerApptByMonthAndType(String type, String month) throws SQLException {
        int count = 0;
        for (Appointment t : getAllAppointments()) {
            int temp = t.getStartTime().getMonthValue();
            if (t.getType().equals(type) && String.valueOf(temp).equals(month)) {
                count++;
            }
        }
        return count;
    }

    /**
     * This method returns a filtered observable list by the given contact.
     * LAMBDA EXPRESSION 1: A lambda expression was used here to increase the efficiency of the number of lines of code wrote.
     * @param contact
     * @return filteredAppointments.
     * @throws SQLException
     */
    public static ObservableList<Appointment> getAppointmentByContact(int contact) throws SQLException {
        ObservableList<Appointment> filteredAppointments = getAllAppointments().stream()
                .filter(apt -> apt.getContactID() == contact)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        return filteredAppointments;
    }

    /**
     * This method is used to send an alert to the user if that specific user has an upcoming appointment.
     * @param userID
     * @return string.
     * @throws SQLException
     */
    public static String appointmentAlert(int userID) throws SQLException {
        for(Appointment t: getAllAppointments()) {
            if(t.getUserID() == userID && timeCheck(t.getStartTime()) && dateCheck(t.getStartTime())) {
                return ("Upcoming Appointment - Appointment ID: " +t.getAppointmentID() +" Appointment Date: "
                        + t.getStartTime().toLocalDate() + " Appointment Time: " + t.getStartTime().toLocalTime());
            }
        }
        return "No upcoming appointments";
    }

    /**
     * This method is used to compare an appointment date to the current date.
     * @param date
     * @return boolean.
     */
    public static Boolean dateCheck(LocalDateTime date) {
        if(date.toLocalDate().isEqual(LocalDate.now()))
            return true;
        return false;
    }

    /**
     * This method is used to compare an appointment start time to the current time.
     * @param startTime
     * @return boolean.
     */
    public static Boolean timeCheck(LocalDateTime startTime) {

        if(startTime.plusMinutes(15).isAfter(LocalDateTime.now()) || startTime.plusMinutes(15).isEqual(LocalDateTime.now())) {
            return true;
        }
        return false;
    }

    /**
     * This method returns an observable list filtered by the current month.
     * @return getAppointmentByMonth.
     * @throws SQLException
     */
    public static ObservableList<Appointment> getAppointmentByMonth() throws SQLException {
        ObservableList<Appointment> getAppointmentByMonth = FXCollections.observableArrayList();
        for(Appointment t: getAllAppointments()) {
            int tempMonth = t.getStartTime().getMonthValue();
            int tempYear = t.getStartTime().getYear();
            if(LocalDateTime.now().getMonthValue() == tempMonth && LocalDateTime.now().getYear() == tempYear) {
                getAppointmentByMonth.add(t);
            }
        }
        return getAppointmentByMonth;
    }

    /**
     * This method returns an observable list filtered by current week starting on monday.
     * @return getAppointmentByWeek.
     * @throws SQLException
     */
    public static ObservableList<Appointment> getAppointmentByWeek() throws SQLException {
        ObservableList<Appointment> getAppointmentByWeek = FXCollections.observableArrayList();
        DayOfWeek dayOfWeek = DayOfWeek.from(LocalDate.now());
        int currentDay = dayOfWeek.getValue();

        int daysSinceMonday = findMonday(currentDay);

        LocalDate currentWeekStart = LocalDate.now().minusDays(daysSinceMonday);

        LocalDate currentWeekEnd = LocalDate.now().plusDays(6-daysSinceMonday);

        for(Appointment t: getAllAppointments()) {
            if((t.getStartTime().toLocalDate().isAfter(currentWeekStart) && t.getStartTime().toLocalDate().isBefore(currentWeekEnd))
                    ||(t.getStartTime().toLocalDate().isEqual(currentWeekStart) || (t.getStartTime().toLocalDate().isEqual(currentWeekEnd)))) {
                getAppointmentByWeek.add(t);
            }
        }
        return getAppointmentByWeek;
    }

    /**
     * This method is recursive, and is used to find the beginning of the current week.
     * @param day
     * @return
     */
    public static int findMonday(int day) {
        if(day == 1)
            return 0;
        else
            return 1 + findMonday(day-1);
    }

    /**
     * This method is used to find if a new appointment has an overlapping start or end time with an existing appointment.
     * LAMBDA EXPRESSION 2: There was a lambda expression used to create a filtered list of appointments by customer.
     * The lambda expression was used to reduce the total number of lines of code wrote when creating the filtered list.
     * @param customerID
     * @param start
     * @param end
     * @return boolean.
     * @throws SQLException
     */
    public static Boolean newAppointmentOverlap(int customerID, LocalDateTime start, LocalDateTime end) throws SQLException {
        ObservableList<Appointment> filteredByCustomer = getAllAppointments().stream()
                .filter(apt -> apt.getCustomerID() == customerID)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        for(Appointment i: filteredByCustomer) {
            if(i.getStartTime().toLocalDate().isEqual(start.toLocalDate())) {
                if(start.isBefore(i.getEndTime()) && (start.isAfter(i.getStartTime()) || start.isEqual(i.getStartTime())))
                    return false;
                if(i.getEndTime().isAfter(start) && (i.getEndTime().isBefore(end) || i.getEndTime().isEqual(end)))
                    return false;
                if((i.getStartTime().isBefore(start) || i.getStartTime().isEqual(start)) && (i.getEndTime().isAfter(end) || i.getEndTime().isEqual(end)))
                    return false;
            }
        }
        return true;
    }

    /**
     * This method is used to check if the appointment being modified start and end times do not overlap with an existing appointment.
     * @param customerID
     * @param appointmentID
     * @param start
     * @param end
     * @return boolean.
     * @throws SQLException
     */
    public static Boolean modifyAppointmentOverlap(int customerID, int appointmentID, LocalDateTime start, LocalDateTime end) throws SQLException {
        ObservableList<Appointment> filteredByCustomer = FXCollections.observableArrayList();
        for(Appointment t: getAllAppointments()) {
            if(t.getCustomerID() == customerID && t.getAppointmentID() != appointmentID) {
                filteredByCustomer.add(t);
            }
        }
        for(Appointment i: filteredByCustomer) {
            if(i.getStartTime().toLocalDate().isEqual(start.toLocalDate())) {
                if(start.isBefore(i.getEndTime()) && (start.isAfter(i.getStartTime()) || start.isEqual(i.getStartTime())))
                    return false;
                if(i.getEndTime().isAfter(start) && (i.getEndTime().isBefore(end) || i.getEndTime().isEqual(end)))
                    return false;
                if((i.getStartTime().isBefore(start) || i.getStartTime().isEqual(start)) && (i.getEndTime().isAfter(end) || i.getEndTime().isEqual(end)))
                    return false;
            }
        }
        return true;
    }

    /**
     * This method is used to check if a specific customer has active appointments before getting deleted.
     * LAMBDA EXPRESSION 3: This lambda expression is used to find a specific customerID in the appointments list.
     * This lambda expression improves the code by reducing the amount of code wrote and reducing the number of loops used.
     * @param customerID
     * @return boolean.
     * @throws SQLException
     */
    public static boolean activeCustomerAppointmentsCheck(int customerID) throws SQLException {
        Appointment id = getAllAppointments().stream()
                .filter(x -> customerID == x.getCustomerID())
                .findAny()
                .orElse(null);
        if(id == null)
            return true;
        return false;
    }

    /**
     * This method creates and returns a unique appointmentID.
     * @return max+1.
     * @throws SQLException
     */
    public static int getNewAppointmentID() throws SQLException {
        int max = 0;
        for (Appointment t : getAllAppointments()) {
            if (t.getAppointmentID() > max) {
                max = t.getAppointmentID();
            }
        }
        return max + 1;
    }


}
