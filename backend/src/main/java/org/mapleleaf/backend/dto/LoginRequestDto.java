package org.mapleleaf.backend.dto;

import lombok.*;

@Data
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {

    private String maple;

    private String name;

    private String picture;

    private String leaf;
}
