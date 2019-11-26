package com.ankamma.batch.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ankamma.batch.model.Customer;
import com.ankamma.batch.model.CustomerUI;
import com.ankamma.batch.repository.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {
	@Autowired
	CustomerRepository repository;

	@GetMapping("/bulkcreate")
	public String bulkcreate() throws ParseException {
		// save a single Customer
		repository.save(new Customer("Rajesh", "Bhojwani", "active",new SimpleDateFormat("yyyy-MM-dd").parse("2019-11-26")));

		// save a list of Customers
		repository.saveAll(
				Arrays.asList(new Customer("Salim", "Khan", "active",new SimpleDateFormat("yyyy-MM-dd").parse("2019-11-26")), new Customer("Rajesh", "Parihar", "Inactive",new SimpleDateFormat("yyyy-MM-dd").parse("2019-11-26")),
						new Customer("Rahul", "Dravid", "active",new SimpleDateFormat("yyyy-MM-dd").parse("2019-11-26")), new Customer("Dharmendra", "Bhojwani", "active",new SimpleDateFormat("yyyy-MM-dd").parse("2019-11-25"))));

		return "Customers are created";
	}

	@PostMapping("/create")
	public String create(@RequestBody CustomerUI customer) throws ParseException {
		// save a single Customer
		repository.save(new Customer(customer.getFirstName(), customer.getLastName(), customer.getStatus(),new SimpleDateFormat("yyyy-MM-dd").parse("2019-11-25")));

		return "Customer is created";
	}

	@GetMapping("/findall")
	public List<CustomerUI> findAll() {

		List<Customer> customers = repository.findAll();
		List<CustomerUI> customerUI = new ArrayList<>();

		for (Customer customer : customers) {
			customerUI.add(new CustomerUI(customer.getId(),customer.getFirstName(), customer.getLastName(),customer.getStatus()));
		}

		return customerUI;
	}

	@RequestMapping("/search/{id}")
	public String search(@PathVariable long id) {
		String customer = "";
		customer = repository.findById(id).toString();
		return customer;
	}

	@RequestMapping("/searchbyfirstname/{firstname}")
	public List<CustomerUI> fetchDataByLastName(@PathVariable String firstname) {

		List<Customer> customers = repository.findByFirstName(firstname);
		List<CustomerUI> customerUI = new ArrayList<>();

		for (Customer customer : customers) {
			customerUI.add(new CustomerUI(customer.getId(),customer.getFirstName(), customer.getLastName(),customer.getStatus()));
		}

		return customerUI;
	}
}