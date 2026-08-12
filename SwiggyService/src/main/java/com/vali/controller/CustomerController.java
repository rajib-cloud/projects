package com.vali.controller;

import java.net.HttpURLConnection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vali.dto.CustomerDto;
import com.vali.dto.CustomerPatchDto;
import com.vali.dto.CustomerReqByIdDto;
import com.vali.dto.ResponseMessage;
import com.vali.entity.Customer;
import com.vali.service.ICustomerService;
import com.vali.utility.Constants;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
public class CustomerController {

	@Autowired
	private ICustomerService customerService;
	
	@PostMapping("/customer-register")
	public ResponseEntity<ResponseMessage> customerRegister(@Valid @RequestBody CustomerDto customerDto) {
	    Customer register = customerService.createOrUpdateCustomerRegister(customerDto);
	    System.out.println(customerDto);
	    return ResponseEntity.ok(new ResponseMessage(
	            HttpURLConnection.HTTP_OK,
	            Constants.SUCCESS,
	            "Customer registered successfully",
	            register
	    ));
	}

	
	@GetMapping("/customer-ById/{id}")
	public ResponseEntity<ResponseMessage> customerById(@PathVariable Long id){
		CustomerReqByIdDto customerById = customerService.getCustomerById(id);
		return ResponseEntity.ok(new ResponseMessage(
				HttpURLConnection.HTTP_OK,
				Constants.SUCCESS,
				"Customer's detail by ID :: "+id,
				customerById
				));
	}
	
	@GetMapping("/customer-all")
	public ResponseEntity<ResponseMessage> customerAll(){
		List<Customer> allCustomer = customerService.getAllCustomer();
		return ResponseEntity.ok(new ResponseMessage(
				HttpURLConnection.HTTP_OK,
				Constants.SUCCESS,
				"All customer detials fetched",
				allCustomer
				));
	}
	
	@PutMapping("/customer-update")
	public ResponseEntity<ResponseMessage> customerUpdate(@Valid @RequestBody CustomerDto customerDto){
		Customer updateCustomer = customerService.updateCustomer(customerDto);
		return ResponseEntity.ok(new ResponseMessage(
				HttpURLConnection.HTTP_OK,
				Constants.SUCCESS,
				"Customer updated successfully",
				updateCustomer
				));
	}
	
	@PatchMapping("/customer-patch/{id}")
	public ResponseEntity<ResponseMessage> customerPatchUpdate(@PathVariable Long id,@RequestBody CustomerPatchDto customerPatchDto){
		Customer partialUpdateCustomer = customerService.partialUpdateCustomer(id, customerPatchDto);
		return ResponseEntity.ok(new ResponseMessage(
				HttpURLConnection.HTTP_OK,
				Constants.SUCCESS,
				"Customer updated successfully",
				partialUpdateCustomer
				));
	}
	
	
}
