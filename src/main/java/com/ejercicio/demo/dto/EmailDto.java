package com.ejercicio.demo.dto;

import com.ejercicio.demo.annotations.EmailValidAnnotation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Builder
@Getter
public class EmailDto {
    @NotNull(message = "Email is necessary")
//    @EmailValidAnnotation(message = "Email deformado")
//    @Email(message = "Email deformado",regexp = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    @Email(message="Email deformado")
    private String email;
}
