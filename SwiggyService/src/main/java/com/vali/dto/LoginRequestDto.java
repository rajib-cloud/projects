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
@Schema(description = "User's request dto for login.")
public class LoginRequestDto {

	@Email(message = "Invalid email format")
	@NotBlank(message = "Email is required")
	@Schema(description = "User's email address", example = "r@gmail.com")
	private String email;
	
	@NotBlank(message = "Password is required.")
	@Size(min = 4, message = "password must be contain atleast 4 characters.")
	@Schema(description = "User's password", example = "abc@123")
	private String password;
}
