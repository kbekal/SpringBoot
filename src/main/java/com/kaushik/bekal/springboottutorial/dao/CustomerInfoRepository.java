package com.kaushik.bekal.springboottutorial.dao;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.kaushik.bekal.springboottutorial.domain.Customer;

public interface CustomerInfoRepository extends Repository<Customer,Long>{
	List<Customer> findByEmail(String email);
}
