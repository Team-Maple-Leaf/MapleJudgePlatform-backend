package org.mapleleaf.backend.repository;

import org.mapleleaf.backend.entity.AnswerState;
import org.mapleleaf.backend.entity.Example;
import org.mapleleaf.backend.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    public Member findByMaple(String maple);

    public boolean existsByMaple(String maple);
}
