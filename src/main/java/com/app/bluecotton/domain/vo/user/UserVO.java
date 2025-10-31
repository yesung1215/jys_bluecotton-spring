package com.app.bluecotton.domain.vo.user;

import lombok.Data;

import java.util.Date;

@Data
public class UserVO {
    private Long id;
    private String userName;
    private String userNickName;
    private String userEmail;
    private String password;
    private String userAddress;
    private String userGender;
    private Date userBirth;
    private Long userCandy;
    private String userRank;
    private String userProvider;
}
