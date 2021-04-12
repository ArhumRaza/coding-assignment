package com.rogers.codingassignment.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table
public class Person {

    @Id
    @GeneratedValue
    private int id;

    private short age;

    @NotNull
    @NotEmpty
    @NotBlank
    private String name;

    @Past
    @NotNull
    private LocalDate dob;

    @NotNull
    private Gender gender;

    @ElementCollection
    private List<String> address = new ArrayList<>();

    public Person() {

    }

    public Person(short age, String name, LocalDate dob, Gender gender, List<String> address) {
        this.age = age;
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public static List<String> teenagers(List<Person> people) {

        return people
                .stream()
                .filter(person -> person.getAge() > 13 && person.getAge() < 18)
                .sorted(Comparator.comparingInt(Person::getAge))
                .map(Person::getName)
                .collect(Collectors.toList());
    }

}
