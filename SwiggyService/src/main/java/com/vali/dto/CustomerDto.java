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
@Schema(description = "Customer request dto for registration.")
public class CustomerDto {

    @NotBlank(message = "Name can not be blank")
    @Size(min = 2, max = 10, message = "Name must be between 2 to 10 charcters.")
    @Schema(description = "Customer's name", example = "Rishab")
    private String name;

    @Email(message = "invalid email format")
    @NotBlank(message = "email is required.")
    @Schema(description = "Customer's email", example = "rishab@gmail.com")
    private String email;

    @NotBlank(message = "Password is required")
    @Schema(description = "Customer's password")
    private String password;
    
 
   
}

