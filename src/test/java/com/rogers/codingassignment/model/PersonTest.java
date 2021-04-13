package com.rogers.codingassignment.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PersonTest {

    private static final List<Person> personList = new ArrayList<>();

    private static final Person youngestPerson = new Person((short) 15, "Youngest", LocalDate.of(2000, 01, 01), Gender.FEMALE, List.of());
    private static final Person middlePerson = new Person((short) 17, "Middle", LocalDate.of(2000, 01, 01), Gender.MALE, List.of());
    private static final Person oldestPerson = new Person((short) 20, "Oldest", LocalDate.of(2000, 01, 01), Gender.MALE, List.of());

    @BeforeAll
    static void setUp(){
        personList.addAll(List.of(middlePerson, oldestPerson, youngestPerson));
    }

    @Test
    @DisplayName("teenager method - between 13-18 years old and sorted by age youngest to oldest")
    void teenagers(){

        List<String> filteredPersonList = Person.teenagers(personList);

        Assertions.assertEquals(filteredPersonList.size(), 2); // "Oldest should be filtered out
        Assertions.assertEquals(filteredPersonList.get(0), youngestPerson.getName()); // should be "Youngest"
        Assertions.assertEquals(filteredPersonList.get(1), middlePerson.getName()); // should be "Middle"
    }
}
