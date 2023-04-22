package com.driver.services.impl;

import com.driver.model.*;
import com.driver.services.CustomerService;
import org.apache.tomcat.util.buf.C2BConverter;
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

		List<Customer> customers = customerRepository2.findAll();
		for(Customer customer : customers) {
			if (customer.getCustomerId() == customerId) {
				customers.remove(customer);
				return;
			}

		}

	}

	@Override
	public TripBooking bookTrip(int customerId, String fromLocation, String toLocation, int distanceInKm) throws Exception {
		//Book the driver with lowest driverId who is free (cab available variable is Boolean.TRUE). If no driver is available, throw "No cab available!" exception
		//Avoid using SQL query
		List<Driver> driverList = driverRepository2.findAll();
		Driver driver = null;
		Cab cab = null;
		int min = Integer.MAX_VALUE;

			for (Driver driver1 : driverList) {
				if (driver1.getDriverId() < min && driver1.getCab().getAvailable()) {
					min = driver1.getDriverId();
					driver = driver1;
					cab = driver1.getCab();
				}
			}

		if(cab==null){
			throw new Exception("No cab available!");
		}
		Customer customer = customerRepository2.findById(customerId).get();
		TripBooking tripBooking = new TripBooking();
		tripBooking.setDriver(driver);
		tripBooking.setCustomer(customer);
		tripBooking.setFromLocation(fromLocation);
		tripBooking.setToLocation(toLocation);
		tripBooking.setDistanceInKm(distanceInKm);
		tripBooking.setStatus(TripStatus.CONFIRMED);

		cab.setAvailable(false);

		int perKmRate = driver.getCab().getPerKmRate();
		int bill = distanceInKm*perKmRate;
		tripBooking.setBill(bill);


		driver.getTripBookingList().add(tripBooking);
		driverRepository2.save(driver);

		customer.getTripBookingList().add(tripBooking);
		customerRepository2.save(customer);
		return tripBooking;
	}

	@Override
	public void cancelTrip(Integer tripId){
		//Cancel the trip having given trip Id and update TripBooking attributes accordingly
        TripBooking tripBooking = tripBookingRepository2.findById(tripId).get();
		tripBooking.setFromLocation(null);
		tripBooking.setToLocation(null);
		tripBooking.setDistanceInKm(0);
		tripBooking.setBill(0);
		tripBooking.setStatus(TripStatus.CANCELED);
		tripBooking.getDriver().getCab().setAvailable(true);
		tripBookingRepository2.save(tripBooking);

	}

	@Override
	public void completeTrip(Integer tripId){
		//Complete the trip having given trip Id and update TripBooking attributes accordingly
		TripBooking tripBooking = tripBookingRepository2.findById(tripId).get();
		tripBooking.setStatus(TripStatus.COMPLETED);
		tripBookingRepository2.save(tripBooking);
	}
}
