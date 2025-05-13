package com.ejercicio.demo.service.impl;

import com.ejercicio.demo.dto.RegistryDto;
import com.ejercicio.demo.exceptions.BadRequestException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RegistryValidatorHelper {
    private static final String EMAIL_REGEX_PATTERN = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String REGISTRY_NAME_REGEX = "[^a-zA-Z]";
    @Value("${app.regexRegistry}")
    private String passwordRegex;

    public void validate(RegistryDto registryDto) {
        if (!Pattern.matches(EMAIL_REGEX_PATTERN, registryDto.getEmail())) {
            throw new BadRequestException("Email deformado");
        } else if (!Pattern.matches(passwordRegex, registryDto.getPassword())) {
            throw new BadRequestException("La contraseña debe tener: 1 Mayuscula, 1 Minuscula, 1 Numero y ser como minimo de 6 caracteres");
        } else if (registryDto.getName().length() < 3 || Pattern.matches(REGISTRY_NAME_REGEX, registryDto.getName())) {
            throw new BadRequestException("Ingresa un nombre valido");
        }
    }
}
