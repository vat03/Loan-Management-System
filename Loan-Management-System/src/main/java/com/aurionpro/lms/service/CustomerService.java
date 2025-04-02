package com.aurionpro.lms.service;

import com.aurionpro.lms.entity.Customer;
import java.util.List;
import java.util.Optional;

public interface CustomerService {
	Customer saveCustomer(Customer customer);

	Optional<Customer> getCustomerById(int id);

	List<Customer> getAllCustomers();

	Customer updateCustomer(Customer customer);

	void deleteCustomer(int id);
}