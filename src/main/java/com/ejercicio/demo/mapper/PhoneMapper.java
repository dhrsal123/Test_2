package com.ejercicio.demo.mapper;

import com.ejercicio.demo.dto.PhoneDto;
import com.ejercicio.demo.entity.PhoneEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PhoneMapper {
    PhoneEntity toPhoneEntity(PhoneDto phoneDto);
}
