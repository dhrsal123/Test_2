package com.ejercicio.demo.controller;

import com.ejercicio.demo.dto.MessageDto;
import com.ejercicio.demo.dto.RegistryDto;
import com.ejercicio.demo.dto.RegistryReturnDto;
import com.ejercicio.demo.service.RegistryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
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
    public ResponseEntity<Object> obtenerPorEmail(@RequestParam @Valid @Email(message = "Email deformado",regexp = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")  String email) {
        Object response=registryService.getByEmail(email);
        return new ResponseEntity<>(response,(response instanceof RegistryDto)?HttpStatus.OK:HttpStatus.NOT_FOUND);
    }
    @GetMapping("/eliminarPorId/{id}")
    public ResponseEntity<Object> eliminarPorId(@PathVariable Long id) {
        Object response=registryService.deleteUserById(id);
        return new ResponseEntity<>(response,(response instanceof String)?HttpStatus.OK:HttpStatus.NOT_FOUND);
    }
    @GetMapping("/obtenerTodos")
    public ResponseEntity<Object> obtenerTodos(@RequestParam(required = false) Long start, @RequestParam(required = false) Long end) {
        Object response=registryService.getAll();
        return new ResponseEntity<>(response,(response instanceof ArrayList<?>)?HttpStatus.OK:HttpStatus.NOT_FOUND);
    }
}
