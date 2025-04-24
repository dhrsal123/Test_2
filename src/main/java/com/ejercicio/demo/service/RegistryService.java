package com.ejercicio.demo.service;

import com.ejercicio.demo.dto.RegistryDto;

public interface RegistryService {
    Object register(RegistryDto registryDto);

    String validate(String email, String password, String name);
}
