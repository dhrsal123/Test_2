package com.ejercicio.demo.controller;

import com.ejercicio.demo.dto.MessageDto;
import com.ejercicio.demo.exceptions.BadRequestException;
import com.ejercicio.demo.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestControllerAdvicer {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<MessageDto> handleException(Exception e) {
        return new ResponseEntity<>(new MessageDto("Something went wrong "+e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<MessageDto> handleBadRequestException(BadRequestException exception) {
        MessageDto messageDto = new MessageDto(exception.getMessage());
        return new ResponseEntity<>(messageDto, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<MessageDto> handleNotFoundException(NotFoundException exception) {
        MessageDto messageDto = new MessageDto(exception.getMessage());
        return new ResponseEntity<>(messageDto, HttpStatus.NOT_FOUND);
    }
}
