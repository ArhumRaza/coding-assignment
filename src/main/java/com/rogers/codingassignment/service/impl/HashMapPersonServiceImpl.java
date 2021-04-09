package com.rogers.codingassignment.service.impl;

import com.rogers.codingassignment.model.Gender;
import com.rogers.codingassignment.model.Person;
import com.rogers.codingassignment.service.PersonService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class HashMapPersonServiceImpl implements PersonService {

    @Override
    public List<Person> findAll() {
       Person person = new Person((short) 18, "John", LocalDate.of(2002, 9,12), Gender.MALE, List.of());
       List<Person> personList = new ArrayList<>();
       personList.add(person);
        return personList;
    }

    @Override
    public Person findById(int id) {
        Person person = new Person((short) 18, "Bob", LocalDate.of(2002, 9,12), Gender.MALE, List.of());
        return person;
    }

    @Override
    public int save(Person person) {
        return 0;
    }

    @Override
    public void update(int id, Person person) {

    }
}
