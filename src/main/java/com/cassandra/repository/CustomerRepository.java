package com.cassandra.repository;

import java.util.UUID;

import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;

import com.cassandra.entity.Customer;

public interface CustomerRepository extends CassandraRepository<Customer, UUID> {

	@AllowFiltering
	Customer findByEmail(String email);
}
