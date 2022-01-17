package com.parkit.parkingsystem.model;

import com.parkit.parkingsystem.constants.ParkingType;

public class ParkingSpot {
    /**
     * Declaring a number for the parking spot.
     */
    private int number;
    /**
     * Declaring a parking type for the parking spot.
     */
    private ParkingType parkingType;
    /**
     * Declaring an availability.
     */
    private boolean isAvailable;

    /**
     * Constructor of the method.
     * @param number
     * @param parkingType
     * @param isAvailable
     */
    public ParkingSpot(int number, ParkingType parkingType, boolean isAvailable) {
        this.number = number;
        this.parkingType = parkingType;
        this.isAvailable = isAvailable;
    }

    /**
     * This method gets the id of the parking spot.
     * @return the id.
     */
    public int getId() {
        return number;
    }

    /**
     * This method sets an id to the parking spot.
     * @param number
     */
    public void setId(int number) {
        this.number = number;
    }

    /**
     * This method gets the parking type.
     * @return the parking type.
     */
    public ParkingType getParkingType() {
        return parkingType;
    }

    /**
     * This method set the parking type.
     * @param parkingType
     */
    public void setParkingType(ParkingType parkingType) {
        this.parkingType = parkingType;
    }

    /**
     * This method gets the availability of the parking spot.
     * @return the availability.
     */
    public boolean isAvailable() {
        return isAvailable;
    }

    /**
     * This method set the availability of the parking spot.
     * @param available
     */
    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true; }
        if (o == null || getClass() != o.getClass()) {
            return false; }
        ParkingSpot that = (ParkingSpot) o;
        return number == that.number;
    }

    @Override
    public int hashCode() {
        return number;
    }
}
