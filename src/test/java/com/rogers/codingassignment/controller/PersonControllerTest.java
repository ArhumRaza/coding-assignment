package com.rogers.codingassignment.controller;

import com.rogers.codingassignment.model.Gender;
import com.rogers.codingassignment.model.Person;
import com.rogers.codingassignment.model.PersonNotFoundException;
import com.rogers.codingassignment.service.PersonService;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PersonController.class)
@TestPropertySource(locations = {"/application-test.properties"}) // grabs everything inside the file
public class PersonControllerTest {

    private final Person testPerson  = new Person((short) 26, "John", LocalDate.of(1994, 9, 2), Gender.MALE, List.of("Oakville"));

    @Value("${server.uri.url}")
    private String baseURL;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    @Test
    @DisplayName("retrieve all people - success")
    void retrieveAllPeople() throws Exception {
        BDDMockito.given(personService.findAll()).willReturn(List.of(testPerson));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/person").accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].age").value(Matchers.is((int) testPerson.getAge())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").value(Matchers.is(testPerson.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].dob").value(Matchers.is(testPerson.getDob().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].gender").value(Matchers.is(testPerson.getGender().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].address").value(Matchers.is(testPerson.getAddress())));

    }

    @Test
    @DisplayName("retrieve person - success")
    void retrievePerson() throws Exception {
        int id = 5;

        BDDMockito.given(personService.findById(id)).willReturn(testPerson);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/person/" + id).accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(Matchers.is((int) testPerson.getAge())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(Matchers.is(testPerson.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dob").value(Matchers.is(testPerson.getDob().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value(Matchers.is(testPerson.getGender().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value(Matchers.is(testPerson.getAddress())));

    }

    @Test
    @DisplayName("retrieve person - invalid ID - 404 not found")
    void retrievePersonIdNotFound() throws Exception {
        int id = 5;

        BDDMockito.given(personService.findById(id)).willThrow(new PersonNotFoundException(id));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/person/" + id).accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    @Test
    @DisplayName("create person - success")
    void createPerson() throws Exception {
        int id = 99;

        BDDMockito.given(personService.save(BDDMockito.any())).willReturn(id);

        String requestBody = new JSONObject()
                .put("age", 4)
                .put("name", "John")
                .put("dob", LocalDate.of(1996, 02, 01).toString())
                .put("gender", Gender.OTHER)
                .toString();

        mockMvc.perform(
                MockMvcRequestBuilders.post("/person").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().string("Location", baseURL + "/person/" + id));

    }

    @Test
    @DisplayName("update person - success")
    void updatePerson() throws Exception {
        int id = 1;

        BDDMockito.doNothing().when(personService).update(BDDMockito.eq(id), BDDMockito.isA(Person.class));

        String requestBody = new JSONObject()
                .put("age", 20)
                .put("name", "Doe")
                .put("dob", LocalDate.of(1996, 02, 01).toString())
                .put("gender", Gender.OTHER)
                .toString();

        mockMvc.perform(MockMvcRequestBuilders.put("/person/" + id)
                .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }

    @Test
    @DisplayName("delete person - success")
    void deletePerson() throws Exception {
        int id = 1;

        BDDMockito.doNothing().when(personService).delete(id);

        mockMvc.perform(MockMvcRequestBuilders.delete("/person/" + id))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}