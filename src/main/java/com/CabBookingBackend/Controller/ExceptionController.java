package com.CabBookingBackend.Controller;


import com.CabBookingBackend.Exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(AdminException.class)
    public ResponseEntity<String> employeeNotFound(AdminException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(CabException.class)
    public ResponseEntity<String> employeeNotFound(CabException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(CurrentUserSessionException.class)
    public ResponseEntity<String> employeeNotFound(CurrentUserSessionException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(CustomerException.class)
    public ResponseEntity<String> employeeNotFound(CustomerException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(DriverException.class)
    public ResponseEntity<String> employeeNotFound(DriverException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(TripBookingException.class)
    public ResponseEntity<String> employeeNotFound(TripBookingException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
