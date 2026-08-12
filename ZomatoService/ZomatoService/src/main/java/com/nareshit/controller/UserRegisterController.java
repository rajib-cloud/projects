package com.nareshit.controller;

import java.net.HttpURLConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nareshit.dto.ResponseMessage;
import com.nareshit.dto.UserRequestDto;
import com.nareshit.enity.UserRegister;
import com.nareshit.service.UserRegisterService;
import com.nareshit.utility.Contstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "User Registration and Login Operations", tags = {"User Operations"})
@RestController
public class UserRegisterController {
	
	@Autowired
	private UserRegisterService userRegisterService;

	  @ApiOperation(value = "Zomato User Registration", notes = "Register a new user", response = ResponseMessage.class)
	    @ApiResponses(value = {
	    @ApiResponse(code = 201, message = "Zomato user Register succesfully", response = ResponseMessage.class),
	    @ApiResponse(code = 400, message = "Zomato user Register failed", response = ResponseMessage.class),
	    @ApiResponse(code = 500, message = "Internal Server Error", response = ResponseMessage.class)
	    })
	@PostMapping("/zomatouser")
	public ResponseEntity<ResponseMessage> createRegister(@ApiParam(value = "User Registration Data", required = true)
	                                                     @RequestBody UserRequestDto userRequestDto) {
	try {
		
	if(userRequestDto ==null || userRequestDto.getEmail()==null || userRequestDto.getEmail().isEmpty() ||
			
			userRequestDto.getPassword()==null || userRequestDto.getPassword().isEmpty())	{
		
		return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Contstants.FAILURE, "email and password cannot be empty..!"));
	}
			
		UserRegister userRegister = userRegisterService.createdZomatoRegister(userRequestDto);
		
		if(userRegister!=null) {
		
		return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_CREATED, Contstants.SUCCESS, "Zomato user Register succesfully", userRegister));
	}else {
	
		return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Contstants.FAILURE, "Zomato user Register failed",userRegister));
		
	}}catch (Exception e) {
		return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_GATEWAY, Contstants.FAILED, "Internal Server Error"));
	}
	
	
	}
	
	
}
