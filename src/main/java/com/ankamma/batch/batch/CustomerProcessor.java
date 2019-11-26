package com.ankamma.batch.batch;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.ankamma.batch.model.Customer;
import com.ankamma.batch.model.CustomerUI;

@Component
public class CustomerProcessor implements ItemProcessor<Customer, CustomerUI> {

	@Override
	public CustomerUI process(Customer customer) throws Exception {
		CustomerUI customerUI = new CustomerUI(customer.getId(),customer.getFirstName(), customer.getLastName(), customer.getStatus());

		return customerUI;
	}
}
