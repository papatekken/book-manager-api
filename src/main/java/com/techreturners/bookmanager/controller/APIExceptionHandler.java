package com.techreturners.bookmanager.controller;

import com.techreturners.bookmanager.exception.BookAlreadyExistsException;
import com.techreturners.bookmanager.exception.BookNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class APIExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<Object> handleBookNotFoundException(
            BookNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>("Book not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BookAlreadyExistsException.class)
    public ResponseEntity<Object> handleBookAlreadyExistsException(
            BookAlreadyExistsException ex, WebRequest request) {
        return new ResponseEntity<>("Book already exists", HttpStatus.BAD_REQUEST);
    }
}
