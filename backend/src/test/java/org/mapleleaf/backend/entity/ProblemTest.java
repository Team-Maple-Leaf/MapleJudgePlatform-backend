package org.mapleleaf.backend.entity;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapleleaf.backend.repository.ExampleRepository;
import org.mapleleaf.backend.repository.ProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ProblemTest {
    @Autowired
    ExampleRepository exampleRepository;
    @Autowired
    ProblemRepository problemRepository;
    Example ex1, ex2, ex3;
    Problem problem;
    static final Long problemId = 1000L;

    @BeforeEach
    public void setup() {
        assertThat(problemRepository.findAll()).isEmpty();
        assertThat(exampleRepository.findAll()).isEmpty();
        ex1 = Example.builder()
                .input("ex1 input")
                .output("ex1 output")
                .build();
        ex2 = Example.builder()
                .input("ex2 input")
                .output("ex2 output")
                .build();
        ex3 = Example.builder()
                .input("ex3 input")
                .output("ex3 output")
                .build();
        problem = Problem.builder()
                .id(problemId)
                .title("title")
                .problemDesc("problem")
                .inputDesc("input")
                .outputDesc("output")
                .examples(Lists.list(ex1, ex2))
                .build();
    }

    @AfterEach
    public void teardown() {
        exampleRepository.deleteAll();
        problemRepository.deleteAll();
    }

    @Test
    public void entityUpdateTest() {
        String title = "test title";
        String desc = "test desc";
        String input = "test input";
        String output = "test output";
        problem = problemRepository.save(problem);
        problem.setTitle(title);
        problem.setProblemDesc(desc);
        problem.setInputDesc(input);
        problem.setOutputDesc(output);
        problemRepository.flush();

        Problem found = problemRepository
                .findById(problemId)
                .orElseThrow(IllegalAccessError::new);
        assertThat(found.getTitle()).isEqualTo(title);
        assertThat(found.getProblemDesc()).isEqualTo(desc);
        assertThat(found.getInputDesc()).isEqualTo(input);
        assertThat(found.getOutputDesc()).isEqualTo(output);

    }

    @Test
    public void associationTest() {
        problem = problemRepository.save(problem);
        List<Example> examples = exampleRepository.findAll();

        assertThat(examples.size()).isEqualTo(2);
        problem.addExample(ex3);
        problemRepository.flush();
        exampleRepository.flush();

        assertThat(exampleRepository.findAll().size()).isEqualTo(3);
    }

    @Test
    public void deleteProblemTest() {
        problem = problemRepository.save(problem);
        problemRepository.delete(problem);
        problemRepository.flush();
        exampleRepository.flush();

        assertThat(exampleRepository.findAll().size()).isEqualTo(0);
    }
}