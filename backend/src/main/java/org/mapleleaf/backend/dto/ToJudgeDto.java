package org.mapleleaf.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mapleleaf.backend.dto.problem.TestcaseDto;
import org.mapleleaf.backend.entity.Language;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToJudgeDto {
    @JsonProperty("answer_id")
    Long answerId;
    String code;
    Language language;
    List<TestcaseDto> testcases;
    @JsonProperty("time_limit")
    int timeLimit;
    @JsonProperty("memory_limit")
    int memoryLimit;
}
