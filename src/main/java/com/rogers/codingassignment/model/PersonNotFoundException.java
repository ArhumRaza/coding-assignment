package com.rogers.codingassignment.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Invalid ID")
public class PersonNotFoundException extends RuntimeException{

    public PersonNotFoundException(int id) {
        super(new StringBuilder().append("Person with id: ").append(id).append(" does not exist").toString());
    }

}
