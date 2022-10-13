package org.mapleleaf.backend.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity // db 테이블과 1:1 매핑되는 객체, enum에는 사용 불가
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "example")
public class Example {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="example_id")
    private Long id;

    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "problem_id")
    private Problem problem;

    public void setProblem(Problem problem){
        this.problem = problem;

        if(!problem.getExamples().contains(this)){
            problem.getExamples().add(this);
        }
    }

    @Column
    private String input;

    @Column
    private String output;


}
