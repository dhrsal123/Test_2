package com.ejercicio.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.ejercicio.demo.dto.MessageDto;
import com.ejercicio.demo.dto.RegistryDto;
import com.ejercicio.demo.service.RegistryService;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class RegistryController {
    private RegistryService registryService;

    @PostMapping("/registro")
    public ResponseEntity<Object> register(@RequestBody RegistryDto registryDto) {
        Object response = registryService.register(registryDto);
        return new ResponseEntity<>(response,
                (response instanceof MessageDto) ? HttpStatus.BAD_REQUEST : HttpStatus.CREATED);
    }

}
