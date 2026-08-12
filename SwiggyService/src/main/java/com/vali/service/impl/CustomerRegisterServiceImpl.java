package com.vali.service.impl;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vali.dto.CustomerDto;
import com.vali.dto.CustomerPatchDto;
import com.vali.dto.CustomerReqByIdDto;
import com.vali.dto.LoginRequestDto;
import com.vali.entity.Customer;
import com.vali.repository.CustomerRepository;
import com.vali.service.ICustomerService;

@Service
public class CustomerRegisterServiceImpl implements ICustomerService{

	@Autowired 
	private CustomerRepository customerRepository;

    
	@Override
	public Customer createOrUpdateCustomerRegister(CustomerDto customerDto) {
		Customer customer = null;
		try {
			customer = new Customer();
			customer.setName(customerDto.getName());
			customer.setEmail(customerDto.getEmail());
			customer.setPassword(Base64.getEncoder().encodeToString(customerDto.getPassword().getBytes()));
			 customerRepository.save(customer);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return customer;
	}

	@Override
	public Customer customerLogin(LoginRequestDto loginRequestDto) {
		try {
			Optional<Customer> byEmail = customerRepository.findByEmail(loginRequestDto.getEmail());
			Customer customer = byEmail.get();
			String pwd = new String(Base64.getDecoder().decode(customer.getPassword()));
			if(pwd.equals(loginRequestDto.getPassword()))
				return customer;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public CustomerReqByIdDto getCustomerById(Long id) {
		 Customer customer = customerRepository.findById(id).orElseThrow(()->
		 new RuntimeException("Customer not found with ID :: "+id));
		return new CustomerReqByIdDto(customer.getName(),customer.getEmail(),customer.getCreatedDate(),customer.getUpdatedDate());
	}
	
	@Override
	public List<Customer> getAllCustomer() {
		
		return customerRepository.findAll();
	}

	@Override
	public Customer updateCustomer(CustomerDto customerDto) {
		Customer existingCustomer = customerRepository.findByEmail(customerDto.getEmail()).orElseThrow(()->
		new RuntimeException("Customer not found with email :: "+customerDto.getEmail()));
		existingCustomer.setName(customerDto.getName());
		existingCustomer.setEmail(customerDto.getEmail());
		if(customerDto.getPassword() != null && !customerDto.getPassword().isBlank()) {
			existingCustomer.setPassword(Base64.getEncoder().encodeToString(customerDto.getPassword().getBytes()));
		}
		return customerRepository.save(existingCustomer);
	}

	@Override
	public Customer partialUpdateCustomer(Long id, CustomerPatchDto customerPatchDto) {
		Customer existingCustomer = customerRepository.findById(id).orElseThrow(()->
		new RuntimeException("Customer not found with ID::"+id));
		if(customerPatchDto.getName() != null) {
			existingCustomer.setName(customerPatchDto.getName());
		}
		if(customerPatchDto.getEmail() != null) {
			existingCustomer.setEmail(customerPatchDto.getEmail());
		}
		if(customerPatchDto.getPassword() != null) {
			existingCustomer.setPassword(Base64.getEncoder().encodeToString(customerPatchDto.getPassword().getBytes()));
		}
		return customerRepository.save(existingCustomer);
	}

	

}
