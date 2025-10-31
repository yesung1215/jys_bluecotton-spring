package com.app.bluecotton.domain.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString @EqualsAndHashCode(of = "id")
public class TokenDTO {
    private Long memberId;
    private String accessToken;
    private String refreshToken;
}
