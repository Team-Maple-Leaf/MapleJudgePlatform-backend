package org.mapleleaf.backend.repository;

import org.mapleleaf.backend.entity.AnswerState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerStatusRepository extends JpaRepository<AnswerState, Long> {
}
