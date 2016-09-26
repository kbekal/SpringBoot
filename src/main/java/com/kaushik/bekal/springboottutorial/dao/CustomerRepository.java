package com.kaushik.bekal.springboottutorial.dao;

import org.springframework.data.repository.CrudRepository;
import com.kaushik.bekal.springboottutorial.domain.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
	
}
