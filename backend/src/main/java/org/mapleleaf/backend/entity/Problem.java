package org.mapleleaf.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity // db 테이블과 1:1 매핑되는 객체, enum에는 사용 불가
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "problem")
public class Problem {

    // @Column(name = "name", nullable = false, length = 10)
    @Id
    @Column(name = "problem_id")
    private Long id;

    private String title;

    @Column(name = "problem_desc", columnDefinition = "TEXT")
    private String problemDesc;

    @Column(name = "input_desc", columnDefinition = "TEXT")
    private String inputDesc;

    @Column(name = "output_desc", columnDefinition = "TEXT")
    private String outputDesc;

    @Column(name = "limit_memory")
    private int limitMemory;

    @Column(name = "limit_time")
    private int limitTime;

    @OneToMany(
            mappedBy="problem",
            cascade = {CascadeType.ALL},
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<Example> examples;

    public void addExample(Example example){
        this.examples.add(example);
        if(example.getProblem() != this){
            example.setProblem(this);
        }
    }


}
