package org.mapleleaf.backend.repository;

import org.mapleleaf.backend.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    public Answer findFirstByProblemId_IdAndUserId(Long problemId, String UserId);
}
