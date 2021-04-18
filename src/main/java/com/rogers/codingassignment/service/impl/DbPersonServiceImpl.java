package com.rogers.codingassignment.service.impl;

import com.rogers.codingassignment.model.Person;
import com.rogers.codingassignment.model.PersonNotFoundException;
import com.rogers.codingassignment.repository.PersonEntity;
import com.rogers.codingassignment.repository.PersonRepository;
import com.rogers.codingassignment.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Primary
@Service
public class DbPersonServiceImpl implements PersonService {

    @Autowired
    PersonRepository repository;

    @Override
    public List<Person> findAll() {

        return repository
                        .findAll()
                        .stream()
                        .map(mapToPerson)
                        .collect(Collectors.toList());
    }

    @Override
    public Person findById(int id) {

        PersonEntity personEntity = repository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
        return mapToPerson.apply(personEntity);
    }

    @Override
    public int save(Person person) {
        PersonEntity personEntity = mapToPersonEntity.apply(person);
        personEntity = repository.save(personEntity);
        return personEntity.getId();
    }

    @Override
    public void update(int id, Person person) {
        repository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
        PersonEntity personEntity = mapToPersonEntity.apply(person);
        personEntity.setId(id);
        repository.save(personEntity);
    }

    @Override
    public void delete(int id) {
        repository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
        repository.deleteById(id);
    }

    private static final Function<PersonEntity, Person> mapToPerson = personEntity -> {
                    Person person = new Person(personEntity.getAge(), personEntity.getName(), personEntity.getDob(), personEntity.getGender(), personEntity.getAddress());
                    person.setId(personEntity.getId());
                    return person;
    };

    private static final Function<Person, PersonEntity> mapToPersonEntity = person -> {
        PersonEntity personEntity = new PersonEntity(person.getAge(), person.getName(), person.getDob(), person.getGender(), person.getAddress());
        return personEntity;
    };

}
