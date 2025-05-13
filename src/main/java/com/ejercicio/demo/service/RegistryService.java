package com.ejercicio.demo.service;

import com.ejercicio.demo.dto.RegistryDto;

public interface RegistryService {
    Object register(RegistryDto registryDto);
    Object getByEmail(String email);
    Object getAll();
    Object updateUser(RegistryDto registryDto);
    Object deleteUserById(Long id);
}
