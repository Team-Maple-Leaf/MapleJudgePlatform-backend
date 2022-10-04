package org.mapleleaf.backend.entity;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
public class AnswerState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
    ** JAVA의 enum 사용
    ** @Enumerated(EnumType.ORDINAL)
    ** @Enumerated(EnumType.STRING)
    */
    @Column(length = 45)
    private String result;

    @Column
    private int memory;

    @Column
    private int time;
}
