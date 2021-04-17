package com.rogers.codingassignment.controller;

import com.rogers.codingassignment.model.Person;
import com.rogers.codingassignment.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/person")
class PersonController {

    private static final String URL_PATH_ID = "/{id}";

    @Autowired
    PersonService personService;

    @Autowired
    @Qualifier("baseURL")
    String baseURL;

    @GetMapping
    public ResponseEntity<List<Person>> retrieveAllPeople() {
        List<Person> personList = personService.findAll();
        return ResponseEntity.ok(personList);
    }

    @GetMapping(URL_PATH_ID)
    public ResponseEntity<Person> retrievePerson(@PathVariable int id) {
        Person person = personService.findById(id);
        return ResponseEntity.ok(person);
    }

    @PostMapping
    public ResponseEntity<String> createPerson(@RequestBody Person person) {
        int personId = personService.save(person);
        return ResponseEntity.created(URI.create(baseURL + "/person/" + personId)).build();
    }

    @PutMapping(URL_PATH_ID)
    public ResponseEntity<Void> updatePerson(@PathVariable int id, @RequestBody Person person) {
        personService.update(id,person);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(URL_PATH_ID)
    public ResponseEntity<Void> deletePerson(@PathVariable int id) {
        personService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
