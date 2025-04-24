package com.ejercicio.demo.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import org.springframework.core.env.Environment;
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
    private Environment environment;

    @Override
    public Object register(RegistryDto registryDto) {
        try {
            if (registryDto.getEmail().isEmpty() || registryDto.getName().isEmpty()
                    || registryDto.getPassword().isEmpty()
                    || registryDto.getPhones().size() == 0) {
                return new MessageDto("Es necesario que ingreses todos tus datos");
            }
        } catch (Exception e) {
            return new MessageDto("Es necesario que ingreses todos tus datos");
        }
        String validate = validate(registryDto.getEmail(), registryDto.getPassword(), registryDto.getName());
        if (validate != "Correcto") {
            return new MessageDto(validate);
        }
        if (registryRepository.existsByEmail(registryDto.getEmail())) {
            return new MessageDto("El correo ya esta registrado");
        }
        RegistryEntity register = new RegistryEntity();
        register.setName(registryDto.getName());
        register.setEmail(registryDto.getEmail());
        register.setPassword(passwordEncoder.encode(registryDto.getPassword()));
        List<PhoneEntity> phones = new ArrayList<>();
        registryDto.getPhones().forEach((phone) -> {
            try {
                if (phone.getCitycode().length() >= 1 && phone.getCountrycode().length() >= 1
                        && phone.getNumber().length() >= 4) {
                    PhoneEntity curEntity = new PhoneEntity();
                    curEntity.setCitycode(phone.getCitycode());
                    curEntity.setCountrycode(phone.getCountrycode());
                    curEntity.setNumber(phone.getNumber());
                    // PhoneEntity exists = phoneRepository.findByNumber(phone.getNumber());
                    // if (exists != null) {
                    // phones.add(exists);
                    // } else {

                    // Deberia haber problema por numeros duplicados??
                    phoneRepository.save(curEntity);
                    phones.add(curEntity);
                    // }
                }
            } catch (Exception e) {
            }
        });
        if (phones.size() == 0) {
            return new MessageDto("Numero telefonico no valido");
        }
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
            return new MessageDto("Error al crear el usuario");
        }
        RegistryReturnDto response = new RegistryReturnDto(register.getId(), register.getCreated(),
                register.getModified(), register.getLast_login(), register.getToken(), register.getIsactive());
        return response;
    }

    public String validate(String email, String password, String name) {
        try {
            if (Pattern.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", email) == false) {
                return "Email deformado";
            } else if (Pattern.matches(environment.getProperty("app.regexRegistry"), password) == false) {
                return "La contraseña debe tener: 1 Mayuscula, 1 Minuscula, 1 Numero y ser como minimo de 6 caracteres";
            } else if (name.length() < 3 || Pattern.matches("[^a-zA-Z]", name) == true) {
                return "Ingresa un nombre valido";
            }
            return "Correcto";
        } catch (Exception e) {
            if (e.getMessage().equals(
                    "Cannot invoke \"org.springframework.core.env.Environment.getProperty(String)\" because \"this.environment\" is null")) {
                try {
                    if (Pattern.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", email) == false) {
                        return "Email deformado";
                    } else if (Pattern.matches("^((?=\\S*?[A-Z])(?=\\S*?[a-z])(?=\\S*?[0-9]).{6,})$",
                            password) == false) {
                        return "La contraseña debe tener: 1 Mayuscula, 1 Minuscula, 1 Numero y ser como minimo de 6 caracteres";
                    } else if (name.length() < 3 || Pattern.matches("[^a-zA-Z]", name) == true) {
                        return "Ingresa un nombre valido";
                    }
                    return "Correcto";
                } catch (Exception e2) {

                }
            }
            return "Es necesario que ingreses todos tus datos";
        }
    }
}
