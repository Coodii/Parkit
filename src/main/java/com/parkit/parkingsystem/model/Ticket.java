package com.parkit.parkingsystem.model;

import java.util.Date;

public class Ticket {
    /**
     * Declare an id.
     */
    private int id;
    /**
     * Declare a parking spot.
     */
    private ParkingSpot parkingSpot;
    /**
     * Declare a vehicleRegNumber.
     */
    private String vehicleRegNumber;
    /**
     * Declare a price.
     */
    private double price;
    /**
     * Declare a in time.
     */
    private Date inTime;
    /**
     * Declare an out time.
     */
    private Date outTime;

    /**
     * This method get an id for the ticket.
     * @return an id for the ticket.
     */
    public int getId() {
        return id;
    }

    /**
     * This method set an id for the ticket.
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * This method get a parking spot for the ticket.
     * @return a parking spot for the ticket.
     */
    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    /**
     * This method set an parking spot for the ticket.
     * @param parkingSpot
     */
    public void setParkingSpot(ParkingSpot parkingSpot) {
        this.parkingSpot = parkingSpot;
    }

    /**
     * This method get a vehicle regulation number for the ticket.
     * @return a vehicle regulation number for the ticket.
     */
    public String getVehicleRegNumber() {
        return vehicleRegNumber;
    }

    /**
     * This method set a vehicle regulation number for the ticket.
     * @param vehicleRegNumber
     */
    public void setVehicleRegNumber(String vehicleRegNumber) {
        this.vehicleRegNumber = vehicleRegNumber;
    }

    /**
     * This method get a price for the ticket.
     * @return a price for the ticket.
     */
    public double getPrice() {
        return price;
    }

    /**
     * This method set a price for the ticket.
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * This method get an in time for the ticket.
     * @return an in time for the ticket.
     */
    public Date getInTime() {
        return new Date(inTime.getTime());
    }

    /**
     * This method set an in time for the ticket.
     * @param inTime
     */
    public void setInTime(final Date inTime) {
        this.inTime = new Date(inTime.getTime());
    }

    /**
     * This method get an out time for the ticket.
     * @return an out time for the ticket.
     */
    public Date getOutTime() {
        return new Date(outTime.getTime());
    }

    /**
     * This method set an out time for the ticket.
     * @param outTime
     */
    public void setOutTime(final Date outTime) {
        this.outTime = new Date();
    }
}
