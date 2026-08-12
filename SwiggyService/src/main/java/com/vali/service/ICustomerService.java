package com.vali.service;

import java.util.List;

import com.vali.dto.CustomerDto;
import com.vali.dto.CustomerPatchDto;
import com.vali.dto.CustomerReqByIdDto;
import com.vali.dto.LoginRequestDto;
import com.vali.entity.Customer;

public interface ICustomerService {

	public Customer createOrUpdateCustomerRegister(CustomerDto customerDto);
	
	public Customer customerLogin(LoginRequestDto loginRequestDto);
	
	public CustomerReqByIdDto getCustomerById(Long id);
	
	public List<Customer> getAllCustomer();
	
	public Customer updateCustomer(CustomerDto customerDto);
	
	public Customer partialUpdateCustomer(Long id, CustomerPatchDto customerPatchDto);
}
