package com.ejercicio.demo.mapper;

import com.ejercicio.demo.dto.RegistryDto;
import com.ejercicio.demo.dto.RegistryReturnDto;
import com.ejercicio.demo.entity.RegistryEntity;
import jakarta.persistence.EntityListeners;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Date;

@Mapper(componentModel = "spring",imports={Date.class})
public interface RegistryMapper {
    @Mapping(expression="java(java.util.UUID.randomUUID().toString())",target="UUID")
    @Mapping(expression="java(new Date(new Date().getTime() + 604800000))",target="expireDate")
    @Mapping(constant = "true",target="isActive")
    @Mapping(expression="java(java.util.UUID.randomUUID().toString())",target="token")
    RegistryEntity toRegistryEntity(RegistryDto registryDto);
    RegistryDto toRegistryDto(RegistryEntity registryEntity);
    RegistryReturnDto toRegistryReturnDto(RegistryEntity registryEntity);
}
