package org.mapleleaf.backend.repository;

import org.mapleleaf.backend.entity.Answer;
import org.mapleleaf.backend.entity.AnswerState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    public Answer findFirstByProblemId_IdAndUserId(Long problemId, String UserId);
    public List<Answer> findAllByProblemId_IdAndUserId(Long problemId, String userId);
}
