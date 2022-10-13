package org.mapleleaf.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.mapleleaf.backend.dto.SubmitDto;
import org.mapleleaf.backend.entity.Language;
import org.mapleleaf.backend.entity.Problem;
import org.mapleleaf.backend.repository.AnswerStateRepository;
import org.mapleleaf.backend.repository.ProblemRepository;
import org.mapleleaf.backend.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AnswerControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProblemRepository problemRepository;
    @Autowired
    private AnswerService answerService;
    @Autowired
    private AnswerStateRepository answerStateRepository;

    @Test
    public void testUserIntroduction() throws Exception {
        Problem problem = problemRepository.save(
                Problem.builder()
                        .id(1000L)
                        .title("Hello")
                        .inputDesc("empty")
                        .problemDesc("print hello")
                        .outputDesc("hello")
                        .examples(Lists.newArrayList())
                        .build()
        );
        SubmitDto dto = new SubmitDto();
        dto.setUserId("test1");
        dto.setCode("print(\"hello\")");
        dto.setLanguage(Language.C);
        answerService.submit(dto, problem.getId());
        mockMvc.perform(get("/v1/answers/1"))
                .andExpect(status().isOk())
                .andDo(print());
        mockMvc.perform(get("/v1/answers"))
                .andExpect(status().isOk())
                .andDo(print());
        dto.setUserId("test2");
        mockMvc.perform(post("/v1/submit/1000")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(dto)));
        mockMvc.perform(get("/v1/answers/2"))
                .andExpect(status().isOk())
                .andDo(print());
        mockMvc.perform(get("/v1/answers"))
                .andExpect(status().isOk())
                .andDo(print());
    }

}