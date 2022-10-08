package org.mapleleaf.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Data       // @ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor 를 자동으로 넣어준다.
@Entity     // 테이블과 매핑시켜준다. (@Table 어노테이션을 따로 추가하지 않고 클래스 이름을 그대로 테이블 이름으로 사용하자)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Answer {

    @Id // DB에서 id로 이용 될 변수
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Id 생성 방식을 DB에게 넘긴다.
    private Long id;

    @Column(name = "user_id") // DB Column을 표시
    private String userId;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)   // 1대1 연관관계
    @JoinColumn(name = "state_id") // column 이름 설정
    private AnswerState stateId;

    @ManyToOne // N:1 관계 표현, @ManyToOne이 붙은 엔티티가 M이고 반대 엔티티가 1이다.
    @JoinColumn(name = "problem_id")
    private Problem problemId;

    @Enumerated(EnumType.STRING)
    private Language language;

    @Column(columnDefinition = "TEXT")
    private String code;

    @Column
    private int code_length;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    /* 필요하시면 쓰시라고 ...
    * TemporalType.DATE : 날짜, 데이터베이스 data 타입과 매핑 (2020-12-18)
    * TemporalType.TIME : 시간, 데이터베이스 time 타입과 매핑 (23:36:33)
    * TemporalType.TIMESTAMP : 날짜와 시간, 데이터베이스 timestamp 타입과 매핑 (2020-12-18 23:36:33)
    * (default. TemporalType은 필수로 지정)
    * */
}

