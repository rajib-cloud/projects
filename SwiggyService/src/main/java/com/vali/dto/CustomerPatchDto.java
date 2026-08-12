package com.vali.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Customer patch request dto for partial updates.")
public class CustomerPatchDto {

    @Schema(description = "Customer's name", example = "Rishab")
    private String name;

    @Schema(description = "Customer's email", example = "rishab@gmail.com")
    private String email;

    @Schema(description = "Customer's password")
    private String password;
}
