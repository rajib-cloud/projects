package com.vali.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vali.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

	public Optional<Customer> findByEmail(String email);
}
