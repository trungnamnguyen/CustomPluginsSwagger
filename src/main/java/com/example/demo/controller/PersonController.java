package com.example.demo.controller;

import com.example.demo.dto.PersonDTO;
import com.example.demo.dto.StudentDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
public class PersonController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public PersonDTO getPerson() throws JsonProcessingException {
        PersonDTO personDTO = new PersonDTO(12, 1,Arrays.asList(new StudentDTO()));
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
//        String result = mapper.writeValueAsString(personDTO);
        return personDTO;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<PersonDTO> getAllPerson() throws JsonProcessingException {
        return Arrays.asList(new PersonDTO(12, 1,Arrays.asList(new StudentDTO())));
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public PersonDTO addPerson(@RequestBody @Valid PersonDTO personDTO) {
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
//        String result = mapper.writeValueAsString(personDTO);
        return personDTO;
    }
}
