package org.mapleleaf.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapleleaf.backend.dto.AnswerStatusDto;
import org.mapleleaf.backend.entity.Answer;
import org.mapleleaf.backend.entity.AnswerState;
import org.mapleleaf.backend.repository.AnswerRepository;
import org.mapleleaf.backend.repository.AnswerStatusRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class AnswerStatusService {
    private final AnswerRepository answerRepository;
    private final AnswerStatusRepository answerStatusRepository;

    public void update(AnswerStatusDto dto) {
        log.info("update info: {}", dto.toString());
        Answer answer = answerRepository.findById(dto.getAnswerId()).orElseThrow(IllegalArgumentException::new);
        AnswerState answerState = answer.getStateId();
        answerState.setTime(dto.getTime());
        answerState.setMemory(dto.getMemory());
        answerState.setResult(dto.getResult());
        answerStatusRepository.save(answerState);
    }
}
