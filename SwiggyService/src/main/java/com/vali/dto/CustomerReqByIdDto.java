package com.vali.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Customer's detail by id.")
public class CustomerReqByIdDto {

    @NotBlank(message = "Name is required")
    @Schema(description = "Customer name", example = "Risabh Jaiswal")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Schema(description = "Customer email", example = "risabh@example.com")
    private String email;

    @NotNull(message = "Created date is required")
    @Schema(description = "Date when customer was created")
    private LocalDateTime createdDate;

    @NotNull(message = "Updated date is required")
    @Schema(description = "Date when customer was last updated")
    private LocalDateTime updatedDate;
}
