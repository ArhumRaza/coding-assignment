package com.rogers.codingassignment.service.impl;

import com.rogers.codingassignment.model.Gender;
import com.rogers.codingassignment.model.Person;
import com.rogers.codingassignment.model.PersonNotFoundException;
import com.rogers.codingassignment.repository.PersonEntity;
import com.rogers.codingassignment.repository.PersonRepository;
import com.rogers.codingassignment.service.PersonService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class DbPersonServiceImplTest {

    @Mock
    PersonRepository personRepository;

    @InjectMocks
    PersonService personService = new DbPersonServiceImpl();

    private static final PersonEntity personEntity1 = new PersonEntity((short) 20, "John", LocalDate.of(2001, 02, 04), Gender.MALE, List.of("Oakville"));
    private static final PersonEntity personEntity2 = new PersonEntity((short) 25, "Doe", LocalDate.of(1996, 02, 01), Gender.OTHER, List.of("Toronto"));

    private static final int id = 1;
    private static final int invalidId = 99;


    @Test
    @DisplayName("find all person")
    public void findAll() {
        BDDMockito.given(personRepository.findAll()).willReturn(List.of(personEntity1, personEntity2));

        List<Person> personList = personService.findAll();

        Assertions.assertEquals(personList.size(), 2);

        Assertions.assertEquals(personList.get(0).getName(), personEntity1.getName());

        Assertions.assertEquals(personList.get(1).getName(), personEntity2.getName());
    }

    @Test
    @DisplayName("find person by id")
    public void findById() {
        BDDMockito.given(personRepository.findById(id)).willReturn(Optional.of(personEntity1));

        Person testPerson = personService.findById(id);

        Assertions.assertEquals(testPerson.getAge(), personEntity1.getAge());
        Assertions.assertEquals(testPerson.getName(), personEntity1.getName());
        Assertions.assertEquals(testPerson.getDob(), personEntity1.getDob());
        Assertions.assertEquals(testPerson.getGender(), personEntity1.getGender());
        Assertions.assertEquals(testPerson.getAddress(), personEntity1.getAddress());
    }

    @Test
    @DisplayName("find person by id - 404 not found due to invalid id")
    public void findByIdInvalidId() {
        BDDMockito.given(personRepository.findById(invalidId)).willReturn(Optional.empty());

        Assertions.assertThrows(PersonNotFoundException.class, () -> personService.findById(invalidId));
    }

    @Test
    @DisplayName("create new person")
    public void save() {
        Person person = new Person((short) 25, "Doe", LocalDate.of(1996, 02, 01), Gender.OTHER, List.of("Toronto"));

        personEntity2.setId(id);

        BDDMockito.given(personRepository.save(BDDMockito.isA(PersonEntity.class))).willReturn(personEntity2);

        Assertions.assertEquals(personService.save(person), personEntity2.getId());
    }

    @Test
    @DisplayName("update person by id")
    public void update() {
        Person updatedPerson = new Person((short) 25, "New Guy", LocalDate.of(1996, 02, 01), Gender.OTHER, List.of("Toronto"));
        PersonEntity updatedPersonEntity = new PersonEntity((short) 25, "New Guy", LocalDate.of(1996, 02, 01), Gender.OTHER, List.of("Toronto"));

        BDDMockito.given(personRepository.findById(id)).willReturn(Optional.of(updatedPersonEntity));
        BDDMockito.given(personRepository.save(BDDMockito.isA(PersonEntity.class))).willReturn(updatedPersonEntity);

        personService.update(id, updatedPerson);
    }

    @Test
    @DisplayName("update person by id - 404 not found due to invalid id")
    public void updateInvalidId() {
        Person updatedPerson = new Person((short) 25, "New Guy", LocalDate.of(1996, 02, 01), Gender.OTHER, List.of("Toronto"));

        BDDMockito.given(personRepository.findById(invalidId)).willReturn(Optional.empty());

        Assertions.assertThrows(PersonNotFoundException.class, () -> personService.update(invalidId, updatedPerson));
    }

    @Test
    @DisplayName("delete person by id")
    public void delete() {
        BDDMockito.given(personRepository.findById(id)).willReturn(Optional.of(personEntity1));
        BDDMockito.doNothing().when(personRepository).deleteById(id);

        personService.delete(id);
    }

}