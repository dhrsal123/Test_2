package com.ejercicio.demo.service.impl;

import com.ejercicio.demo.annotations.EmailValidAnnotation;
import com.ejercicio.demo.exceptions.BadRequestException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

public class EmailValidator implements ConstraintValidator<EmailValidAnnotation, String> {

    @Value("${email.regex.pattern}")
    private String regex;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!Strings.isBlank(value)) {
            Pattern compile = Pattern.compile(regex);
            return compile.matcher(value).matches();
        }
        return false;
    }
}
