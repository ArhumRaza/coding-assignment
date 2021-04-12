package com.rogers.codingassignment.service.impl;
import com.rogers.codingassignment.model.Gender;
import com.rogers.codingassignment.model.Person;
import com.rogers.codingassignment.model.PersonNotFoundException;
import com.rogers.codingassignment.service.PersonService;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;

public class HashMapPersonServiceImplTest {

    PersonService personService = new HashMapPersonServiceImpl();

    private static final Person person1 = new Person((short) 20, "John", LocalDate.of(2001, 02, 04), Gender.MALE, List.of());
    private static final Person person2 = new Person((short) 25, "Doe", LocalDate.of(1996, 02, 01), Gender.OTHER, List.of());

    @BeforeAll
    static void setUp() {
        HashMapPersonServiceImpl.personMap.put(1, person1);
        HashMapPersonServiceImpl.personMap.put(2, person2);
    }

    @Test
    @DisplayName("find all person")
    public void findAll() {
        List<Person> personList = personService.findAll();
        Assertions.assertEquals(personList.size(), 2);
        Assertions.assertEquals(personList.get(0), person1);
        Assertions.assertEquals(personList.get(1), person2);
    }

    @Test
    @DisplayName("find person by id")
    public void findById() {
        Person person = personService.findById(1);
        Assertions.assertEquals(person, person1);
    }

    @Test
    @DisplayName("find person by id- invalid id (person not found)")
    public void findByIdInvalidId() {
        Assertions.assertThrows(PersonNotFoundException.class, () -> personService.findById(99)); //TODO - look into executables and passing lambda expressions
    }

    @Test
    @DisplayName("create a new person")
    public void save() {
        Person personNew = new Person((short) 25, "Doe", LocalDate.of(1996, 02, 01), Gender.OTHER, List.of());
        Assertions.assertEquals(personService.save(personNew), HashMapPersonServiceImpl.counterId);
        Assertions.assertEquals(HashMapPersonServiceImpl.personMap.get(HashMapPersonServiceImpl.counterId), personNew);
    }

    @Test
    @DisplayName("update a person by id")
    public void update() {
        Person personOld = HashMapPersonServiceImpl.personMap.get(2);
        Person personUpdate = new Person((short) 30, "Bob", LocalDate.of(1991, 02, 01), Gender.OTHER, List.of());
        personService.update(2, personUpdate);
        Assertions.assertNotEquals(HashMapPersonServiceImpl.personMap.get(2), personOld);
    }

    @Test
    @DisplayName("update person by id - invalid id (person not found)")
    public void updateInvalidId() {
        Person personUpdate = new Person((short) 30, "Bobby", LocalDate.of(1991, 02, 01), Gender.OTHER, List.of());
        Assertions.assertThrows(PersonNotFoundException.class, () -> personService.update(99, personUpdate));
    }

}
