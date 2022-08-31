package com.cassandra.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cassandra.entity.Customer;
import com.cassandra.repository.CustomerRepository;
import com.datastax.oss.driver.api.core.uuid.Uuids;

@RestController
public class CustomerController {

	@Autowired
	private CustomerRepository customerRepository;

	private static final Logger log = LoggerFactory.getLogger(CustomerController.class);

	// Customer registration
	@PostMapping("/register-customer")
	public ResponseEntity<String> registerCustomer(@RequestBody Customer customer) {
		try {
			customerRepository.save(
					new Customer(Uuids.timeBased(), customer.getName(), customer.getAddress(), customer.getEmail()));
			log.info("Customer registered successfully");
			return new ResponseEntity<>("You registered successfully", HttpStatus.ACCEPTED);
		} catch (Exception e) {
			log.error("Caught an exception" + e);
			return new ResponseEntity<>("Duplicate entry", HttpStatus.NOT_ACCEPTABLE);
		}
	}

	// Retrieving all customer details
	@GetMapping("/get-all-customers")
	public ResponseEntity<List<Customer>> getAllCustomers() {
		try {
			log.info("Retrieving all customers");
			return new ResponseEntity<>(customerRepository.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			log.error("Caught an exception" + e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Retrieving Customer details based on Id
	@GetMapping("/get-by-id/{id}")
	public ResponseEntity<Optional<Customer>> getById(@PathVariable UUID id) {
		try {
			Optional<Customer> customer = customerRepository.findById(id);
			if (customer.isEmpty()) {
				log.warn("Retreiving Customer object that does not exist");
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			} else {
				log.info("Retrieving customer details by Id");
				return new ResponseEntity<>(customer, HttpStatus.OK);
			}
		} catch (Exception e) {
			log.error("Caught an exception" + e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Retrieving Customer details by email id
	@GetMapping("/get-by-email/{email}")
	public ResponseEntity<Customer> getByEmail(@PathVariable String email) {
		try {
			Customer customer = customerRepository.findByEmail(email);
			if (customer == null) {
				log.warn("Retreiving Customer object that does not exist");
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			} else {
				log.info("Retrieving customer details by Email");
				return new ResponseEntity<>(customer, HttpStatus.OK);
			}
		} catch (Exception e) {
			log.error("Caught an exception" + e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Updating details of Customer based on Id
	@PutMapping("/update-by-id/{id}")
	public ResponseEntity<String> updateCustomer(@RequestBody Customer customer, @PathVariable UUID id) {
		try {
			log.info("Updating customer details");
			customerRepository.save(new Customer(id, customer.getName(), customer.getAddress(), customer.getEmail()));
			return new ResponseEntity<>("Updated Successfully", HttpStatus.ACCEPTED);
		} catch (Exception e) {
			log.error("Caught an exception" + e);
			return new ResponseEntity<>("No such Id Exists", HttpStatus.NOT_ACCEPTABLE);
		}
	}

	// Deleting Customer details based on Id
	@DeleteMapping("/delete-by-id/{id}")
	public ResponseEntity<String> deleteById(@PathVariable UUID id) {
		try {
			customerRepository.deleteById(id);
			log.warn("Deleting customer details by Id");
			return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
		} catch (Exception e) {
			log.error("Caught an exception" + e);
			return new ResponseEntity<>("No such Id exists", HttpStatus.BAD_REQUEST);
		}
	}

}
