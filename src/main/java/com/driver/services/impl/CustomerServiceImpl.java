package com.driver.services.impl;

import com.driver.model.*;
import com.driver.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;
import com.driver.repository.TripBookingRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository2;

	@Autowired
	DriverRepository driverRepository2;

	@Autowired
	TripBookingRepository tripBookingRepository2;

	@Override
	public void register(Customer customer) {
		//Save the customer in database
		customerRepository2.save(customer);
	}

	@Override
	public void deleteCustomer(Integer customerId) {
		// Delete customer without using deleteById function

		//customerRepository2.deleteById(customerId);
	}

	@Override
	public TripBooking bookTrip(int customerId, String fromLocation, String toLocation, int distanceInKm) throws Exception {
		//Book the driver with lowest driverId who is free (cab available variable is Boolean.TRUE). If no driver is available, throw "No cab available!" exception
		//Avoid using SQL query
		List<Driver> driverList = driverRepository2.findAll();
		Driver driver = null;
		int min = Integer.MAX_VALUE;
		try {
			for (Driver driver1 : driverList) {
				if (driver1.getDriverId() < min && driver1.getCab().isAvailable()) {
					min = driver1.getDriverId();
					driver = driver1;
				}
			}
		} catch (Exception e) {
			throw new Exception("No cab available!");
		}
		Customer customer = customerRepository2.findById(customerId).get();
		TripBooking tripBooking = new TripBooking();
		tripBooking.setDriver(driver);
		tripBooking.setCustomer(customer);
		tripBooking.setFromLocation(fromLocation);
		tripBooking.setToLocation(toLocation);
		tripBooking.setDistanceInKms(distanceInKm);
		tripBooking.setTripStatus(TripStatus.CONFIRMED);
        List<TripBooking> tripBookingList = customer.getTripBookingList();
		tripBookingList.add(tripBooking);

		driver.getCab().setAvailable(false);

		int perKmRate = driver.getCab().getPerKmRate();
		int bill = distanceInKm*perKmRate;
		tripBooking.setBill(bill);

		customer.setTripBookingList(tripBookingList);
		driver.setTripBookingList(tripBookingList);

		customerRepository2.save(customer);
		driverRepository2.save(driver);
		//tripBookingRepository2.save(tripBooking);

		return tripBooking;
	}

	@Override
	public void cancelTrip(Integer tripId){
		//Cancel the trip having given trip Id and update TripBooking attributes accordingly
        TripBooking tripBooking = tripBookingRepository2.findById(tripId).get();
		Customer customer = tripBooking.getCustomer();
		Driver driver = tripBooking.getDriver();
		tripBooking.setTripStatus(TripStatus.CANCELED);


		Cab cab =driver.getCab();
		cab.setAvailable(true);

		List<TripBooking> tripBookingList = customer.getTripBookingList();
		tripBookingList.add(tripBooking);
		customer.setTripBookingList(tripBookingList);
		driver.setTripBookingList(tripBookingList);

		tripBookingRepository2.save(tripBooking);
	}

	@Override
	public void completeTrip(Integer tripId){
		//Complete the trip having given trip Id and update TripBooking attributes accordingly
		TripBooking tripBooking = tripBookingRepository2.findById(tripId).get();
		Customer customer = tripBooking.getCustomer();
		Driver driver = tripBooking.getDriver();
		tripBooking.setTripStatus(TripStatus.COMPLETED);

		Cab cab =driver.getCab();
		cab.setAvailable(true);

		List<TripBooking> tripBookingList = customer.getTripBookingList();
		tripBookingList.add(tripBooking);
		customer.setTripBookingList(tripBookingList);
		driver.setTripBookingList(tripBookingList);

		tripBookingRepository2.save(tripBooking);
	}
}
