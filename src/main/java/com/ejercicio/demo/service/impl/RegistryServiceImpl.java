package com.ejercicio.demo.service.impl;

import com.ejercicio.demo.dto.EmailDto;
import com.ejercicio.demo.exceptions.BadRequestException;
import com.ejercicio.demo.exceptions.NotFoundException;
import com.ejercicio.demo.mapper.RegistryMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

@Slf4j
@Service
@RequiredArgsConstructor
@Validated
public class RegistryServiceImpl implements RegistryService {
    private final RegistryRepository registryRepository;
    private final PasswordEncoder passwordEncoder;
    private final PhoneRepository phoneRepository;
    private final RegistryValidatorHelper registryValidatorHelper;
    private final RegistryMapper registryMapper;
    //Arreglar exepciones, tverminar crud, registar, actualizar, buscar por email, buscar todos paginado y eliminar
    /*Hecho/por hacer
    [+] eliminar (hecho?)
    [+-] crear
    [+-] buscar email
    [+] buscar todos / paginado(~hecho)
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
    public Object getByEmail(@NotNull @Valid EmailDto email) {
        //[] completly validate the email
        RegistryEntity response=registryRepository.findByEmail(email.getEmail());
        if(response==null) {
            throw new BadRequestException("El email no existe");
        }
        return registryMapper.toRegistryReturnDto(response);
    }
    @Override
    public Object getAll(int page, int size) {
        List<RegistryEntity> response=registryRepository.findAll(PageRequest.of(page,size)).getContent();
        List<RegistryReturnDto> ret=new ArrayList<>();
        for (RegistryEntity t : response) {
            ret.add(registryMapper.toRegistryReturnDto(t));
        }
        return ret;
    }
    @Override
    public Object updateUser(RegistryDto registryDto) {
        registryValidatorHelper.validate(registryDto);
        RegistryEntity response=registryRepository.findByEmail(registryDto.getEmail());
        if(response==null) {
            throw new BadRequestException("Usuario inexistente");
        }
        response.setEmail(registryDto.getEmail());
        response.setName(registryDto.getName());
        response.setPassword(passwordEncoder.encode(registryDto.getPassword()));
        registryRepository.save(response);
        return registryMapper.toRegistryReturnDto(response);
    }
    @Override
    public Object deleteUserById(@NotNull @Valid Long id) {
        if(!registryRepository.existsById(id)) {
            throw new NotFoundException("User not found");
        }
        registryRepository.deleteById(id);
        return "Successfully deleted user";
    }

}
