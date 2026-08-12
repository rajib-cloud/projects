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
@Schema(description = "user request dto to show firstName and email")
public class ReqByIdDto {

	@NotBlank(message = "Name can not be blank.")
	@Size(min = 2, max = 10, message = "Name must be between 2 to 10 characters.")
	@Schema(description = "User's firstName", example = "Rajib")
	private String firstName;
	
	@Email(message = "Invalid email format")
	@NotBlank(message = "Email is required")
	@Schema(description = "User's email address", example = "r@gmail.com")
	private String email;
}
