package org.mapleleaf.backend.service;

import lombok.RequiredArgsConstructor;
import org.mapleleaf.backend.dto.AnswerStatusDto;
import org.mapleleaf.backend.entity.Answer;
import org.mapleleaf.backend.entity.AnswerState;
import org.mapleleaf.backend.repository.AnswerRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class AnswerStateService {
    private final AnswerRepository repository;

    public AnswerStatusDto getStateByProblemIdAndUserId(Long problemId, String userId)
    {
        Answer answer = repository.findFirstByProblemId_IdAndUserId(problemId, userId);
        AnswerState answerState = answer.getStateId();
        return new AnswerStatusDto(answerState);
    }
}
