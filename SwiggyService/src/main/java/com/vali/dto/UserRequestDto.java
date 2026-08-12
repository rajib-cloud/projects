package com.vali.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "user request dto for registration")
public class UserRequestDto {

	@NotBlank(message = "Name can not be blank.")
	@Size(min = 2, max = 10, message = "Name must be between 2 to 10 characters.")
	@Schema(description = "User's firstName", example = "Rajib")
	private String firstName;
	
	@NotBlank(message = "Name can not be blank.")
	@Size(min = 2, max = 10, message = "Name must be between 2 to 10 characters.")
	@Schema(description = "User's lastName", example = "Maharana")
	private String lastName;
	
	@Email(message = "Invalid email format")
	@NotBlank(message = "Email is required")
	@Schema(description = "User's email address", example = "r@gmail.com")
	private String email;
	
	@NotBlank(message = "Password is required.")
	@Size(min = 4, message = "password must be contain atleast 4 characters.")
	@Schema(description = "User's password", example = "abc@123")
	private String password;
	
	
	@NotBlank(message = "Contact ID is required")
	//@Pattern(regexp = "CID\\d{3}", message = "Contact ID must start with 'CID' followed by 5 digits")
	@Schema(description = "Contact ID in format CID12345", example = "CID12345")
	private String contactId;


	/*
	 
	 {
	     "firstName": "javi",
	     "lastName": "anji",
	     "email": "anji@gmail.com",
	     "password": "anji",
	     "contactId": "9398259492"
	     	 
	     	 }
	 */
}
