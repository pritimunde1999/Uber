# Uber
Uber is a ride-hailing service that allows customers to book rides through a mobile application. In order to facilitate this service, Uber needs a system to manage the different entities involved in the service such as the customers, drivers, cabs, and trip bookings.

The system should have the following features:

An Admin entity that can register new admins, update the password of existing admins, and delete existing admins.
A Customer entity that can register new customers, delete existing customers, and book trips. When a customer books a trip, the system should assign the nearest available driver and cab to the customer. The customer should also be able to cancel or complete the trip.
A Driver entity that can register new drivers, delete existing drivers and update the status of their cab (available or unavailable).
A Cab entity that has information about the per-km rate and the availability of the cab.
A TripBooking entity that stores information about the trip such as the from and to locations, the distance, the status of the trip, and the bill.

Used SpringBoot framework to built the Uber app backend.
