package com.parkit.parkingsystem.constants;

public class DBConstants {

    /**
     * This variable gets the next parking spot from the SQL database.
     */
    public static final String GET_NEXT_PARKING_SPOT = "select min(PARKING_NUMBER) "
            + "from parking where AVAILABLE = true and TYPE = ?";
    /**
     * This variable updates the parking spot from the SQL database.
     */
    public static final String UPDATE_PARKING_SPOT
            = "update parking set available = ? where" + " PARKING_NUMBER = ?";
    /**
     * This variable saves the ticket from the SQL database.
     */
    public static final String SAVE_TICKET = "insert into ticket(PARKING_NUMBER, VEHICLE_REG_NUMBER, PRICE, IN_TIME, OUT_TIME) values(?,?,?,?,?)";
    /**
     * This variable updates the ticket from the SQL database.
     */
    public static final String UPDATE_TICKET = "update ticket set PRICE=?, OUT_TIME=? where ID=?";
    /**
     * This variable gets the ticket from the SQL database.
     */
    public static final String GET_TICKET = "select t.PARKING_NUMBER, t.ID, t.PRICE, t.IN_TIME, t.OUT_TIME, p.TYPE"
            + " from ticket t,parking p"
            + " where p.parking_number = t.parking_number and t.VEHICLE_REG_NUMBER=?"
            + " order by t.IN_TIME DESC limit 1";
    /**
     *  This variable checks if the vehicle exists in the SQL database.
     */
    public static final String GET_AN_EXISTING_VEHICLE = "SELECT * FROM prod.ticket where VEHICLE_REG_NUMBER=? AND OUT_TIME IS NOT NULL";
}
