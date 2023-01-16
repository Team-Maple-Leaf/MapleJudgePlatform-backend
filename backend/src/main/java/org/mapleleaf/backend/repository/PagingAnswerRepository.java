package org.mapleleaf.backend.repository;

import org.mapleleaf.backend.dto.problem.ProblemDto;
import org.mapleleaf.backend.entity.Answer;
import org.mapleleaf.backend.entity.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagingAnswerRepository extends JpaRepository<Problem, Long> {
}
