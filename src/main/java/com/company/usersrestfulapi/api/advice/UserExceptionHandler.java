package com.company.usersrestfulapi.api.advice;

import com.company.usersrestfulapi.api.controller.InvalidRangeException;
import com.company.usersrestfulapi.api.service.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;
import java.util.StringJoiner;

@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(UserException exc) {

        ErrorResponse error = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(exc.getMessage())
                .timestamp(System.currentTimeMillis()).build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(MethodArgumentNotValidException exc) {

        StringJoiner message = new StringJoiner(", ");
        exc.getBindingResult().getFieldErrors().forEach(fieldError ->
                message.add(fieldError.getField() + " - " + fieldError.getDefaultMessage()));

        ErrorResponse error = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(message.toString())
                .timestamp(System.currentTimeMillis()).build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(DateTimeParseException exc) {

        ErrorResponse error = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message("Invalid date format (yyyy-MM-dd)")
                .timestamp(System.currentTimeMillis()).build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(InvalidRangeException exc) {

        ErrorResponse error = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(exc.getMessage())
                .timestamp(System.currentTimeMillis()).build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
