package com.ejercicio.demo.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistryDto {
    private String name;
    private String email;
    private String password;
    List<PhoneDto> phones;

}
