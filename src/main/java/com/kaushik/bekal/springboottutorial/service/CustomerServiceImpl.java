package com.kaushik.bekal.springboottutorial.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kaushik.bekal.springboottutorial.dao.CustomerInfoRepository;
import com.kaushik.bekal.springboottutorial.dao.CustomerRepository;
import com.kaushik.bekal.springboottutorial.domain.Customer;

//Comment 1
//Comment 2
//Comment 3
//Some comment1
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private CustomerInfoRepository customerInfoRepository;
	
	@Override
	public Customer createCustomer(Customer customer) {
		return customerRepository.save(customer);
	}
	
	@Override
	public List<Customer> findByEmail(String email) {
		return customerInfoRepository.findByEmail(email);
	}
	
	@Override
	public Customer findById(long id) {
		return customerRepository.findOne(id);
	}

	@Override
	public Iterable<Customer> findAll() {
		return customerRepository.findAll();
	}

	@Override
	public Customer createOrUpdateCustomer(long id, Customer customerIncoming) {
		if(customerRepository.exists(id)){ //If exists, update customer
			Customer customer = customerRepository.findOne(id);
			if(customerIncoming.getFirstName() != null)
				customer.setFirstName(customerIncoming.getFirstName());
			if(customerIncoming.getLastName() != null)
				customer.setLastName(customerIncoming.getLastName());
			if(customerIncoming.getEmail() != null)
				customer.setEmail(customerIncoming.getEmail());
			if(customerIncoming.getUsername() != null)
				customer.setUsername(customerIncoming.getUsername());
			if(customerIncoming.getPassword() != null)
				customer.setPassword(customerIncoming.getPassword());
			
				return customerRepository.save(customer);
		}else{ //Create a new customer
			return customerRepository.save(customerIncoming);
		}
	}

}
