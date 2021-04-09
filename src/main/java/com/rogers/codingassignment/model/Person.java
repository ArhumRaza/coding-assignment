package com.rogers.codingassignment.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/*
Create an immutable class - Person
age (short) ;
name - String;
dob - LocalDate;
gender - Enum Class Male/Female/Other;
List<String> address;
 */
public class Person {

    private short age;
    private String name;
    private LocalDate dob;
    private Gender gender;
    private List<String> address;

    public Person(short age, String name, LocalDate dob, Gender gender, List<String> address) {
        this.age = age;
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
    }

    public short getAge() {
        return age;
    }

    public void setAge(short age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public List<String> getAddress() {
        return address;
    }

    public void setAddress(List<String> address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", dob=" + dob +
                ", gender=" + gender +
                ", address=" + address +
                '}';
    }

    /*
        // Return the names of all teens (age 13 - 18) --> sort youngest to oldest
         */
    public static List<String> teenagers(List<Person> people) {

        return people
                .stream()
                .filter(person -> person.getAge() > 13 && person.getAge() < 18)
                .sorted(Comparator.comparingInt(Person::getAge))
                .map(Person::getName)
                .collect(Collectors.toList());
    }

}
