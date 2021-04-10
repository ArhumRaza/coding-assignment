package com.rogers.codingassignment.service.impl;

import com.rogers.codingassignment.model.Person;
import com.rogers.codingassignment.model.PersonNotFoundException;
import com.rogers.codingassignment.service.PersonService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HashMapPersonServiceImpl implements PersonService {
    private Map<Integer,Person> personMap = new HashMap<>();
    private static int COUNTER_ID = 0;

    @Override
    public List<Person> findAll() {
       return new ArrayList<>(personMap
               .values());
    }

    @Override
    public Person findById(int id) {
        return Optional
                        .ofNullable(personMap.get(id))
                        .orElseThrow(() -> new PersonNotFoundException(id));
    }

    @Override
    public int save(Person person) {
        COUNTER_ID++;
        personMap.put(COUNTER_ID, person);
        return COUNTER_ID;
    }

    @Override
    public void update(int id, Person person) {
        Optional
                .ofNullable(personMap.replace(id, person))
                .orElseThrow(() -> new PersonNotFoundException(id));
    }

}
