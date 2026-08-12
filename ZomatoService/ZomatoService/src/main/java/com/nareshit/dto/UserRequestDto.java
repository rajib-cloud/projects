package com.nareshit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String password;
	
	private String contactId;
	
	
	
	
	
}
