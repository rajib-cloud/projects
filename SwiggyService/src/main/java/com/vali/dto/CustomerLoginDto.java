package com.vali.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerLoginDto {

	@Email(message = "Invalid email format")
	@NotBlank(message = "Email is required.")
	@Schema(description = "Customer's email", example = "risabh@gmail.com")
	private String email;

	@NotBlank(message = "Password is required.")
	@Size(min = 4, message = "password must be contain atleast 4 characters.")
	@Schema(description = "User's password", example = "abc@123")
	private String password;
	
}
