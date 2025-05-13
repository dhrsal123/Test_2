package com.ejercicio.demo.dto;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistryDto {
    @NotBlank(message="Name must be not empty")
    private String name;
    @NotBlank(message="Email must be not empty")
//    @Email(message = "Email deformado_0",regexp = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private String email;
    @NotBlank(message="Password must be not empty")
    private String password;
    @NotNull(message = "Phones must be not null")
    @NotEmpty
    List<PhoneDto> phones;
}
