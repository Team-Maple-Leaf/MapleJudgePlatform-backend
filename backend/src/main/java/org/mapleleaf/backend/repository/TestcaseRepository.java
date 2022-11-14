package org.mapleleaf.backend.repository;

import org.mapleleaf.backend.entity.Testcase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestcaseRepository extends JpaRepository<Testcase, Long> {
    public List<Testcase> findByProblemId(Long problemId);
}