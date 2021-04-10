package com.rogers.codingassignment.service.impl;

import com.rogers.codingassignment.model.Person;
import com.rogers.codingassignment.service.PersonService;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class HashMapPersonServiceImpl implements PersonService {
    private Map<Integer,Person> personMap = new HashMap<>();
    private static int ID = 0;

    @Override
    public List<Person> findAll() {
       return new ArrayList<>(personMap
               .values());
    }

    @Override
    public Person findById(int id) {
        return personMap.get(id);
    }

    @Override
    public int save(Person person) {
        ID++;
        personMap.put(ID, person);
        return 201;
    }

    @Override
    public void update(int id, Person person) {
        personMap.replace(id, person);
    }

//    @Override
//    public void delete(int id) {
//        personMap.remove(id);
//        ID--;
//    }
}
