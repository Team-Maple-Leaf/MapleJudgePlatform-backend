package org.mapleleaf.backend.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapleleaf.backend.repository.AnswerRepository;
import org.mapleleaf.backend.repository.AnswerStateRepository;
import org.mapleleaf.backend.repository.ProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class AnswerTest {
    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    AnswerStateRepository answerStateRepository;
    @Autowired
    ProblemRepository problemRepository;

    @BeforeEach
    public void setup()
    {
        Problem p = problemRepository.save(
                Problem
                        .builder()
                        .id(1000L)
                        .inputDesc("input")
                        .outputDesc("output")
                        .problemDesc("desc")
                        .title("title")
                        .build()
        );
        Answer answer = Answer
                .builder()
                .userId("userid")
                .stateId(
                        AnswerState
                                .builder()
                                .result(Result.ACCEPTED)
                                .memory(100)
                                .time(100).build())
                .language(Language.C)
                .code("test")
                .date(new Date())
                .build();
        answer.setProblemId(p);
        answerRepository.save(answer);
    }

    @AfterEach
    public void teardown()
    {
        problemRepository.deleteAll();
        answerRepository.deleteAll();
        answerStateRepository.deleteAll();
    }

    @Test
    public void insertTest()
    {
        assertThat(answerRepository.findAll().size()).isEqualTo(1);
        assertThat(answerStateRepository.findAll().size()).isEqualTo(1);
        assertThat(problemRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    public void deleteTest()
    {
        answerRepository.deleteAll();
        assertThat(answerRepository.findAll().size()).isEqualTo(0);
        assertThat(answerStateRepository.findAll().size()).isEqualTo(0);
        assertThat(problemRepository.findAll().size()).isEqualTo(1);
    }
}