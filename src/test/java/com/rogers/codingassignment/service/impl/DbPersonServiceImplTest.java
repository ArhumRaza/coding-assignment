package com.rogers.codingassignment.service.impl;

import com.rogers.codingassignment.model.Gender;
import com.rogers.codingassignment.model.Person;
import com.rogers.codingassignment.model.PersonNotFoundException;
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

    private static final Person person1 = new Person((short) 20, "John", LocalDate.of(2001, 02, 04), Gender.MALE, List.of("Oakville"));
    private static final Person person2 = new Person((short) 25, "Doe", LocalDate.of(1996, 02, 01), Gender.OTHER, List.of("Toronto"));
    private static final Person updatedPerson = new Person((short) 25, "New Guy", LocalDate.of(1996, 02, 01), Gender.OTHER, List.of("Toronto"));

    private static final int id = 1;
    private static final int invalidId = 99;


    @Test
    @DisplayName("find all person")
    public void findAll() {
        BDDMockito.given(personRepository.findAll()).willReturn(List.of(person1, person2));
        Assertions.assertEquals(personService.findAll().size(), 2);
    }

    @Test
    @DisplayName("find person by id")
    public void findById() {
        BDDMockito.given(personRepository.findById(id)).willReturn(Optional.of(person1));
        Assertions.assertEquals(personService.findById(id), person1);
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
        person2.setId(id);
        BDDMockito.given(personRepository.save(person2)).willReturn(person2);
        Assertions.assertEquals(personService.save(person2), person2.getId());
    }

    @Test
    @DisplayName("update person by id")
    public void update() {
        BDDMockito.given(personRepository.findById(id)).willReturn(Optional.of(person1));
        BDDMockito.given(personRepository.save(updatedPerson)).willReturn(updatedPerson);
        personService.update(id, updatedPerson);
    }

    @Test
    @DisplayName("update person by id - 404 not found due to invalid id")
    public void updateInvalidId() {
        BDDMockito.given(personRepository.findById(invalidId)).willReturn(Optional.empty());
        Assertions.assertThrows(PersonNotFoundException.class, () -> personService.update(invalidId, updatedPerson));
    }

    @Test
    @DisplayName("delete person by id")
    public void delete() {
        BDDMockito.given(personRepository.findById(id)).willReturn(Optional.of(person1));
        BDDMockito.doNothing().when(personRepository).deleteById(id);
        personService.delete(id);
    }

}