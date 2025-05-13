package com.ejercicio.demo.controller;

import com.ejercicio.demo.dto.EmailDto;
import com.ejercicio.demo.dto.MessageDto;
import com.ejercicio.demo.dto.RegistryDto;
import com.ejercicio.demo.dto.RegistryReturnDto;
import com.ejercicio.demo.service.RegistryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api")
@Validated
@RequiredArgsConstructor
public class RegistryController {
    private final RegistryService registryService;

    @PostMapping("/registro")
    public ResponseEntity<Object> register(@RequestBody @Valid RegistryDto registryDto) {
        Object response = registryService.register(registryDto);
        return new ResponseEntity<>(response,
                (response instanceof MessageDto) ? HttpStatus.BAD_REQUEST : HttpStatus.CREATED);
    }
    @GetMapping("/obtenerPorEmail")
    public ResponseEntity<Object> obtenerPorEmail(@Valid @RequestBody EmailDto email) {
        Object response=registryService.getByEmail(email);
        return new ResponseEntity<>(response,(response instanceof RegistryDto)?HttpStatus.OK:HttpStatus.NOT_FOUND);
    }
    @GetMapping("/eliminarPorId/{id}")
    public ResponseEntity<Object> eliminarPorId(@PathVariable @Min(value = 1, message = "ID must be greater than or equal to 1")  Long id) {
        //anotacion min no funcional
        Object response=registryService.deleteUserById(id);
        return new ResponseEntity<>(response,(response instanceof String)?HttpStatus.OK:HttpStatus.NOT_FOUND);
    }
    @GetMapping("/obtenerTodos")
    public ResponseEntity<Object> obtenerTodos(@RequestParam(required = false,defaultValue = "0") @Min(0) int page, @RequestParam(required = false,defaultValue = "10") @Min(1) int size) {
        Object response=registryService.getAll(page,size);
        return new ResponseEntity<>(response,(response instanceof ArrayList<?>)?HttpStatus.OK:HttpStatus.NOT_FOUND);
    }
    @PostMapping("/actualizar")
    public ResponseEntity<Object> actualizar(@RequestBody @Valid RegistryDto registryDto) {
        return new ResponseEntity<>(registryService.updateUser(registryDto),HttpStatus.OK);
    }
}
