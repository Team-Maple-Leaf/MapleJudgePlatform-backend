package org.mapleleaf.backend.repository;

import org.mapleleaf.backend.entity.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExampleRepository extends JpaRepository<Example, Long> {
    public List<Example> findByProblemId(Long problemId);
}
