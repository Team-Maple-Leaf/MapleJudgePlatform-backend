package org.mapleleaf.backend.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity // db 테이블과 1:1 매핑되는 객체, enum에는 사용 불가
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "example")
public class Example {

    @Id
    private Long id;

    // @ManyToOne
    @Column(name = "problem_id")
    private Long problemId;

    private String input;

    private String output;
}
