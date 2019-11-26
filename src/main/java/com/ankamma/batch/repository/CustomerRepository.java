package com.ankamma.batch.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ankamma.batch.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long>{
	List<Customer> findByFirstName(String FirstName);
	List<Customer> findAll();
}