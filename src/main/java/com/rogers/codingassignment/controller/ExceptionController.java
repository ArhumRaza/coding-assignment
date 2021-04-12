package com.rogers.codingassignment.controller;

import com.rogers.codingassignment.model.PersonNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public void handleConstaintViolations() {
        // used to convert the 500 into 400
        // https://stackoverflow.com/questions/58614373/how-to-convert-constraintviolationexception-500-error-to-400-bad-request
    }

//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ExceptionHandler(PersonNotFoundException.class)
//    public void handlePersonNotFoundException() {
//
//    }

}
