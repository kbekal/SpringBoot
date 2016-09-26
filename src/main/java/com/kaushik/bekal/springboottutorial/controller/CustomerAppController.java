package com.kaushik.bekal.springboottutorial.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kaushik.bekal.springboottutorial.domain.Customer;
import com.kaushik.bekal.springboottutorial.service.CustomerService;

@RestController
@RequestMapping(value="/customers")
public class CustomerAppController {
	
	private CustomerService customerService;
	
	@Autowired
	public CustomerAppController(CustomerService customerService){
		this.customerService = customerService;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public @ResponseBody Customer createCustomer(@RequestBody Customer customer){
		return customerService.createCustomer(customer);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public @ResponseBody Customer createOrUpdateCustomer(@PathVariable long id,
			@RequestBody Customer customer){
		return customerService.createOrUpdateCustomer(id, customer);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public @ResponseBody Customer createCustomer(@PathVariable long id){
		return customerService.findById(id);
	}
	
	@RequestMapping(value="/email/{email:.*}", method=RequestMethod.GET)
	public @ResponseBody List<Customer> getCustomerByEmail(@PathVariable String email){
		return customerService.findByEmail(email);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public @ResponseBody Iterable<Customer> getAllCustomers(){
		return customerService.findAll();
	}
	
}
