package com.decision.engine.loan.domain;

import com.decision.engine.loan.dto.PersonDto;
import com.decision.engine.loan.exception.UserDataLoadException;
import com.decision.engine.loan.exception.UserNotFoundException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;

@Repository
@Slf4j
public class PersonDao {

    public PersonDto findByIdentifier(String identifier) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, PersonDto> persons;
        try (InputStream inputStream = new ClassPathResource("persons.json").getInputStream()) {
            persons = objectMapper.readValue(inputStream, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new UserDataLoadException();
        }

        PersonDto foundPerson = persons.get(identifier);
        if (Objects.isNull(foundPerson)) {
            throw new UserNotFoundException(identifier);
        }

        return foundPerson;
    }
}