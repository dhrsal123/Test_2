package com.ejercicio.demo.service.impl;

import com.ejercicio.demo.exceptions.BadRequestException;
import com.ejercicio.demo.exceptions.NotFoundException;
import com.ejercicio.demo.mapper.RegistryMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ejercicio.demo.dto.MessageDto;
import com.ejercicio.demo.dto.RegistryDto;
import com.ejercicio.demo.dto.RegistryReturnDto;
import com.ejercicio.demo.entity.RegistryEntity;
import com.ejercicio.demo.repository.PhoneRepository;
import com.ejercicio.demo.repository.RegistryRepository;
import com.ejercicio.demo.service.RegistryService;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegistryServiceImpl implements RegistryService {
    private final RegistryRepository registryRepository;
    private final PasswordEncoder passwordEncoder;
    private final PhoneRepository phoneRepository;
    private final RegistryValidatorHelper registryValidatorHelper;
    private final RegistryMapper registryMapper;
    //Arreglar exepciones, tverminar crud, registar, actualizar, buscar por email, buscar todos paginado y eliminar
    /*Hecho/por hacer
    [+] eliminar (hecho?)
    [] crear
    [] buscar email
    [] buscar todos
    [] actualizar
     */
    @Override
    public Object register(@NotNull @Valid RegistryDto registryDto) {
        registryValidatorHelper.validate(registryDto);
        if (registryRepository.existsByEmail(registryDto.getEmail())) {
            return new MessageDto("El correo ya esta registrado");
        }
        registryDto.setPassword(passwordEncoder.encode(registryDto.getPassword()));
        RegistryEntity registryEntity = registryMapper.toRegistryEntity(registryDto);
        try {
            RegistryEntity register = registryRepository.save(registryEntity);
            return new RegistryReturnDto(register.getId(), register.getCreated(),
                    register.getModified(), register.getLastLogin(), register.getToken(), register.getIsActive());
        } catch (Exception e) {
            log.error("Error al registrar el registro", e);
            return new MessageDto("Error al crear el usuario");
        }
    }

    @Override
    public Object getByEmail(@NotBlank @Validated @Email String email) {
        //completly validate the email
        log.error(email);
        RegistryEntity response=registryRepository.findByEmail(email);
        if(response==null) {
            throw new BadRequestException("El email no existe");
        }
        return registryMapper.toRegistryDto(response);
    }

    @Override
    public Object getAll() {
        List<RegistryEntity> response=registryRepository.findAll();
        response.forEach(registryMapper::toRegistryDto);
        return response;
    }

    @Override
    public Object updateUser(RegistryDto registryDto) {
        return null;
    }

    @Override
    public Object deleteUserById(@NotNull @Valid Long id) {
        if(id<=0 || !registryRepository.existsById(id)) {
            throw new NotFoundException("Invalid id");
        }
        registryRepository.deleteById(id);
        return "Successfully deleted user";
    }

}
