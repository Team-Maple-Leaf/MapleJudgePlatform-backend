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
@Table(name = "problem")
public class Problem {

    // @Column(name = "name", nullable = false, length = 10)
    @Id
    private Long id;

    private String title;

    @Column(name = "problem_desc")
    private String problemDesc;

    @Column(name = "input_desc")
    private String inputDesc;

    @Column(name = "output_desc")
    private String outputDesc;
}
