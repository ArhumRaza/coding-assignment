package com.rogers.codingassignment.service.impl;

import com.rogers.codingassignment.model.Person;
import com.rogers.codingassignment.model.PersonNotFoundException;
import com.rogers.codingassignment.repository.PersonRepository;
import com.rogers.codingassignment.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class DbPersonServiceImpl implements PersonService {

    @Autowired
    PersonRepository repository;

    @Override
    public List<Person> findAll() {
        return repository.findAll();
    }

    @Override
    public Person findById(int id) {
        return repository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
    }

    @Override
    public int save(Person person) {
        Person p = repository.save(person);
        return p.getId();
    }

    @Override
    public void update(int id, Person person) {
        repository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
        person.setId(id);
        repository.save(person);
    }
}
