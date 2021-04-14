package com.rogers.codingassignment;

import com.rogers.codingassignment.model.Gender;
import com.rogers.codingassignment.model.Person;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private final Person testPerson = new Person((short) 20, "TestPerson", LocalDate.of(2000, 01, 01), Gender.MALE, List.of("OAKVILLE"));

    @BeforeEach
    void setUp() throws Exception{

        String requestBody = new JSONObject()
                .put("age", testPerson.getAge())
                .put("name", testPerson.getName())
                .put("dob", testPerson.getDob().toString())
                .put("gender", testPerson.getGender())
                .toString();

        mockMvc.perform(
                MockMvcRequestBuilders.post("/person").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isCreated());

    }

    @Test
    @Order(1)
    @DisplayName("retrieve all person Integration Test")
    void retrievePerson() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/person").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].age").value(Matchers.is((int) testPerson.getAge())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").value(Matchers.is(testPerson.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].dob").value(Matchers.is(testPerson.getDob().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].gender").value(Matchers.is(testPerson.getGender().toString())));

    }

    @Test
    @Order(2)
    @DisplayName("retrieve person by id Integration Test")
    void retrievePersonById() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/person/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(Matchers.is((int) testPerson.getAge())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(Matchers.is(testPerson.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dob").value(Matchers.is(testPerson.getDob().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value(Matchers.is(testPerson.getGender().toString())));
    }

    @Test
    @Order(3)
    @DisplayName("create person Integration Test")
    void createPerson() throws Exception {

        String requestBody = new JSONObject()
                .put("age", testPerson.getAge())
                .put("name", testPerson.getName())
                .put("dob", testPerson.getDob().toString())
                .put("gender", testPerson.getGender().toString())
                .toString();

        mockMvc.perform(
                MockMvcRequestBuilders.post("/person").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"));

    }

    @Test
    @Order(4)
    @DisplayName("update person - success Integration Test")
    void updatePerson() throws Exception {

        String requestBody = new JSONObject()
                .put("age", 20)
                .put("name", "John")
                .put("dob", LocalDate.of(2000, 01,01).toString())
                .put("gender", Gender.OTHER.toString())
                .toString();

        mockMvc.perform(
                MockMvcRequestBuilders.put("/person/1").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/person/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(Matchers.is((int) 20)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(Matchers.is("John")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dob").value(Matchers.is(LocalDate.of(2000, 01,01).toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value(Matchers.is(Gender.OTHER.toString())));

    }

}

