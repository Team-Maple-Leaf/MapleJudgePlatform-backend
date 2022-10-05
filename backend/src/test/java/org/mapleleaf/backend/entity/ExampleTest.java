package org.mapleleaf.backend.entity;

import org.junit.jupiter.api.Test;
import org.mapleleaf.backend.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class ExampleTest {
    @Autowired
    AnswerRepository repository;
    @Test
    public void test() {
        repository.findAll();
    }
}