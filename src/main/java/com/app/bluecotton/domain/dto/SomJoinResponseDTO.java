package com.app.bluecotton.domain.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString @EqualsAndHashCode(of = "id")
public class SomJoinResponseDTO {
    private Long id;
    private Long somId;
    private String memberNickname;
    private String memberName;
    private String memberProfilePath;
    private String memberProfileName;
}
