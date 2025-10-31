package com.app.bluecotton.domain.vo.user;

import lombok.Data;

@Data
public class UserProfileVO {
    private Long id;
    private String userProfilePath;
    private String userProfileName;
    private Long userId;
}
