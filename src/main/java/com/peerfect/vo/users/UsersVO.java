package com.peerfect.vo.users;

import lombok.Data;

import java.sql.Date;

@Data
public class UsersVO {

    private int userNo;
    private int challengeNo;
    private String userName;
    private String userPassword;
    private String userEmail;
    private String userImage;
    private String userNickname;
    private Date userUIstart;
    private Date userUIend;
    private Date userUXstart;
    private Date userUXend;

    public UsersVO(){}

    // 필요한 필드만 초기화하는 생성자
    public UsersVO(String userName, String userEmail, String userPassword) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }

}
