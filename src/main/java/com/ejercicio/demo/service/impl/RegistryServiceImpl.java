package com.ejercicio.demo.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ejercicio.demo.dto.MessageDto;
import com.ejercicio.demo.dto.RegistryDto;
import com.ejercicio.demo.dto.RegistryReturnDto;
import com.ejercicio.demo.entity.PhoneEntity;
import com.ejercicio.demo.entity.RegistryEntity;
import com.ejercicio.demo.repository.PhoneRepository;
import com.ejercicio.demo.repository.RegistryRepository;
import com.ejercicio.demo.service.RegistryService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RegistryServiceImpl implements RegistryService {
    RegistryRepository registryRepository;
    private PasswordEncoder passwordEncoder;
    PhoneRepository phoneRepository;

    @Override
    public Object register(RegistryDto registryDto) {
        if (registryDto.getEmail().isEmpty() || registryDto.getName().isEmpty() || registryDto.getPassword().isEmpty()
                || registryDto.getPhones().size() == 0) {
            return new MessageDto("Es necesario que ingreses todos tus datos");
        }
        if (registryRepository.existsByEmail(registryDto.getEmail())) {
            return new MessageDto("El correo ya esta registrado");
        }
        String validate = validate(registryDto.getEmail(), registryDto.getPassword(), registryDto.getName());
        if (validate != "Correcto") {
            return new MessageDto(validate);
        }
        RegistryEntity register = new RegistryEntity();
        // register.setId(UUID.randomUUID().toString());
        register.setName(registryDto.getName());
        register.setEmail(registryDto.getEmail());
        register.setPassword(passwordEncoder.encode(registryDto.getPassword()));
        List<PhoneEntity> phones = new ArrayList<>();
        registryDto.getPhones().forEach((phone) -> {
            PhoneEntity curEntity = new PhoneEntity(UUID.randomUUID().toString(), phone.getNumber(),
                    phone.getCitycode(), phone.getCountrycode());
            PhoneEntity exists = phoneRepository.findByNumberAndCountrycodeAndCitycode(phone.getNumber(),
                    phone.getCitycode(), phone.getCountrycode());
            if (exists != null) {
                phones.add(exists);
            } else {
                phoneRepository.save(curEntity);
                phones.add(curEntity);
            }
        });
        LocalDateTime creation = LocalDateTime.now();
        register.setUUIDId(UUID.randomUUID().toString());
        register.setPhones(phones);
        register.setCreated(creation);
        register.setModified(creation);
        register.setLast_login(creation);
        register.setIsactive(true);
        register.setToken(UUID.randomUUID().toString());
        register.setExpireDate(new Date(new Date().getTime() + 604800000));
        try {
            registryRepository.save(register);
        } catch (Exception e) {
            return new MessageDto("Error al crear el usuario" + e.getMessage());
        }
        RegistryReturnDto response = new RegistryReturnDto(register.getId(), register.getCreated(),
                register.getModified(), register.getLast_login(), register.getToken(), register.getIsactive());
        return response;
    }

    public String validate(String email, String password, String name) {
        if (Pattern.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", email) == false) {
            return "Email deformado";
        } else if (Pattern.matches("^((?=\\S*?[A-Z])(?=\\S*?[a-z])(?=\\S*?[0-9]).{6,})$", password) == false) {
            return "La contraseña debe tener: 1 Mayuscula, 1 Minuscula, 1 Numero y ser como minimo de 6 caracteres";
        } else if (name.length() < 3) {
            return "Ingresa un nombre valido";
        }
        return "Correcto";
    }
}
