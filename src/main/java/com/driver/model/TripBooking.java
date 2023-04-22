package com.driver.model;

import javax.persistence.*;

@Entity
public class TripBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tripBookingId;

    private String fromLocation;

    private String toLocation;

    private int distanceInKms;

    private TripStatus tripStatus;
    private int bill;

    @ManyToOne
    @JoinColumn
    private Driver driver;

    @ManyToOne
    @JoinColumn
    private Customer customer;

    public TripBooking() {
    }

    public TripBooking(int tripBookingId, String fromLocation, String toLocation, int distanceInKms, TripStatus tripStatus, int bill, Driver driver, Customer customer) {
        this.tripBookingId = tripBookingId;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.distanceInKms = distanceInKms;
        this.tripStatus = tripStatus;
        this.bill = bill;
        this.driver = driver;
        this.customer = customer;
    }

    public int getTripBookingId() {
        return tripBookingId;
    }

    public void setTripBookingId(int tripBookingId) {
        this.tripBookingId = tripBookingId;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public int getDistanceInKms() {
        return distanceInKms;
    }

    public void setDistanceInKms(int distanceInKms) {
        this.distanceInKms = distanceInKms;
    }

    public TripStatus getTripStatus() {
        return tripStatus;
    }

    public void setTripStatus(TripStatus tripStatus) {
        this.tripStatus = tripStatus;
    }

    public int getBill() {
        return bill;
    }

    public void setBill(int bill) {
        this.bill = bill;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}