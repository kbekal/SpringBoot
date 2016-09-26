package com.kaushik.bekal.springboottutorial.service;

import java.util.List;

import com.kaushik.bekal.springboottutorial.domain.Customer;

public interface CustomerService {
	
	public Customer createCustomer(Customer customer);
	public List<Customer> findByEmail(String email);
	public Customer findById(long id);
	public Iterable<Customer> findAll();
	public Customer createOrUpdateCustomer(long id,
			Customer customer);
}
