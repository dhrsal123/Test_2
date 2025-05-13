package com.ejercicio.demo.service;

import com.ejercicio.demo.dto.EmailDto;
import com.ejercicio.demo.dto.RegistryDto;

public interface RegistryService {
    Object register(RegistryDto registryDto);
    Object getByEmail(EmailDto email);
    Object getAll(int page, int size);
    Object updateUser(RegistryDto registryDto);
    Object deleteUserById(Long id);
}
