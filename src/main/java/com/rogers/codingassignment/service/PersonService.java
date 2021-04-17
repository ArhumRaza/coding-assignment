package com.rogers.codingassignment.service;

import com.rogers.codingassignment.model.Person;

import java.util.List;

public interface PersonService {

    List<Person> findAll();

    Person findById(int id);

    int save(Person person);

    void update(int id, Person person);

    void delete(int id);

}
